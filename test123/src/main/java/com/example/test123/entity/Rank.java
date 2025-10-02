package com.example.test123.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "rank")
@Getter
@Setter
@NoArgsConstructor
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Integer rankId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false, length = 10)
    private String period;

    @Column(name = "rank_date", nullable = false)
    private LocalDate rankDate;

    @Column(nullable = false)
    private Integer volume;

    @Column(name = "rank_position", nullable = false)
    private Integer rankPosition;
}
