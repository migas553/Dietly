package com.rungroup.Dietly.controller;


import com.rungroup.Dietly.DTO.DietDTO;
import com.rungroup.Dietly.models.UserEntity;
import com.rungroup.Dietly.services.MealService;
import com.rungroup.Dietly.services.RecipeService;
import com.rungroup.Dietly.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("diet", diet);
        model.addAttribute("user", user);
        return "user";
    }
}
