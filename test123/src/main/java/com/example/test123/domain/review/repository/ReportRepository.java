package com.example.test123.domain.review.repository;

import com.example.test123.domain.review.entity.Report;
import com.example.test123.domain.review.entity.Review;
import com.example.test123.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReview(Review review);

    List<Report> findByReporterUser(User reporterUser);

    List<Report> findByStatus(Short status);

    List<Report> findByStatusOrderByCreatedAtDesc(Short status);
}
