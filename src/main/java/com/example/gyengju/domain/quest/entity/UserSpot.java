package com.example.gyengju.domain.quest.entity;

import com.example.gyengju.domain.spot.entity.Spot;
import com.example.gyengju.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_spots")
@Getter
@NoArgsConstructor
public class UserSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", nullable = false)
    private Spot spot;

    @CreationTimestamp
    private LocalDateTime visitedAt;

    @Builder
    public UserSpot(User user, Spot spot) {
        this.user = user;
        this.spot = spot;
    }
}
