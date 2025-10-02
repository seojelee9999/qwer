package com.example.test123.domain.order.repository;

import com.example.test123.domain.menu.entity.Menu;
import com.example.test123.domain.order.entity.Order;
import com.example.test123.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    List<OrderItem> findByMenu(Menu menu);
}
