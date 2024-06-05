package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.Meal;
import com.rungroup.Dietly.models.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByName(String name);
    Meal findByDateAndMealType(LocalDate date, MealType mealType);
}