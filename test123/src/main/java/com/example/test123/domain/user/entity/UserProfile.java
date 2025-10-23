package com.example.test123.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(name = "card_token", length = 100)
    private String cardToken;

    @Column(length = 30)
    private String nickname;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}
