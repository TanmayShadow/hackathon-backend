package com.example.codeforbharat.model;

import com.example.codeforbharat.DTO.QuestionDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "questions")
public class McqModel {
    @Id
    private String id;
    private Long userId;
    private List<QuestionDTO> questions;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public McqModel() {
    }

    public McqModel(String id, Long userId, List<QuestionDTO> questions, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.questions = questions;
        this.createdAt = createdAt;
    }

    public McqModel(String id, Long userId, List<QuestionDTO> questions) {
        this.id = id;
        this.userId = userId;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
