package com.example.gyengju.domain.proxy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRequest {
    private List<Message> messages;

    @Getter
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
