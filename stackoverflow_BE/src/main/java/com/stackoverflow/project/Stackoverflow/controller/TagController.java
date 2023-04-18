package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.RequestTagDTO;
import com.stackoverflow.project.Stackoverflow.model.Tag;
import com.stackoverflow.project.Stackoverflow.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/tag")
public class TagController {
    private TagService tagService;

    @GetMapping
    public List<RequestTagDTO> getTags() {
        return tagService.getTags();
    }

    @PostMapping
    public RequestTagDTO insertTag(@RequestBody String tagName) {
        return tagService.insertTag(tagName);
    }
}
