package com.likelion.collab_session.domain.plant.entity;

import com.likelion.collab_session.domain.species.entity.Species;
import com.likelion.collab_session.domain.user.entity.User;
import com.likelion.collab_session.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Plant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;

    @Column(name = "watering_interval_days", nullable = false)
    private int wateringIntervalDays;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(name = "died_at")
    private LocalDateTime diedAt;

    public boolean isDead() {
        return diedAt != null;
    }

    public void die(LocalDateTime diedAt) {
        if (this.diedAt == null) {
            this.diedAt = diedAt;
        }
    }
}