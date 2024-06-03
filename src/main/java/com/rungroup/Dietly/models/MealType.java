package com.rungroup.Dietly.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

//JPA annotation
@Entity
@Table(name = "meal_types")
public class MealType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "mealType")
    private List<Meal> meals;
}