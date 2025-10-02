package com.example.test123.domain.order.repository;

import com.example.test123.domain.order.entity.Order;
import com.example.test123.domain.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Order order);

    List<Payment> findByPaymentStatus(Short paymentStatus);

    Optional<Payment> findByTransactionId(String transactionId);
}
