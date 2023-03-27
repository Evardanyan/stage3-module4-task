package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/news")
@Validated
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> service;

    public NewsController(BaseService<NewsDtoRequest, NewsDtoResponse, Long> service) {
        this.service = service;
    }


    @Override
    @GetMapping
    public ResponseEntity<Page<NewsDtoResponse>> readAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort sortable = Sort.by(sort);
        if (direction.equalsIgnoreCase("desc")) {
            sortable = sortable.descending();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sortable);

        Page<NewsDtoResponse> newsPage = service.readAll(pageRequest);

        return new ResponseEntity<>(newsPage, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<NewsDtoResponse> readById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.readById(id));
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<NewsDtoResponse> create(@Valid @RequestBody NewsDtoRequest createRequest) {
        NewsDtoResponse newsDtoResponse = service.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsDtoResponse);
    }

    @Override
    @PutMapping
    public ResponseEntity<NewsDtoResponse> update(@Valid @RequestBody NewsDtoRequest updateRequest) {
        NewsDtoResponse newsDtoResponse = service.update(updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(newsDtoResponse);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<NewsDtoResponse> getNewsByParams(@Valid @RequestParam(required = false) String tagName,
                                                           @Valid @RequestParam(required = false) Long tagId,
                                                           @Valid @RequestParam(required = false) String authorName,
                                                           @Valid @RequestParam(required = false) String title,
                                                           @Valid @RequestParam(required = false) String content) {
        NewsDtoResponse response = service.getNewsByParams(tagName, tagId, authorName, title, content);
        HttpStatus status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

}
