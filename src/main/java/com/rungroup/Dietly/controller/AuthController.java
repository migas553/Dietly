package com.rungroup.Dietly.controller;

import com.rungroup.Dietly.DTO.RegistrationDTO;
import com.rungroup.Dietly.models.UserEntity;
import com.rungroup.Dietly.services.UserService;
import jakarta.validation.Valid;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AuthController{
    private UserService userService;



    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDTO user = new RegistrationDTO();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute("user") RegistrationDTO user, BindingResult result,
                               Model model) {
        UserEntity existingEmail = userService.findByEmail(user.getEmail());
        UserEntity existingUsername = userService.findByUsername(user.getUsername());
        if (existingEmail != null || existingUsername != null) {
            result.reject("email.exists", "There is already an account registered with that username or email");
            System.out.println("There is already an account registered with that username or email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            System.out.printf("Error: %s%n", result.toString());
            return "register";
        }

        userService.saveUser(user);

        return "redirect:/recipes";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/recipes";
    }




}
