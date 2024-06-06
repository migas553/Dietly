package com.rungroup.Dietly.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

//JPA annotation
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes;

    @Override
    public String toString() {
        return "Category{" +
                // other fields...
                ", recipes=" + recipes.stream().map(Recipe::getName).collect(Collectors.toList()) +  // assuming Recipe has a getName() method
                '}';
    }
}