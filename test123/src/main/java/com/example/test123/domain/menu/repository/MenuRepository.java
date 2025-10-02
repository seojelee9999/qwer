package com.example.test123.domain.menu.repository;

import com.example.test123.domain.menu.entity.Category;
import com.example.test123.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByCategory(Category category);

    List<Menu> findByAvailable(Boolean available);

    List<Menu> findByCategoryAndAvailable(Category category, Boolean available);

    List<Menu> findByNameContaining(String name);
}
