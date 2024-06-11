package com.rungroup.Dietly.models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;



    @ManyToOne
    @JoinColumn(name = "meal_types", nullable = false)
    private MealType mealType;

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", mealType=" + mealType.getName() +  // get only the name of the mealType
                ", recipe=" + recipe.getName() +  // get only the name of the recipe
                ", user=" + user.getUsername() +  // get only the username of the user
                '}';
    }

}