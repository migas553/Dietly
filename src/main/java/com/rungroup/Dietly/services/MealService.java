package com.rungroup.Dietly.services;
import java.util.List;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.DTO.WeeklyMealPlanDTO;
import com.rungroup.Dietly.models.Meal;
import com.rungroup.Dietly.models.WeeklyMealPlan;

public interface MealService {
    Meal createMeal(MealDTO mealDTO);
    List<MealDTO> getAllMeals();
    WeeklyMealPlan createWeeklyMealPlan(WeeklyMealPlanDTO weeklyMealPlanDTO);
    List<MealDTO> getMealsByWeeklyMealPlanId(Long weeklyMealPlanId);
}
