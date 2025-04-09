package com.example.guestbook.infra.emotion.dto;

public class EmotionAnalysisResponse {

    private String input;
    private int predictedClass;
    private String label;

    public EmotionAnalysisResponse() {
    }

    public EmotionAnalysisResponse(String input, int predictedClass, String label) {
        this.input = input;
        this.predictedClass = predictedClass;
        this.label = label;
    }

    public String getInput() {
        return input;
    }

    public int getPredictedClass() {
        return predictedClass;
    }

    public String getLabel() {
        return label;
    }
}
