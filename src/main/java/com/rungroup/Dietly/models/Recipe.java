package com.rungroup.Dietly.models;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String imageUrl;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> instructions;
    private int servings;
    private int prepTime; // in minutes
    private Tag tag;
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Getter
    public enum Tag {
        MEAT("Meat"),
        FISH("Fish"),
        VEGETARIAN("Vegetarian");

        private final String displayName;

        Tag(String displayName) {
            this.displayName = displayName;
        }
    }


}