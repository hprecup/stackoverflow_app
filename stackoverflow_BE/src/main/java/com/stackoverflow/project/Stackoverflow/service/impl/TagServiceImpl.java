package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.dto.RequestTagDTO;
import com.stackoverflow.project.Stackoverflow.mapper.TagMapper;
import com.stackoverflow.project.Stackoverflow.model.Tag;
import com.stackoverflow.project.Stackoverflow.repository.TagRepository;
import com.stackoverflow.project.Stackoverflow.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    private TagMapper  tagMapper;

    public List<RequestTagDTO> getTags() {
        List<Tag> tags = tagRepository.findAll();
        List<RequestTagDTO> requestTags = new ArrayList<>();
        for(Tag tag : tags) {
            RequestTagDTO requestTagDTO = new RequestTagDTO();
            tagMapper.tagToRequestTagDTO(requestTagDTO, tag);
            requestTags.add(requestTagDTO);
        }
        return requestTags;
    }

    public RequestTagDTO insertTag(String tagName) {
        List<Tag> existentTags = tagRepository.findAll();
        for(Tag tag : existentTags){
            if(tag.getTagName().equals(tagName))
                return null;
        }
        Tag createdTag = new Tag();
        createdTag.setTagName(tagName);
        tagRepository.save(createdTag);
        RequestTagDTO requestTagDTO = new RequestTagDTO();
        tagMapper.tagToRequestTagDTO(requestTagDTO, createdTag);
        return requestTagDTO;
    }
}
