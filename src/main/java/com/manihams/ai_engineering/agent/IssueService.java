package com.manihams.ai_engineering.agent;

import org.springframework.stereotype.Service;

@Service
public class IssueService {

    private final OpenAIClient openAIClient;

    public IssueService(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }

    public String processIssue(Issue issue) {

        String prompt = "Analyze this software engineering issue.\n"
                + "Title: " + issue.getTitle() + "\n"
                + "Description: " + issue.getDescription();

        return openAIClient.analyzeIssue(prompt);
    }
}