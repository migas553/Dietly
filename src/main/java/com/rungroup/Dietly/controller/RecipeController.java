
package com.rungroup.Dietly.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rungroup.Dietly.DTO.RecipeDTO;
import com.rungroup.Dietly.models.Recipe;
import com.rungroup.Dietly.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    @GetMapping("/recipes/new")
    public String newRecipe(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "recipe-new";
    }

    @PostMapping("/recipes/new")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe,
                             @RequestParam("ingredients") String ingredientsJson,
                             @RequestParam("instructions") String instructionsJson,
                             @RequestParam("image") MultipartFile imageFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> ingredients = null;
        List<String> instructions = null;
        try {
            ingredients = objectMapper.readValue(ingredientsJson, new TypeReference<List<String>>() {
            });
            instructions = objectMapper.readValue(instructionsJson, new TypeReference<List<String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if (!imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            Path path = Paths.get("src/main/resources/static/images/" + fileName);
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream,
                        path,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);

        recipe = recipeService.createRecipe(recipe);

        if (!imageFile.isEmpty()) {
            String fileName = recipe.getName().replace(" ", "-") + "-" + recipe.getId() + ".jpg";
            Path path = Paths.get("src/main/resources/static/images/" + fileName);
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
            recipe.setImageUrl("./images/" + fileName);
            recipeService.updateRecipe(recipe); // Update the recipe with the new image URL
        }
        return "redirect:/recipes";
    }

    @GetMapping("/recipes/{recipeID}/edit")
    public String editRecipe(@RequestParam("recipeID") Long recipeID, Model model) {
        RecipeDTO recipe = recipeService.getRecipeById(recipeID);
        model.addAttribute("recipe", recipe);
        return "recipe-edit";
    }

}