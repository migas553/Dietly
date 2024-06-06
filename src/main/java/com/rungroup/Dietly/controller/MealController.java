package com.rungroup.Dietly.controller;

import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.models.UserEntity;
import com.rungroup.Dietly.repository.MealRepository;
import com.rungroup.Dietly.services.MealService;
import com.rungroup.Dietly.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MealController {
    private final MealService mealService;
    private final UserService userService;
    private final MealRepository mealRepository;

    @Autowired
    public MealController(MealService mealService, UserService userService, MealRepository mealRepository) {
        this.mealService = mealService;
        this.userService = userService;
        this.mealRepository = mealRepository;
    }

    @PostMapping("/generateSchedule")
    public String generateSchedule(@Valid @ModelAttribute DietDTO diet
    ) {
        User getUsername = userService.getCurrentUser();
        UserEntity user = userService.findByUsername(getUsername.getUsername());
        mealService.generateSchedule(diet, user);



        return "redirect:/user";
    }
    @GetMapping("/shuffleMeal/{id}")
    public String shuffleMeal(@PathVariable String id) {
        mealService.editMeal(Long.parseLong(id));
        return "redirect:/user";
    }


}
