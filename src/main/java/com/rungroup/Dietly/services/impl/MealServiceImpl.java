package com.rungroup.Dietly.services.impl;

import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.DTO.WeeklyMealPlanDTO;
import com.rungroup.Dietly.models.Meal;
import com.rungroup.Dietly.models.WeeklyMealPlan;
import com.rungroup.Dietly.repository.MealRepository;
import com.rungroup.Dietly.repository.RecipeRepository;
import com.rungroup.Dietly.repository.UserRepository;
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
    private final UserRepository userRepository;

    public MealServiceImpl(MealRepository mealRepository, RecipeRepository recipeRepository, WeeklyMealPlanRepository weeklyMealPlanRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.recipeRepository = recipeRepository;
        this.weeklyMealPlanRepository = weeklyMealPlanRepository;
        this.userRepository = userRepository;
    }

    private MealDTO mapToMealDTO(Meal meal) {
        return MealDTO.builder()
                .id(meal.getId())
                .name(meal.getMealType().getName())
                .recipeId(meal.getRecipe().getId())
                .userId(meal.getUser().getId())
                .date(meal.getDate())
                .build();
    }
    private Meal mapToMeal(MealDTO mealDTO) {
        return Meal.builder()
                .id(mealDTO.getId())
                .date(mealDTO.getDate())
                .mealType(mealRepository.findById(mealDTO.getId()).orElseThrow().getMealType())
                .recipe(recipeRepository.findById(mealDTO.getRecipeId()).orElseThrow())
                .user(userRepository.findById(mealDTO.getUserId()).orElseThrow())
                .weeklyMealPlans(List.of())
                .build();
    }
    private WeeklyMealPlanDTO mapToWeeklyMealPlanDTO(WeeklyMealPlan weeklyMealPlan) {
        return WeeklyMealPlanDTO.builder()
                .id(weeklyMealPlan.getId())
                .meals(weeklyMealPlan.getMeals().stream().map(this::mapToMealDTO).collect(Collectors.toList()))
                .build();
    }
    private WeeklyMealPlan mapToWeeklyMealPlan(WeeklyMealPlanDTO weeklyMealPlanDTO) {
        return WeeklyMealPlan.builder()
                .id(weeklyMealPlanDTO.getId())
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
