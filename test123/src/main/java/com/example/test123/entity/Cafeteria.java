package com.example.test123.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cafeteria")
@Getter
@Setter
@NoArgsConstructor
public class Cafeteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafeteria_id")
    private Integer cafeteriaId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(name = "operating_hours", length = 100)
    private String operatingHours;

    @Column(nullable = false, length = 100)
    private String address;
}
