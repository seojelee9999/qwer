package com.example.test123.domain.order.repository;

import com.example.test123.domain.order.entity.Order;
import com.example.test123.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(Short status);

    List<Order> findByUserAndStatus(User user, Short status);

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
