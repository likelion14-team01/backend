package com.likelion.collab_session.domain.record.entity;

import com.likelion.collab_session.domain.plant.entity.Plant;
import com.likelion.collab_session.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "records",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_records_plant_date",
                columnNames = {"plant_id", "record_date"}
        )
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private boolean watered;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "growth_tag", length = 20)
    private GrowthTag growthTag;

    public void update(boolean watered, String photoUrl, String note, GrowthTag growthTag) {
        this.watered = watered;
        this.photoUrl = photoUrl;
        this.note = note;
        this.growthTag = growthTag;
    }
}