package com.example.test123.domain.cafeteria.repository;

import com.example.test123.domain.cafeteria.entity.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeteriaRepository extends JpaRepository<Cafeteria, Long> {

    List<Cafeteria> findByNameContaining(String name);
}
