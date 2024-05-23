package com.rungroup.Dietly.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder

public class RecipeDTO {
    private Long id;
    @NotEmpty(message = "Recipe title should not be empty")
    private String name;
    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;
//    @NotNull(message = "A image needs to be uploaded")
    private String imageUrl;
    @Size(min = 1, message = "At least one ingredient is required")
    private List<String> ingredients;
    @Size(min = 1, message = "At least one instruction is required")
    private List<String> instructions;
    @Min(value = 1, message = "There should be at least one serving")
    private int servings;
    @Min(value = 1, message = "Preparation time should be minimum one minute")
    private int prepTime;
    @NotNull(message = "Recipe should have a category")
    private String category;
    @CreationTimestamp
    private LocalDateTime createdOn;

    }


