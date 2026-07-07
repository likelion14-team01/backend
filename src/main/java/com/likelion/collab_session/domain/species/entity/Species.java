package com.likelion.collab_session.domain.species.entity;

import com.likelion.collab_session.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "species")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Species extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "species_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "watering_interval_days", nullable = false)
    private int wateringIntervalDays;

    @Column(length = 20)
    private String category;

    @Column(length = 100)
    private String description;

    @Column(name = "interval_text", length = 20)
    private String intervalText;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}
