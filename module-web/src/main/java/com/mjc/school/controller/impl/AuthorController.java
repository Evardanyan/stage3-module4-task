package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/authors", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service;

    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<AuthorDtoResponse>> readAll() {
        List<AuthorDtoResponse> authors = service.readAll();
        return ResponseEntity.ok(authors);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<AuthorDtoResponse> readById(@PathVariable Long id) {
        return ResponseEntity.ok(service.readById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<AuthorDtoResponse> create(@RequestBody AuthorDtoRequest createRequest) {
        AuthorDtoResponse authorDtoResponse = service.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDtoResponse);
    }

    @Override
    @PutMapping
    public ResponseEntity<AuthorDtoResponse> update(@RequestBody AuthorDtoRequest updateRequest) {
        AuthorDtoResponse authorDtoResponse = service.update(updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authorDtoResponse);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<Void> deleteById(@NotNull @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/news/{id}/author")
    public ResponseEntity<AuthorDtoResponse> readAuthorByNewsId(@PathVariable Long id) {
        return ResponseEntity.ok(service.readAuthorByNewsId(id));
    }
}
