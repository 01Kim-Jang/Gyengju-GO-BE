package com.example.gyengju.domain.quest.dto;

import com.example.gyengju.domain.quest.entity.UserSpot;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VisitedSpotResponse {

    private Long spotId;
    private String nameKo;
    private String nameEn;
    private String imageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitedAt;

    public static VisitedSpotResponse from(UserSpot userSpot) {
        return new VisitedSpotResponse(
                userSpot.getSpot().getId(),
                userSpot.getSpot().getNameKo(),
                userSpot.getSpot().getNameEn(),
                userSpot.getSpot().getImageUrl(),
                userSpot.getVisitedAt()
        );
    }
}
