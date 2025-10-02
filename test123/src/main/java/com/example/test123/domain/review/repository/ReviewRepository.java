package com.example.test123.domain.review.repository;

import com.example.test123.domain.menu.entity.Menu;
import com.example.test123.domain.review.entity.Review;
import com.example.test123.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMenu(Menu menu);

    List<Review> findByUser(User user);

    List<Review> findByMenuOrderByCreatedAtDesc(Menu menu);
}
