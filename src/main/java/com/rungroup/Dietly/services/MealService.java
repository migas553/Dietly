package com.rungroup.Dietly.services;
import java.util.List;

import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.models.Meal;
import com.rungroup.Dietly.models.UserEntity;

public interface MealService {
    Meal createMeal(MealDTO mealDTO);
    List<MealDTO> getAllMeals();
    List<MealDTO> generateSchedule(DietDTO diet, UserEntity user);
}
