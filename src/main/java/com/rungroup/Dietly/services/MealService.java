package com.rungroup.Dietly.services;
import java.util.List;

import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.models.UserEntity;

public interface MealService {
    void createMeal(MealDTO mealDTO);
    List<MealDTO> getAllMeals(UserEntity user);
    void generateSchedule(DietDTO diet, UserEntity user);
    void editMeal(Long id);
}
