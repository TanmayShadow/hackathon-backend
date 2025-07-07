package com.example.codeforbharat.service;

import com.example.codeforbharat.model.UserModel;
import com.example.codeforbharat.repository.UserRepository;
import io.github.tanmayshadow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    int tokenExpirationTime = 172800000;//1000*60*60*48 = 2 Days

    public String generateEncryptedPassword(String password){
        return passwordEncoder.encode(password);
    }

    public String addUser(UserModel userModel){
        if(!userModel.isEmailValid())
            return "Email is not valid";
        System.out.println(userModel.getPassword());
        userModel.setPassword(generateEncryptedPassword(userModel.getPassword()));
        try {
            userRepository.save(userModel);
        }catch (DataIntegrityViolationException exception){
            return "Email already exist";
        }catch (Exception exception){
            return "User Addition failed";
        }
        return "SUCCESS";
    }

    public String login(UserModel userModel){
        try {
            if(!userModel.isEmailValid())
                return null;
            UserModel  searchedUser = userRepository.findByEmail(userModel.getEmail());
            if(passwordEncoder.matches(userModel.getPassword(),searchedUser.getPassword()))
            {
                Map<String, Object> map = new HashMap<>();
                map.put("user",searchedUser.getId());
                map.put("email",searchedUser.getEmail());
                return jwtUtil.generateJwtToken(map,this.tokenExpirationTime);
            }
        }catch (NullPointerException nullPointerException){
            return null;
        }
        return null;
    }

}
