package com.example.test123.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false)
    private Short paymentMethod = 1; // 1=CARD, 2=CASH, 3=MOBILE, 4=STUDENT_CARD

    @Column(name = "payment_status", nullable = false)
    private Short paymentStatus = 1; // 1=PENDING, 2=COMPLETED, 3=FAILED, 4=REFUNDED

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
