package com.rungroup.Dietly.DTO;

import lombok.*;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDTO {
    private Long id;
    private String name;
    private Long recipeId;
}