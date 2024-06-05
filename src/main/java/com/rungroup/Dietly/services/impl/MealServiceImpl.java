package com.rungroup.Dietly.services.impl;

import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.models.*;
import com.rungroup.Dietly.repository.MealRepository;
import com.rungroup.Dietly.repository.MealTypeRepository;
import com.rungroup.Dietly.repository.RecipeRepository;
import com.rungroup.Dietly.repository.UserRepository;
import com.rungroup.Dietly.services.MealService;
import org.springframework.stereotype.Service;

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

    public MealServiceImpl(MealRepository mealRepository, RecipeRepository recipeRepository, UserRepository userRepository, MealTypeRepository mealTypeRepository) {
        this.mealRepository = mealRepository;
        this.recipeRepository = recipeRepository;

        this.userRepository = userRepository;
        this.mealTypeRepository = mealTypeRepository;
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
                .date(mealDTO.getDate())
                .mealType(mealTypeRepository.findById(mealDTO.getMealTypeId()).orElseThrow())
                .recipe(recipeRepository.findById(mealDTO.getRecipeId()).orElseThrow())
                .user(userRepository.findById(mealDTO.getUserId()).orElseThrow())
                .date(mealDTO.getDate())
                .name(mealDTO.getName())
                .build();
    }
    private void deleteMealByDate(LocalDate date, MealType mealType) {
        Meal meal = mealRepository.findByDateAndMealType(date, mealType);
        if (meal != null) {
            mealRepository.delete(meal);
        }
    }

    @Override
    public Meal createMeal(MealDTO mealDTO) {
        Meal meal = mapToMeal(mealDTO);
        deleteMealByDate(meal.getDate(), meal.getMealType());
        mealRepository.save(meal);
        return meal;
    }

    @Override
    public List<MealDTO> getAllMeals() {
        List<Meal> meals = mealRepository.findAll();
        return meals.stream().map(this::mapToMealDTO).collect(Collectors.toList());

    }

    @Override
    public List<MealDTO> generateSchedule(DietDTO diet, UserEntity user) {

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
        List<Recipe> meatRecipes = recipeRepository.findByCategoryNameEquals("Meat");
        List<Recipe> fishRecipes = recipeRepository.findByCategoryNameEquals("Fish");
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

        List<String> daysOfWeek = new LinkedList<>(List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        String startDay = diet.getStartDate().getDayOfWeek().name();
        // Rotate the daysOfWeek list so that it starts with the specified day
        while (!daysOfWeek.get(0).equalsIgnoreCase(startDay)) {
            String removedDay = daysOfWeek.remove(0);
            daysOfWeek.add(removedDay);
        }
        MealDTO[][] weeklySchedule = new MealDTO[7][2];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                // Select a recipe from the list of selected recipes
                Recipe selectedRecipe = selectedRecipes.removeFirst();
                LocalDate nextDayOfWeek =
                        diet.getStartDate().with(TemporalAdjusters.next(DayOfWeek.valueOf(daysOfWeek.get(i).toUpperCase())));

                // Create a MealDTO with the selected recipe, meal type, and the day of the week as the name
                MealDTO mealDTO = new MealDTO();
                mealDTO.setRecipeId(selectedRecipe.getId());
                mealDTO.setUserId(user.getId());
                mealDTO.setDate(nextDayOfWeek);
                mealDTO.setName(daysOfWeek.get(i));

                // Set the meal type as lunch or dinner
                if (j == 0) {
                    mealDTO.setMealTypeId(mealTypeRepository.findByName("Lunch").getId());
                } else {
                    mealDTO.setMealTypeId(mealTypeRepository.findByName("Dinner").getId());
                }

                // Add the created MealDTO to the weekly schedule
                weeklySchedule[i][j] = mealDTO;
                createMeal(mealDTO);

            }
        }

        return Arrays.stream(weeklySchedule)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    public List<MealDTO> generateWeeklySchedule(int meatPercentage, int fishPercentage, String userId) {

//        // Generate the weekly schedule based on the user's preferences and recipes
//        List<Recipe> meatRecipes = new ArrayList<>();
//        List<Recipe> fishRecipes = new ArrayList<>();
//        for (Recipe recipe : recipes) {
//            if (recipe.getCategory().getName().equals("meat")) {
//                meatRecipes.add(recipe);
//            } else if (recipe.getCategory().getName().equals("fish")) {
//                fishRecipes.add(recipe);
//            }
//        }
//
//        // Generate the weekly schedule
//        List<MealDTO> weeklySchedule = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 7; i++) {
//            if (random.nextInt(100) < meatPercentage) {
//                weeklySchedule.add(new MealDTO(meatRecipes.get(random.nextInt(meatRecipes.size()))));
//            } else {
//                weeklySchedule.add(new MealDTO(fishRecipes.get(random.nextInt(fishRecipes.size()))));
//            }
//        }

        return null;
    }


}
