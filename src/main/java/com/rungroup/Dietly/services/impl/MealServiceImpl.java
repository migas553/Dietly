package com.rungroup.Dietly.services.impl;

import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.DTO.WeeklyMealPlanDTO;
import com.rungroup.Dietly.models.Meal;
import com.rungroup.Dietly.models.WeeklyMealPlan;
import com.rungroup.Dietly.repository.MealRepository;
import com.rungroup.Dietly.repository.RecipeRepository;
import com.rungroup.Dietly.repository.WeeklyMealPlanRepository;
import com.rungroup.Dietly.services.MealService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;
    private final WeeklyMealPlanRepository weeklyMealPlanRepository;

    public MealServiceImpl(MealRepository mealRepository, RecipeRepository recipeRepository, WeeklyMealPlanRepository weeklyMealPlanRepository) {
        this.mealRepository = mealRepository;
        this.recipeRepository = recipeRepository;
        this.weeklyMealPlanRepository = weeklyMealPlanRepository;
    }

    private MealDTO mapToMealDTO(Meal meal) {
        return MealDTO.builder()
                .id(meal.getId())
                .name(meal.getName())
                .recipeId(meal.getRecipe().getId())
                .build();
    }
    private Meal mapToMeal(MealDTO mealDTO) {
        return Meal.builder()
                .id(mealDTO.getId())
                .name(mealDTO.getName())
                .recipe(recipeRepository.findById(mealDTO.getRecipeId()).orElseThrow())
                .build();
    }
    private WeeklyMealPlanDTO mapToWeeklyMealPlanDTO(WeeklyMealPlan weeklyMealPlan) {
        return WeeklyMealPlanDTO.builder()
                .id(weeklyMealPlan.getId())
                .weekStartDate(weeklyMealPlan.getWeekStartDate())
                .meals(weeklyMealPlan.getMeals().stream().map(this::mapToMealDTO).collect(Collectors.toList()))
                .build();
    }
    private WeeklyMealPlan mapToWeeklyMealPlan(WeeklyMealPlanDTO weeklyMealPlanDTO) {
        return WeeklyMealPlan.builder()
                .id(weeklyMealPlanDTO.getId())
                .weekStartDate(weeklyMealPlanDTO.getWeekStartDate())
                .meals(weeklyMealPlanDTO.getMeals().stream().map(this::mapToMeal).collect(Collectors.toList()))
                .build();
    }


    @Override
    public Meal createMeal(MealDTO mealDTO) {
        Meal meal = mapToMeal(mealDTO);
        mealRepository.save(meal);
        return meal;
    }

    @Override
    public List<MealDTO> getAllMeals() {
        List<Meal> meals = mealRepository.findAll();
        return meals.stream().map(this::mapToMealDTO).collect(Collectors.toList());

    }

    @Override
    public WeeklyMealPlan createWeeklyMealPlan(WeeklyMealPlanDTO weeklyMealPlanDTO) {
        WeeklyMealPlan weeklyMealPlan = mapToWeeklyMealPlan(weeklyMealPlanDTO);
        weeklyMealPlanRepository.save(weeklyMealPlan);
        return weeklyMealPlan;
    }

    @Override
    public List<MealDTO> getMealsByWeeklyMealPlanId(Long weeklyMealPlanId) {
        return List.of();
    }
}
