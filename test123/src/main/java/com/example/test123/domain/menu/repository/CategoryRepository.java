package com.example.test123.domain.menu.repository;

import com.example.test123.domain.cafeteria.entity.Cafeteria;
import com.example.test123.domain.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCafeteria(Cafeteria cafeteria);

    List<Category> findByCategoryNameContaining(String categoryName);
}
