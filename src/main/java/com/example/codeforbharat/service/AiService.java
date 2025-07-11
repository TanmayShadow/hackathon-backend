package com.example.codeforbharat.service;

import com.example.codeforbharat.DTO.AiQueryDTO;
import com.example.codeforbharat.DTO.FileToMcqDTO;
import com.example.codeforbharat.DTO.QuestionDTO;
import com.example.codeforbharat.model.LearningPathModel;
import com.example.codeforbharat.model.McqModel;
import com.example.codeforbharat.model.McqUserMapperModel;
import com.example.codeforbharat.model.ReserchPaperFinderModel;
import com.example.codeforbharat.repository.LearningPathRepository;
import com.example.codeforbharat.repository.McqRepository;
import com.example.codeforbharat.repository.McqUserMapperRepository;
import com.example.codeforbharat.repository.ResearchPaperFinderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class AiService {
    private final WebClient webClient;
    @Autowired
    LearningPathRepository learningPathRepository;

    @Autowired
    ResearchPaperFinderRepository researchPaperFinderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private McqRepository mcqRepository;

    @Autowired
    private McqUserMapperRepository mcqUserMapperRepository;

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

    public McqModel getMcqFromFile(MultipartFile file, FileToMcqDTO dto, Long userId)throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // Wrap file as Resource
        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("file", fileResource);
        body.add("noOfQuestions", dto.getNoOfQuestions());
        body.add("difficultyLevel", dto.getdifficultyLevel());
        String response = webClient.post()
                .uri("/upload/")  // Replace with full URL if needed
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        McqModel mcqModel = new McqModel();
        mcqModel.setQuestions(this.createMcqModeFromRequestString(response));
        mcqModel.setUserId(userId);
        mcqModel.setCreatedAt(LocalDateTime.now());
        return mcqModel;
    }

    public McqModel saveMcqData(McqModel mcqModel){
        mcqModel.setCreatedAt(LocalDateTime.now());
        mcqModel = mcqRepository.save(mcqModel);
        McqUserMapperModel mcqUserMapperModel = new McqUserMapperModel();
        mcqUserMapperModel.setMcqId(mcqModel.getId());
        mcqUserMapperModel.setUserId(mcqModel.getUserId());
        mcqUserMapperRepository.save(mcqUserMapperModel);
        return mcqModel;
    }

    private List<QuestionDTO> createMcqModeFromRequestString(String request) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(request);
        String questionsJsonString = root.get("questions").asText();
        List<QuestionDTO> questionList = objectMapper.readValue(
                questionsJsonString,
                new TypeReference<List<QuestionDTO>>() {}
        );

        return questionList;
    }

}
