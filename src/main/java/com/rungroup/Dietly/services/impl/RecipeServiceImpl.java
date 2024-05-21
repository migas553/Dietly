package com.rungroup.Dietly.services.impl;

import com.rungroup.Dietly.DTO.RecipeDTO;
import com.rungroup.Dietly.models.Recipe;
import com.rungroup.Dietly.repository.RecipeRepository;
import com.rungroup.Dietly.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

//  GET ALL RECIPES - READ
    @Override
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(this::mapToRecipeDTO).collect(Collectors.toList());

    }

    private RecipeDTO mapToRecipeDTO(Recipe recipe) {

        return RecipeDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .imageUrl(recipe.getImageUrl())
                .ingredients(recipe.getIngredients())
                .instructions(recipe.getInstructions())
                .servings(recipe.getServings())
                .prepTime(recipe.getPrepTime())
                .tag(RecipeDTO.Tag.valueOf(recipe.getTag().getDisplayName().toUpperCase()))
                .createdOn(recipe.getCreatedOn())
                .build();
    }

    //  CREATE RECIPE - CREATE
    @Override
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        if (recipe.getId() != null && recipeRepository.existsById(recipe.getId())) {
            recipeRepository.save(recipe);
        } else {
            throw new IllegalArgumentException("Recipe does not exist");
        }
    }

    @Override
    public RecipeDTO getRecipeById(Long recipeID) {
        Recipe recipe = recipeRepository.findById(recipeID).get();
        return mapToRecipeDTO(recipe);
    }


}
