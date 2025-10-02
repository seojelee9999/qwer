package com.example.test123.domain.order.repository;

import com.example.test123.domain.order.entity.Cart;
import com.example.test123.domain.order.entity.CartItem;
import com.example.test123.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndMenu(Cart cart, Menu menu);

    void deleteByCart(Cart cart);
}
