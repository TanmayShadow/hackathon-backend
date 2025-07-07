package com.example.codeforbharat.repository;

import com.example.codeforbharat.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel,Long> {

    public UserModel findByEmail(String email);
}
