package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tags")
@Validated
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {

    private final BaseService<TagDtoRequest, TagDtoResponse, Long> service;

    public TagController(BaseService<TagDtoRequest, TagDtoResponse, Long> service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TagDtoResponse>> readAll() {
//        List<TagDtoResponse> tags = service.readAll();
//        return ResponseEntity.ok(tags);
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<TagDtoResponse> readById(@Valid @PathVariable Long id) {
//        return ResponseEntity.ok(service.readById(id));
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<TagDtoResponse> create(@Valid @RequestBody TagDtoRequest createRequest) {
        TagDtoResponse tagDtoResponse = service.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDtoResponse);
    }

    @Override
    @PutMapping
    public ResponseEntity<TagDtoResponse> update(@Valid @RequestBody TagDtoRequest updateRequest) {
        TagDtoResponse tagDtoResponse = service.update(updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(tagDtoResponse);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/news/{id}/tags")
    public ResponseEntity<List<TagDtoResponse>> readTagsByNewsId(@Valid @PathVariable Long id) {
        List<TagDtoResponse> tagsByNewsId = service.readTagsByNewsId(id);
        return ResponseEntity.ok(tagsByNewsId);
    }

}
