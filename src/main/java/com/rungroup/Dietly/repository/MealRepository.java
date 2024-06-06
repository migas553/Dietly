package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.Meal;
import com.rungroup.Dietly.models.MealType;
import com.rungroup.Dietly.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByName(String name);
    Meal findById (MealType mealType);
    List<Meal> findAllByUser(UserEntity user);
    void deleteByDateBetween(LocalDate startDate, LocalDate endDate);
}