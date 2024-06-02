package com.rungroup.Dietly.models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

//JPA annotation
@Entity
@Table(name = "weekly_meal_plans")
public class WeeklyMealPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate weekStartDate;

    @OneToMany(mappedBy = "weeklyMealPlan")
    private List<Meal> meals;
}