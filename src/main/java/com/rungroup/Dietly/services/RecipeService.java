package com.rungroup.Dietly.services;
import com.rungroup.Dietly.DTO.RecipeDTO;
import com.rungroup.Dietly.models.Recipe;

import java.util.List;


public interface RecipeService {
    List<RecipeDTO> getAllRecipes();
    Recipe createRecipe(RecipeDTO recipe);

    void updateRecipe(RecipeDTO recipe);
    void updateRecipePhoto(RecipeDTO recipe);

    Recipe mapToRecipe(RecipeDTO recipeDTO);

    RecipeDTO getRecipeById(Long recipeID);
    void deleteRecipe(Long recipeID);
    RecipeDTO mapToRecipeDTO(Recipe recipe);

}
