package com.example.codeforbharat.repository;

import com.example.codeforbharat.model.McqModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface McqRepository extends MongoRepository<McqModel,String> {
}
