package com.manihams.ai_engineering.agent;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;
    private final GitHubClient gitHubClient;

    public IssueController(IssueService issueService, GitHubClient gitHubClient) {
        this.issueService = issueService;
        this.gitHubClient = gitHubClient;
    }

    @PostMapping
    public String createIssue(@RequestBody Issue issue) {
        return issueService.processIssue(issue);
    }

    @GetMapping("/github-test")
    public String testGitHub() {
        return gitHubClient.getRepository(
                "Manihams",
                "ai-engineering-agent"
        );
    }

    @GetMapping("/github-files")
    public String testGitHubFiles() {
        return gitHubClient.getRepositoryFiles(
                "Manihams",
                "ai-engineering-agent"
        );
    }

    @GetMapping("/github-file")
    public String testGitHubFile() {
        return gitHubClient.getFile(
                "Manihams",
                "ai-engineering-agent",
                "src/main/java/com/manihams/ai_engineering/agent/IssueService.java"
        );
    }
}