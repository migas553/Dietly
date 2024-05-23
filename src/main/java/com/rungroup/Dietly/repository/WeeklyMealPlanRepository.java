package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.WeeklyMealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyMealPlanRepository extends JpaRepository<WeeklyMealPlan, Long> {
}