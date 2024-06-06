package com.rungroup.Dietly.repository;

import com.rungroup.Dietly.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long>{
    Category findByName(String name);
    Category findById(long id);

}
