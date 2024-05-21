package com.rungroup.Dietly.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder

public class RecipeDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private List<String> ingredients;
    private List<String> instructions;
    private int servings;
    private int prepTime; // in minutes
    private Tag tag;
    private LocalDateTime createdOn;

    public enum Tag {
        MEAT("Meat"),
        FISH("Fish"),
        VEGETARIAN("Vegetarian");

        private final String displayName;

        Tag(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return this.displayName;
        }
    }

}
