package com.example.gyengju.domain.proxy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TranslateRequest {
    private String text;
    private String targetLangCode;
}
