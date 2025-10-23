package com.example.test123.domain.menu.repository;

import com.example.test123.domain.menu.entity.Menu;
import com.example.test123.domain.menu.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {


}
