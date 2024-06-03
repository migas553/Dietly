package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealTypeRepository extends JpaRepository<MealType, Long> {
    MealType findByName(String name);
}
