package com.rungroup.Dietly.DTO;



import lombok.*;
import java.time.LocalDate;
import java.util.List;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyMealPlanDTO {
    private Long id;
    private List<MealDTO> meals;
}