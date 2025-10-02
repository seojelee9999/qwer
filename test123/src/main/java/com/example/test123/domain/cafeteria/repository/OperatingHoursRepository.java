package com.example.test123.domain.cafeteria.repository;

import com.example.test123.domain.cafeteria.entity.Cafeteria;
import com.example.test123.domain.cafeteria.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatingHoursRepository extends JpaRepository<OperatingHours, Long> {

    List<OperatingHours> findByCafeteria(Cafeteria cafeteria);

    List<OperatingHours> findByCafeteriaAndDayOfWeek(Cafeteria cafeteria, Short dayOfWeek);
}
