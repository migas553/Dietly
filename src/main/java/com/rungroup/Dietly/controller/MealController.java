package com.rungroup.Dietly.controller;

import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.DTO.MealDTO;
import com.rungroup.Dietly.models.UserEntity;
import com.rungroup.Dietly.services.MealService;
import com.rungroup.Dietly.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MealController {
    private final MealService mealService;
    private final UserService userService;

    @Autowired
    public MealController(MealService mealService, UserService userService) {
        this.mealService = mealService;
        this.userService = userService;
    }

    @PostMapping("/generateSchedule")
    public String generateSchedule(@Valid @ModelAttribute DietDTO diet
    ) {
        User getUsername = userService.getCurrentUser();
        UserEntity user = userService.findByUsername(getUsername.getUsername());
        List<MealDTO> meals = mealService.generateSchedule(diet, user);



        return "redirect:/user";
    }


}
