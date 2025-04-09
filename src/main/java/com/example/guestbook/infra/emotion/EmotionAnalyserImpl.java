package com.example.guestbook.infra.emotion;

import com.example.guestbook.domain.post.service.EmotionAnalyser;
import com.example.guestbook.infra.emotion.dto.EmotionAnalysisRequest;
import com.example.guestbook.infra.emotion.dto.EmotionAnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EmotionAnalyserImpl implements EmotionAnalyser {

    public final RestClient restClient;

    public EmotionAnalyserImpl(@Value("${ai-server.url}") String AI_SERVER_URL) {
        this.restClient = RestClient.create(AI_SERVER_URL);
    }

    @Override
    public String analysis(String content) {
        EmotionAnalysisResponse response = restClient.post()
                .uri("/emotion/analysis")
                .body(new EmotionAnalysisRequest(content))
                .retrieve()
                .body(EmotionAnalysisResponse.class);

        return response.getLabel();
    }

}
