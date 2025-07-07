package com.example.codeforbharat.controller;

import com.example.codeforbharat.DTO.LearningPathDTO;
import com.example.codeforbharat.service.LearningPathService;
import io.github.tanmayshadow.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {
    @Autowired
    LearningPathService learningPath;

    @Autowired
    JwtUtil jwtUtil;



    @PostMapping("/learningPath")
    public ResponseEntity<String> getLearningPath(@RequestBody LearningPathDTO learningPathDTO, HttpServletRequest httpServletRequest)
    {

        String res = learningPath.getLearningPath(learningPathDTO, Long.parseLong(jwtUtil.getClaim(httpServletRequest,"user")+""));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
