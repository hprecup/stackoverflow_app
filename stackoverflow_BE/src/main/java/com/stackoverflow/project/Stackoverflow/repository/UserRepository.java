package com.stackoverflow.project.Stackoverflow.repository;

import com.stackoverflow.project.Stackoverflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
