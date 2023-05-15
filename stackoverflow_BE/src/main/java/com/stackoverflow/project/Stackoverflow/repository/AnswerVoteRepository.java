package com.stackoverflow.project.Stackoverflow.repository;

import com.stackoverflow.project.Stackoverflow.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
    @Query("SELECT a FROM AnswerVote a WHERE a.user=?1 AND a.answer=?2")
    Optional<AnswerVote> findAnswerVoteByLoggedUser(User loggedUser, Answer answer);
}
