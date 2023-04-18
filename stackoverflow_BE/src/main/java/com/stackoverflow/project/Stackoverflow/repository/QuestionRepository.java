package com.stackoverflow.project.Stackoverflow.repository;

import com.stackoverflow.project.Stackoverflow.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q ORDER BY q.creationDateTime DESC")
    List<Question> findAllSortedByDate();

}
