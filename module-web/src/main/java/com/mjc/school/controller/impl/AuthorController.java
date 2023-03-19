package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Page<AuthorDtoResponse>> readAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort sortable = Sort.by(sort);
        if (direction.equalsIgnoreCase("desc")) {
            sortable = sortable.descending();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sortable);

        Page<AuthorDtoResponse> authorsPage = service.readAll(pageRequest);

        return new ResponseEntity<>(authorsPage, HttpStatus.OK);
    }


    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<AuthorDtoResponse> readById(@PathVariable Long id) {
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
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/news/{id}/author")
    public ResponseEntity<AuthorDtoResponse> readAuthorByNewsId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(service.readAuthorByNewsId(id));
    }
}
