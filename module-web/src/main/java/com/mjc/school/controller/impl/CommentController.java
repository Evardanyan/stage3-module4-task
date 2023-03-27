package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/comments")
@Validated
public class CommentController implements BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    private final BaseService<CommentDtoRequest, CommentDtoResponse, Long> service;

    public CommentController(BaseService<CommentDtoRequest, CommentDtoResponse, Long> service) {
        this.service = service;
    }


    @Override
    @GetMapping
    public ResponseEntity<Page<CommentDtoResponse>> readAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "content") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort sortable = Sort.by(sort);
        if (direction.equalsIgnoreCase("desc")) {
            sortable = sortable.descending();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sortable);

        Page<CommentDtoResponse> commentsPage = service.readAll(pageRequest);

        return new ResponseEntity<>(commentsPage, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<CommentDtoResponse> readById(@Valid @PathVariable Long id) {
//        return ResponseEntity.ok(service.readById(id));
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

//    @Override
//    @PostMapping
//    public ResponseEntity<CommentDtoResponse> create(@Valid @RequestBody CommentDtoRequest createRequest) {
//        CommentDtoResponse commentDtoResponse = service.create(createRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoResponse);
//    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentDtoResponse> create(@Valid @RequestBody CommentDtoRequest createRequest) {
        CommentDtoResponse commentDtoResponse = service.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoResponse);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CommentDtoResponse> update(@PathVariable Long id, @Valid @RequestBody CommentDtoRequest updateRequest) {
        CommentDtoResponse commentDtoResponse = service.update(updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(commentDtoResponse);
    }

//    @Override
//    @DeleteMapping(value = "/{id:\\d+}")
//    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
//        service.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping("/news/{id}/comments")
    public ResponseEntity<List<CommentDtoResponse>> readCommentsByNewsId(@PathVariable Long id) {
        List<CommentDtoResponse> commentsByNewsId = service.readCommentsByNewsId(id);
        return ResponseEntity.ok(commentsByNewsId);
    }

}
