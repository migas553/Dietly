package com.rungroup.Dietly.services.impl;

import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.DTO.RecipeDTO;
import com.rungroup.Dietly.models.*;
import com.rungroup.Dietly.repository.*;
import com.rungroup.Dietly.services.MealService;
import com.rungroup.Dietly.services.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final MealTypeRepository mealTypeRepository;
    private final RecipeService recipeService;
    private final CategoryRepository categoryRepository;


    public MealServiceImpl(MealRepository mealRepository, RecipeRepository recipeRepository, UserRepository userRepository, MealTypeRepository mealTypeRepository,  RecipeService recipeService, CategoryRepository categoryRepository) {
        this.mealRepository = mealRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.mealTypeRepository = mealTypeRepository;
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
    }

    private MealDTO mapToMealDTO(Meal meal) {
        return MealDTO.builder()
                .id(meal.getId())
                .date(meal.getDate())
                .name(meal.getName())
                .mealType(meal.getMealType())
                .recipe(recipeService.mapToRecipeDTO(meal.getRecipe()))
                .userId(meal.getUser().getId())
                .date(meal.getDate())
                .build();
    }
    private Meal mapToMeal(MealDTO mealDTO) {
        return Meal.builder()
                .id(mealDTO.getId())
                .date(mealDTO.getDate())
                .mealType(mealTypeRepository.findById(mealDTO.getMealType().getId()).orElseThrow())
                .recipe(recipeService.mapToRecipe(mealDTO.getRecipe()))
                .user(userRepository.findById(mealDTO.getUserId()).orElseThrow())
                .date(mealDTO.getDate())
                .name(mealDTO.getName())
                .build();
    }



    @Override
    public void createMeal(MealDTO mealDTO) {
        Meal meal = mapToMeal(mealDTO);
        mealRepository.save(meal);

    }

    @Override
    public List<MealDTO> getAllMeals(UserEntity user) {
        List<Meal> meals = mealRepository.findAllByUser(user);
        return meals.stream()
                .sorted(Comparator.comparing(Meal::getDate))
                .map(this::mapToMealDTO)
                .collect(Collectors.toList());

    }
    @Transactional
    @Override
    public void generateSchedule(DietDTO diet, UserEntity user) {
        mealRepository.deleteAllByDateBefore(LocalDate.now());
        // Calculate the total number of meals
        int totalMeals = 14;

        // Calculate vegetarians meals
        int vegetarianMeals = (int) Math.round(totalMeals * (diet.getVegetablePercentage() / 100.0));
        // Calculate the difference
        int proteinMeals = totalMeals - vegetarianMeals;

        // Divide protein meals
        int meatMeals = (int) Math.round(proteinMeals * (diet.getMeatPercentage() / 100.0));
        int fishMeals = proteinMeals - meatMeals;


        // Get meat, fish and vegetable recipes
        List<Recipe> fishRecipes = recipeRepository.findByCategoryNameEquals("Fish");
        List<Recipe> meatRecipes = recipeRepository.findByCategoryNameEquals("Meat");
        List<Recipe> vegetableRecipes = recipeRepository.findByCategoryNameEquals("Vegetarian");

        // Randomize recipes
        Collections.shuffle(meatRecipes);
        Collections.shuffle(fishRecipes);
        Collections.shuffle(vegetableRecipes);

        // Slice the first n recipes from each list
        List<Recipe> selectedMeatRecipes = meatRecipes.subList(0, meatMeals);
        List<Recipe> selectedFishRecipes = fishRecipes.subList(0, fishMeals);
        List<Recipe> selectedVegetarianRecipes = vegetableRecipes.subList(0, vegetarianMeals);

        // Combine all the selected recipes into a single list
        List<Recipe> selectedRecipes = new ArrayList<>();
        selectedRecipes.addAll(selectedMeatRecipes);
        selectedRecipes.addAll(selectedFishRecipes);
        selectedRecipes.addAll(selectedVegetarianRecipes);
        Collections.shuffle(selectedRecipes);
        LocalDate startDate = diet.getStartDate();
        LocalDate endDate = diet.getStartDate().plusDays(6);
        mealRepository.deleteByDateBetween(startDate, endDate);

        List<String> daysOfWeek = new LinkedList<>(List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        String startDay = diet.getStartDate().getDayOfWeek().name();
        // Rotate the daysOfWeek list so that it starts with the specified day
        int startDayIndex = daysOfWeek.indexOf(startDay);
        Collections.rotate(daysOfWeek, -startDayIndex);

        MealDTO[][] weeklySchedule = new MealDTO[7][2];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                // Select a recipe from the list of selected recipes

                RecipeDTO selectedRecipe = recipeService.mapToRecipeDTO(selectedRecipes.removeFirst());
                LocalDate nextDayOfWeek;
                int currentDayOfWeekIndex = diet.getStartDate().getDayOfWeek().getValue() % 7;
                int targetDayOfWeekIndex = DayOfWeek.valueOf(daysOfWeek.get(i).toUpperCase()).getValue() % 7;
                int daysUntilNextDayOfWeek = (targetDayOfWeekIndex - currentDayOfWeekIndex + 7) % 7;
                nextDayOfWeek = diet.getStartDate().plusDays(daysUntilNextDayOfWeek);
                // Create a MealDTO with the selected recipe, meal type, and the day of the week as the name
                MealDTO mealDTO = new MealDTO();
                mealDTO.setRecipe(selectedRecipe);
                mealDTO.setUserId(user.getId());
                mealDTO.setDate(nextDayOfWeek);
                mealDTO.setName(daysOfWeek.get(i));

                // Set the meal type as lunch or dinner
                if (j == 0) {
                    mealDTO.setMealType(mealTypeRepository.findById(1L));
                } else {
                    mealDTO.setMealType(mealTypeRepository.findById(2L));
                }

                // Add the created MealDTO to the weekly schedule
                weeklySchedule[i][j] = mealDTO;
                System.out.println(mealDTO);
                createMeal(mealDTO);

            }
        }

//        Arrays.stream(weeklySchedule)
//                .flatMap(Arrays::stream)
//                .collect(Collectors.toList());
    }

    @Override
    public void editMeal(Long id) {
        MealDTO mealDTO = mapToMealDTO(mealRepository.findById(id).orElseThrow());
        String recipeCategory = mealDTO.getRecipe().getCategory();
        List<Recipe> categoryRecipes = recipeRepository.findByCategoryNameEquals(recipeCategory);
        Collections.shuffle(categoryRecipes);
        RecipeDTO selectedRecipe = recipeService.mapToRecipeDTO(categoryRecipes.removeFirst());
        mealDTO.setRecipe(selectedRecipe);
        createMeal(mealDTO);

    }


}
