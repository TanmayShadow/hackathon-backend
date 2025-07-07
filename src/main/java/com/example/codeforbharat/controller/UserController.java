package com.example.codeforbharat.controller;

import com.example.codeforbharat.model.UserModel;
import com.example.codeforbharat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserModel userModel){
        String status = userService.addUser(userModel);
        Map<String,Object> response = new HashMap<>();
        if(status.equals("SUCCESS")){
            response.put("isSuccess",true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        response.put("isSuccess",false);
        response.put("message",status);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel userModel){
        String token = userService.login(userModel);
        Map<String,Object> response = new HashMap<>();
        if(token==null){
            response.put("isSuccess",false);
            response.put("message","Invalid Email or Password");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        response.put("isSuccess",true);
        response.put("token",token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
