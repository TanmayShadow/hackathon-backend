package com.example.codeforbharat.service;

import com.example.codeforbharat.DTO.LearningPathDTO;
import com.example.codeforbharat.model.LearningPathModel;
import com.example.codeforbharat.repository.LearningPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LearningPathService {
    private final WebClient webClient;
    @Autowired
    LearningPathRepository learningPathRepository;

    @Autowired
    public LearningPathService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getLearningPath(LearningPathDTO query,Long userId){
        String response = webClient.post()
                .uri("/learnpath")  // ← appended to baseUrl
                .bodyValue(query)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        LearningPathModel learningPathModel = new LearningPathModel();
        learningPathModel.setQuery(query.getQuery());
        learningPathModel.setResponse(response);
        learningPathModel.setUserId(userId);
        learningPathRepository.save(learningPathModel);
        return response;
    }


}
