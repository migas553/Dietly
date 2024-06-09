package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.Meal;
import com.rungroup.Dietly.models.MealType;
import com.rungroup.Dietly.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByName(String name);
    Meal findById (MealType mealType);
    List<Meal> findAllByUser(UserEntity user);
    void deleteByDateBetween(LocalDate startDate, LocalDate endDate);
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.date < :date")
    void deleteAllByDateBefore(@Param("date") LocalDate date);
}