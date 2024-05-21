package com.rungroup.Dietly.repository;
import com.rungroup.Dietly.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);

}
