package com.example.gyengju.domain.quest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VisitRequest {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
