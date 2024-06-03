
package com.rungroup.Dietly.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rungroup.Dietly.DTO.RecipeDTO;
import com.rungroup.Dietly.models.Recipe;
import com.rungroup.Dietly.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities =
                    ((UserDetails) authentication.getPrincipal()).getAuthorities();
            isAdmin = authorities.stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);
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
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeDTO recipeDTO, BindingResult bindingResult, Model model,
                             @RequestParam("ingredients") String ingredientsJson,
                             @RequestParam("instructions") String instructionsJson,
                             @RequestParam("image") MultipartFile imageFile) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipe", recipeDTO);
            return "recipe-new";
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> ingredients;
        List<String> instructions;
        try {
            ingredients = objectMapper.readValue(ingredientsJson, new TypeReference<>() {
            });
            instructions = objectMapper.readValue(instructionsJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        recipeDTO.setIngredients(ingredients);
        recipeDTO.setInstructions(instructions);

        String fileName = recipeDTO.getName().replace(" ", "-") + ".jpg";
        Path path = Paths.get("src/main/resources/static/images/%s".formatted(fileName));
        try (InputStream inputStream = imageFile.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        recipeDTO.setImageUrl("/images/" + fileName);

        recipeService.createRecipe(recipeDTO);

        return "redirect:/recipes";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/recipes/edit/{recipeID}")
    public String editRecipe(@PathVariable Long recipeID, Model model) {
        RecipeDTO recipe = recipeService.getRecipeById(recipeID);
        model.addAttribute("recipe", recipe);
        return "recipes-edit";
    }

    @PostMapping("/recipes/edit/{recipeId}")
    public String updateRecipe(@PathVariable("recipeId") Long recipeID,
                               @Valid @ModelAttribute("recipe") RecipeDTO recipe,
                               BindingResult bindingResult,
                               @RequestParam("ingredients") String ingredientsJson,
                               @RequestParam("instructions") String instructionsJson,
                               @RequestParam(value = "newImage", required = false) MultipartFile imageFile) {
        if (bindingResult.hasErrors()) {
            return "recipes-edit";
        }
        List<String> ingredients = Arrays.asList(ingredientsJson.split("\\|"));
        List<String> instructions = Arrays.asList(instructionsJson.split("\\|"));

        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = recipe.getName().replace(" ", "-") + "-" + recipe.getId() + ".jpg";
            Path path = Paths.get("src/main/resources/static/images/" + fileName);
            try {
                Files.deleteIfExists(path);
                try (InputStream inputStream = imageFile.getInputStream()) {
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image file.", e);
            }
            recipe.setImageUrl("/images/" + fileName);
        } else {
            RecipeDTO currentRecipe = recipeService.getRecipeById(recipeID);
            recipe.setImageUrl(currentRecipe.getImageUrl());
        }

        recipeService.updateRecipe(recipe);
        return "redirect:/recipes";
    }

    @DeleteMapping("/recipes/delete/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/recipes/{recipeID}")
    public String viewRecipe(@PathVariable Long recipeID, Model model) {
        RecipeDTO recipe = recipeService.getRecipeById(recipeID);
        model.addAttribute("recipe", recipe);
        return "recipe-show";
    }


}

