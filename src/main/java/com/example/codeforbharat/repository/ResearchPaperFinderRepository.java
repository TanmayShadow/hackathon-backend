package com.example.codeforbharat.repository;

import com.example.codeforbharat.model.LearningPathModel;
import com.example.codeforbharat.model.ReserchPaperFinderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearchPaperFinderRepository extends JpaRepository<ReserchPaperFinderModel,Long> {
}
