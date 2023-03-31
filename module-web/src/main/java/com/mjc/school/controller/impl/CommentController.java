package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.*;
import io.swagger.annotations.*;
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
@Api(tags = "Comment Management", description = "Operations related to comments")
public class CommentController implements BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    private final BaseService<CommentDtoRequest, CommentDtoResponse, Long> service;

    public CommentController(BaseService<CommentDtoRequest, CommentDtoResponse, Long> service) {
        this.service = service;
    }


    @Override
    @GetMapping
    @ApiOperation(value = "get a list of all news", response = CommentDtoResponse.class, responseContainer = "Page")
    public ResponseEntity<Page<CommentDtoResponse>> readAll(
            @RequestParam(value = "page", defaultValue = "0")
            @ApiParam(value = "Page number of the results", defaultValue = "10") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "content") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        PageRequest pageRequest = service.buildPageRequest(page, size, sort, direction);

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
    @ApiOperation(value = "Create a new comment", response = NewsDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new  news"),
            @ApiResponse(code = 400, message = "Invalid news data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CommentDtoResponse> create(@Valid @RequestBody CommentDtoRequest createRequest) {
        CommentDtoResponse commentDtoResponse = service.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoResponse);
    }

    @Override
    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing comment", response = NewsDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the news"),
            @ApiResponse(code = 400, message = "Invalid author data"),
            @ApiResponse(code = 404, message = "News not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CommentDtoResponse> update(@PathVariable Long id, @Valid @RequestBody CommentDtoRequest updateRequest) {
        CommentDtoRequest updatedRequest = new CommentDtoRequest(id, updateRequest.content());
        CommentDtoResponse commentDtoResponse = service.update(updatedRequest);
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
    @ApiOperation(value = "Delete an comment by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the comment"),
            @ApiResponse(code = 404, message = "Comment not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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
