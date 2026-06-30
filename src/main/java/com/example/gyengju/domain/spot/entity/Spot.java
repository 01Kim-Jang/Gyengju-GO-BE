package com.example.gyengju.domain.spot.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "spots")
@Getter
@NoArgsConstructor
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stid;

    @Column(nullable = false)
    private String nameKo;

    @Column(nullable = false)
    private String nameEn;

    @Column(columnDefinition = "TEXT")
    private String descriptionKo;

    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String imageUrl;

    private String audioUrl;

    @Builder
    public Spot(String stid, String nameKo, String nameEn, String descriptionKo,
                String descriptionEn, Double latitude, Double longitude,
                String imageUrl, String audioUrl) {
        this.stid = stid;
        this.nameKo = nameKo;
        this.nameEn = nameEn;
        this.descriptionKo = descriptionKo;
        this.descriptionEn = descriptionEn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
    }
}
