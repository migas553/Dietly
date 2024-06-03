package com.rungroup.Dietly.DTO;

import lombok.*;

import java.util.Date;

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
    private Date date;
}