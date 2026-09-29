package com.manihams.ai_engineering.agent;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Service
public class GitHubClient {

    private final RestClient restClient;

    public GitHubClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + System.getenv("GITHUB_TOKEN"))
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }

    public String getRepository(String owner, String repository) {

        return restClient.get()
                .uri("/repos/{owner}/{repository}", owner, repository)
                .retrieve()
                .body(String.class);
    }

    public String getRepositoryFiles(String owner, String repository) {

        StringBuilder files = new StringBuilder();
    
        getFilesRecursively(owner, repository, "", files);
    
        return files.toString();
    }
    
    private void getFilesRecursively(
            String owner,
            String repository,
            String path,
            StringBuilder files) {
    
        GitHubFile[] contents = restClient.get()
                .uri("/repos/{owner}/{repository}/contents/{path}",
                        owner, repository, path)
                .retrieve()
                .body(GitHubFile[].class);
    
        for (GitHubFile file : contents) {
    
            if (file.getType().equals("dir")) {
    
                getFilesRecursively(
                        owner,
                        repository,
                        file.getPath(),
                        files
                );
    
            } else {
    
                files.append(file.getPath()).append("\n");
            }
        }
    }

    public String getFile(String owner, String repository, String path) {

        GitHubFile file = restClient.get()
                .uri("/repos/{owner}/{repository}/contents/{path}", owner, repository, path)
                .retrieve()
                .body(GitHubFile.class);

        String encodedContent = file.getContent().replaceAll("\\s", "");

        byte[] decodedBytes = Base64.getDecoder().decode(encodedContent);

        return new String(decodedBytes);
    }
}
