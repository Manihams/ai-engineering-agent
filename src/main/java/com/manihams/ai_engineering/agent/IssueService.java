package com.manihams.ai_engineering.agent;

import org.springframework.stereotype.Service;

@Service
public class IssueService {

    private final OpenAIClient openAIClient;
    private final GitHubClient gitHubClient;

    public IssueService(
            OpenAIClient openAIClient,
            GitHubClient gitHubClient) {

        this.openAIClient = openAIClient;
        this.gitHubClient = gitHubClient;
    }

    public String processIssue(Issue issue) {

        // Step 1: Get all files in the repository
        String files = gitHubClient.getRepositoryFiles(
                "Manihams",
                "ai-engineering-agent"
        );

        // Step 2: Ask AI which files are relevant
        String prompt = "You are a software engineering agent.\n\n"
                + "Software engineering issue:\n"
                + "Title: " + issue.getTitle() + "\n"
                + "Description: " + issue.getDescription() + "\n\n"
                + "Here is the list of files in the GitHub repository:\n"
                + files + "\n\n"
                + "Identify the files that are most relevant to investigating this issue.\n"
                + "Return only the file paths, one per line.\n"
                + "Do not include explanations.";

        String selectedFiles = openAIClient.analyzeIssue(prompt);

        // Step 3: Retrieve the actual source code for each selected file
        StringBuilder relevantCode = new StringBuilder();

        String[] paths = selectedFiles.split("\\R");

        for (String path : paths) {

            path = path.trim();

            if (path.isEmpty()) {
                continue;
            }

            String code = gitHubClient.getFile(
                    "Manihams",
                    "ai-engineering-agent",
                    path
            );

            relevantCode.append("\n--- FILE: ")
                    .append(path)
                    .append(" ---\n");

            relevantCode.append(code);
        }

        return relevantCode.toString();
    }
}