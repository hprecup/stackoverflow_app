package com.stackoverflow.project.Stackoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class StackoverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackoverflowApplication.class, args);
	}

}
