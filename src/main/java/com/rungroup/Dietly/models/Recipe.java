package com.rungroup.Dietly.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

//JPA annotation
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> instructions;
    private int servings;
    private int prepTime; // in minutes
    private Tag tag;
    @CreationTimestamp
    private LocalDateTime createdOn;

    public enum Tag {
        MEAT, FISH, VEGETARIAN
    }
}