package com.rungroup.Dietly.models;
import jakarta.persistence.*;
import lombok.*;
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
    private String imageUrl;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> instructions;
    private int servings;
    private int prepTime; // in minutes
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @CreationTimestamp
    private LocalDateTime createdOn;



}