package com.manihams.ai_engineering.agent;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class OpenAIClient {

    private final RestClient restClient;

    public OpenAIClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"))
                .build();
    }

    public String analyzeIssue(String issue) {

        Map<String, Object> request = Map.of(
                "model", "gpt-4.1-mini",
                "input", issue
        );

        OpenAIResponse response = restClient.post()
                .uri("/responses")
                .body(request)
                .retrieve()
                .body(OpenAIResponse.class);

        return response.getOutput()
                .get(0)
                .getContent()
                .get(0)
                .getText();
    }
}