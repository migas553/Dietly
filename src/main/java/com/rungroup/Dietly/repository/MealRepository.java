package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}