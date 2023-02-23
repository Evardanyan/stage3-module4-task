package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {

    private final BaseService<TagDtoRequest, TagDtoResponse, Long> service;

    public TagController(BaseService<TagDtoRequest, TagDtoResponse, Long> service) {
        this.service = service;
    }

    @Override
    @CommandHandler(operation = "11")
    public List<TagDtoResponse> readAll() {
        return service.readAll();
    }

    @Override
    @CommandHandler(operation = "12")
    public TagDtoResponse readById(Long id) {
        return service.readById(id);
    }

    @Override
    @CommandHandler(operation = "13")
    public TagDtoResponse create(TagDtoRequest createRequest) {
        return service.create(createRequest);
    }

    @Override
    @CommandHandler(operation = "14")
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        return service.update(updateRequest);
    }

    @Override
    @CommandHandler(operation = "15")
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }

    @CommandHandler(operation = "16")
    public List<TagDtoResponse> readTagsByNewsId(Long id) {
        return service.readTagsByNewsId(id);
    }

    @Override
    public TagDtoResponse readAuthorByNewsId(Long id) {
        return null;
    }

    @Override
    public TagDtoResponse getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
        return null;
    }
}
