package com.example.test123.domain.menu.repository;

import com.example.test123.domain.menu.entity.Menu;
import com.example.test123.domain.menu.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {

    List<Rank> findByPeriodAndRankDate(String period, LocalDate rankDate);

    List<Rank> findByMenu(Menu menu);

    List<Rank> findByPeriodAndRankDateOrderByRankPositionAsc(String period, LocalDate rankDate);
}
