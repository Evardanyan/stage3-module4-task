package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(value = "/api/v1/authors", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
@RequestMapping(value = "/api/v1/authors")
@Validated
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service;

    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AuthorDtoResponse>> readAll() {
//        List<AuthorDtoResponse> authors = service.readAll();
//        return ResponseEntity.ok(authors);
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<AuthorDtoResponse> readById(@Valid @PathVariable Long id) {
//        return ResponseEntity.ok(service.readById(id));
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<AuthorDtoResponse> create(@Valid @RequestBody AuthorDtoRequest createRequest) {
        AuthorDtoResponse authorDtoResponse = service.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDtoResponse);
    }

    @Override
    @PutMapping
    public ResponseEntity<AuthorDtoResponse> update(@Valid @RequestBody AuthorDtoRequest updateRequest) {
        AuthorDtoResponse authorDtoResponse = service.update(updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authorDtoResponse);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/news/{id}/author")
    public ResponseEntity<AuthorDtoResponse> readAuthorByNewsId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(service.readAuthorByNewsId(id));
    }
}
