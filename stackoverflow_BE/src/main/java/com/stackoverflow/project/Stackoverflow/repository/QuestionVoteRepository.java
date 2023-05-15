package com.stackoverflow.project.Stackoverflow.repository;

import com.stackoverflow.project.Stackoverflow.model.Question;
import com.stackoverflow.project.Stackoverflow.model.QuestionVote;
import com.stackoverflow.project.Stackoverflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
    @Query("SELECT q FROM QuestionVote q WHERE q.user=?1 AND q.question=?2")
    Optional<QuestionVote> findQuestionVoteByLoggedUser(User loggedUser, Question question);
}
