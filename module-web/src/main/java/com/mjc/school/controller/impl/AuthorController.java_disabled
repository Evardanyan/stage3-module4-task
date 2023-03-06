package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service;

    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service) {
        this.service = service;
    }

    @Override
    @CommandHandler(operation = "6")
    public List<AuthorDtoResponse> readAll() {
        return service.readAll();
    }

    @Override
    @CommandHandler(operation = "7")
    public AuthorDtoResponse readById(Long id) {
        return service.readById(id);
    }

    @Override
    @CommandHandler(operation = "8")
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        return service.create(createRequest);
    }

    @Override
    @CommandHandler(operation = "9")
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        return service.update(updateRequest);
    }

    @Override
    @CommandHandler(operation = "10")
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }

    @CommandHandler(operation = "17")
    public AuthorDtoResponse readAuthorByNewsId(Long id) {
        return service.readAuthorByNewsId(id);
    }

    @Override
    public List<AuthorDtoResponse> readTagsByNewsId(Long id) {
        return new ArrayList<>();
    }

    @Override
    public AuthorDtoResponse getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
        return null;
    }
}
