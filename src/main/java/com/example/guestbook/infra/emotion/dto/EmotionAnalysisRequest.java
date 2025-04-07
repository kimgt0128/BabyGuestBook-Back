package com.example.guestbook.infra.emotion.dto;

public class EmotionAnalysisRequest {
    private String text;

    public EmotionAnalysisRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
