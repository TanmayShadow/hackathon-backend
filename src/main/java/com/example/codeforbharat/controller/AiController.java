package com.example.codeforbharat.controller;

import com.example.codeforbharat.DTO.AiQueryDTO;
import com.example.codeforbharat.DTO.FileToMcqDTO;
import com.example.codeforbharat.model.McqModel;
import com.example.codeforbharat.service.AiService;
import io.github.tanmayshadow.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {
    @Autowired
    AiService aiService;

    @Autowired
    JwtUtil jwtUtil;



    @PostMapping("/learningPath")
    public ResponseEntity<String> getLearningPath(@RequestBody AiQueryDTO aiQueryDTO, HttpServletRequest httpServletRequest)
    {

        String res = aiService.getLearningPath(aiQueryDTO, Long.parseLong(jwtUtil.getClaim(httpServletRequest,"user")+""));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/researchPaperFinder")
    public ResponseEntity<String> getResearchPaper(@RequestBody AiQueryDTO aiQueryDTO, HttpServletRequest httpServletRequest)
    {

        String res = aiService.getResearchPapers(aiQueryDTO, Long.parseLong(jwtUtil.getClaim(httpServletRequest,"user")+""));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/fileToMcqGenerater", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> generateFileToMcq(@RequestPart("file") MultipartFile file, @RequestParam("noOfQuestions") Integer noOfQuestions,@RequestParam("difficultyLevel") String difficultyLevel,HttpServletRequest httpServletRequest){
        FileToMcqDTO fileToMcqDTO = new FileToMcqDTO(noOfQuestions,difficultyLevel);
        System.out.println("Data:"+noOfQuestions);
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is required.");
        }

        // Validate file type
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null ||
                (!originalFilename.toLowerCase().endsWith(".pdf") &&
                        !originalFilename.toLowerCase().endsWith(".ppt") &&
                        !originalFilename.toLowerCase().endsWith(".pptx"))) {
            return ResponseEntity
                    .badRequest()
                    .body("Only PDF, PPT, and PPTX files are allowed.");
        }

        // Optional: contentType-based check (additional security layer)
        if (contentType != null &&
                !(contentType.equals("application/pdf") ||
                        contentType.equals("application/vnd.ms-powerpoint") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation"))) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid file type. Only PDF, PPT, and PPTX are allowed.");
        }

        try {
            McqModel mcqModel = aiService.getMcqFromFile(file,fileToMcqDTO,Long.parseLong(jwtUtil.getClaim(httpServletRequest,"user")+""));
            return new ResponseEntity<>(mcqModel,HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error while creating mcq",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveMcqData")
    public ResponseEntity<?> saveMcqData(@RequestBody McqModel mcqModel,HttpServletRequest httpServletRequest){
        Long userId = Long.parseLong(jwtUtil.getClaim(httpServletRequest,"user")+"");
        mcqModel = aiService.saveMcqData(mcqModel);
        if(mcqModel==null)
            return new ResponseEntity<>("Failed to save mcq data",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(mcqModel,HttpStatus.OK);
    }
}
