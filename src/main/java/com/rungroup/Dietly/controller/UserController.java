package com.rungroup.Dietly.controller;


import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.models.UserEntity;
import com.rungroup.Dietly.services.MealService;
import com.rungroup.Dietly.services.RecipeService;
import com.rungroup.Dietly.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;
    private final MealService mealService;

    @Autowired
    public UserController(UserService userService, RecipeService recipeService, MealService mealService) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.mealService = mealService;
    }

    @GetMapping("/user")
    public String user(Model model) {
        User getUsername = userService.getCurrentUser();
        UserEntity user = userService.findByUsername(getUsername.getUsername());
        DietDTO diet = new DietDTO();
        List<MealDTO> meals = mealService.getAllMeals(user);
        List<MealDTO> sortedMeals = meals.stream()
                .sorted(Comparator.comparing(MealDTO::getDate))
                .collect(Collectors.toList());

        Map<LocalDate, List<MealDTO>> mealsByDate = sortedMeals.stream()
                .collect(Collectors.groupingBy(MealDTO::getDate, LinkedHashMap::new, Collectors.toList()));
        model.addAttribute("mealsByDate", mealsByDate);
        model.addAttribute("diet", diet);
        model.addAttribute("user", user);
        return "user";
    }
}
