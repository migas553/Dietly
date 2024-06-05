package com.rungroup.Dietly.DTO;

import lombok.*;

import java.time.LocalDate;


//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDTO {
    private Long id;
    private Long recipeId;
    private Long userId;
    private String name;
    private LocalDate date;
    private Long mealTypeId;
}