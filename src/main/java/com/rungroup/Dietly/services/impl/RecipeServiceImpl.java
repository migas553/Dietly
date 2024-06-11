package com.rungroup.Dietly.services.impl;
import com.rungroup.Dietly.DTO.RecipeDTO;
import com.rungroup.Dietly.models.Category;
import com.rungroup.Dietly.models.Recipe;
import com.rungroup.Dietly.repository.CategoryRepository;
import com.rungroup.Dietly.repository.RecipeRepository;
import com.rungroup.Dietly.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    public RecipeServiceImpl(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

//  GET ALL RECIPES - READ
    @Override
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(this::mapToRecipeDTO).collect(Collectors.toList());

    }
    @Override
    public RecipeDTO mapToRecipeDTO(Recipe recipe) {

        return RecipeDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .imageUrl(recipe.getImageUrl())
                .ingredients(recipe.getIngredients())
                .instructions(recipe.getInstructions())
                .servings(recipe.getServings())
                .prepTime(recipe.getPrepTime())
                .category(recipe.getCategory().getName())
                .createdOn(recipe.getCreatedOn())
                .build();
    }

    @Override
    public List<RecipeDTO> getRecipesByCategory(String category) {
        System.out.println(category);
        List<Recipe> recipes = recipeRepository.findByCategoryNameEquals(category);
        return recipes.stream().map(this::mapToRecipeDTO).collect(Collectors.toList());
    }

    //  CREATE RECIPE - CREATE
    @Override
    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = mapToRecipe(recipeDTO);
        recipeRepository.save(recipe);
        return recipe;
    }


    @Override
    public void updateRecipe(RecipeDTO recipeDTO) {
        System.out.println(recipeDTO);
        Recipe recipe = mapToRecipe(recipeDTO);
       if (recipe.getId() != null && recipeRepository.existsById(recipe.getId())) {
            recipeRepository.save(recipe);
        } else {
            throw new IllegalArgumentException("Recipe does not exist");
        }
    }
    public void updateRecipePhoto(RecipeDTO recipeDTO) {
        Recipe recipe = mapToRecipe(recipeDTO);
        if (recipe.getId() != null && recipeRepository.existsById(recipe.getId())) {
            recipeRepository.save(recipe);
        } else {
            throw new IllegalArgumentException("Recipe does not exist");
        }
    }
    @Override
    public Recipe mapToRecipe(RecipeDTO recipeDTO) {
        return Recipe.builder()
                .id(recipeDTO.getId())
                .name(recipeDTO.getName()) 
                .description(recipeDTO.getDescription())
                .imageUrl(recipeDTO.getImageUrl())
                .ingredients(recipeDTO.getIngredients())
                .instructions(recipeDTO.getInstructions())
                .servings(recipeDTO.getServings())
                .prepTime(recipeDTO.getPrepTime())
                .category(categoryRepository.findByName(recipeDTO.getCategory()))
                .build();

    }

    @Override
    public RecipeDTO getRecipeById(Long recipeID) {
        Recipe recipe = recipeRepository.findById(recipeID).get();
        return mapToRecipeDTO(recipe);
    }

    @Override
    public void deleteRecipe(Long recipeID) {
        recipeRepository.deleteById(recipeID);
    }


}
