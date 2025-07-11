package com.example.codeforbharat.DTO;

public class FileToMcqDTO {
    Integer  noOfQuestions;
    String difficultyLevel;

    public FileToMcqDTO() {
    }

    public FileToMcqDTO(Integer  noOfQuestions, String difficultyLevel) {
        this.noOfQuestions = noOfQuestions;
        this.difficultyLevel = difficultyLevel;
    }

    public Integer  getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public String getdifficultyLevel() {
        return difficultyLevel;
    }

    public void setdifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
