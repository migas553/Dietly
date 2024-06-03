package com.rungroup.Dietly.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Date;
//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

//JPA annotation
@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToMany(mappedBy = "meals")
    private List<WeeklyMealPlan> weeklyMealPlans;

    @ManyToOne
    @JoinColumn(name = "meal_types", nullable = false)
    private MealType mealType;


}