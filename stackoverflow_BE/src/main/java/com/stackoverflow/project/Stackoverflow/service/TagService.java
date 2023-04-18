package com.stackoverflow.project.Stackoverflow.service;

import com.stackoverflow.project.Stackoverflow.dto.RequestTagDTO;
import com.stackoverflow.project.Stackoverflow.model.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {
    List<RequestTagDTO> getTags();

    RequestTagDTO insertTag(String tagName);
}
