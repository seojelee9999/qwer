package com.example.test123.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "social_auth", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"provider", "social_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class SocialAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_auth_id")
    private Long socialAuthId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 20)
    private String provider;

    @Column(name = "social_id", nullable = false, length = 50)
    private String socialId;

    @Column(length = 255)
    private String token;
}
