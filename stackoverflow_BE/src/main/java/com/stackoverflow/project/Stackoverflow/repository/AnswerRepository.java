package com.stackoverflow.project.Stackoverflow.repository;

import com.stackoverflow.project.Stackoverflow.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
