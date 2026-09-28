package com.manihams.ai_engineering.agent;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping
    public String createIssue(@RequestBody Issue issue) {
        return issueService.processIssue(issue);
    }
}