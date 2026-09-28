package com.manihams.ai_engineering.agent;

import java.util.List;

public class OpenAIResponse {

    private List<OutputItem> output;

    public List<OutputItem> getOutput() {
        return output;
    }

    public void setOutput(List<OutputItem> output) {
        this.output = output;
    }

    public static class OutputItem {

        private List<ContentItem> content;

        public List<ContentItem> getContent() {
            return content;
        }

        public void setContent(List<ContentItem> content) {
            this.content = content;
        }
    }

    public static class ContentItem {

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}