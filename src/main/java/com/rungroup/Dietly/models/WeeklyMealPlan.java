package com.rungroup.Dietly.models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Arrays;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "weekly_meal_plans_meals",
            joinColumns = @JoinColumn(
                    name = "weekly_meal_plan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "meal_id", referencedColumnName = "id"))
    private List<Meal> meals;

}