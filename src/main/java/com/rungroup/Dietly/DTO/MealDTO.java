package com.rungroup.Dietly.DTO;

import com.rungroup.Dietly.models.MealType;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDate;


//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDTO {
    private Long id;
    private RecipeDTO recipe;
    private Long userId;
    private String name;
    @FutureOrPresent
    private LocalDate date;
    private MealType mealType;
}