package com.example.codeforbharat.service;

import com.example.codeforbharat.DTO.AiQueryDTO;
import com.example.codeforbharat.model.LearningPathModel;
import com.example.codeforbharat.model.ReserchPaperFinderModel;
import com.example.codeforbharat.repository.LearningPathRepository;
import com.example.codeforbharat.repository.ResearchPaperFinderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AiService {
    private final WebClient webClient;
    @Autowired
    LearningPathRepository learningPathRepository;

    @Autowired
    ResearchPaperFinderRepository researchPaperFinderRepository;

    @Autowired
    public AiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getLearningPath(AiQueryDTO query, Long userId){
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

    public String getResearchPapers(AiQueryDTO query,Long userId){
        String response = webClient.post()
                .uri("/research_material")  // ← appended to baseUrl
                .bodyValue(query)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ReserchPaperFinderModel reserchPaperFinderModel = new ReserchPaperFinderModel();
        reserchPaperFinderModel.setQuery(query.getQuery());
        reserchPaperFinderModel.setResponse(response);
        reserchPaperFinderModel.setUserId(userId);
        researchPaperFinderRepository.save(reserchPaperFinderModel);
        return response;
    }

}
