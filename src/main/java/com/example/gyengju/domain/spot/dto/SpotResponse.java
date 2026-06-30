package com.example.gyengju.domain.spot.dto;

import com.example.gyengju.domain.spot.entity.Spot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpotResponse {

    private Long id;
    private String stid;
    private String nameKo;
    private String nameEn;
    private String descriptionKo;
    private String descriptionEn;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private String audioUrl;

    public static SpotResponse from(Spot spot) {
        return new SpotResponse(
                spot.getId(),
                spot.getStid(),
                spot.getNameKo(),
                spot.getNameEn(),
                spot.getDescriptionKo(),
                spot.getDescriptionEn(),
                spot.getLatitude(),
                spot.getLongitude(),
                spot.getImageUrl(),
                spot.getAudioUrl()
        );
    }
}
