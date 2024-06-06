package com.rungroup.Dietly.repository;
import com.rungroup.Dietly.models.Category;
import com.rungroup.Dietly.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);
    List<Recipe> findByCategoryNameEquals(String categoryName);
    Recipe findById(long id);
    List<Recipe> findByCategory(Category category);



}
