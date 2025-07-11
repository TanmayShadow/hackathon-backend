package com.example.codeforbharat.DTO;

import java.util.List;

public class QuestionDTO {
    private String question;
    private List<String> options;
    private String answer;
    private String difficulty;

    public QuestionDTO() {
    }

    public QuestionDTO(String question, List<String> options, String answer, String difficulty) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
