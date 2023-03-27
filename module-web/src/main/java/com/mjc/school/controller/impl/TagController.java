package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.CommentDtoRequest;
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
@RequestMapping(value = "/api/v1/tags")
@Validated
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {

    private final BaseService<TagDtoRequest, TagDtoResponse, Long> service;

    public TagController(BaseService<TagDtoRequest, TagDtoResponse, Long> service) {
        this.service = service;
    }


    @Override
    @GetMapping
    public ResponseEntity<Page<TagDtoResponse>> readAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort sortable = Sort.by(sort);
        if (direction.equalsIgnoreCase("desc")) {
            sortable = sortable.descending();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sortable);

        Page<TagDtoResponse> tagsPage = service.readAll(pageRequest);

        return new ResponseEntity<>(tagsPage, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<TagDtoResponse> readById(@Valid @PathVariable Long id) {
//        return ResponseEntity.ok(service.readById(id));
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagDtoResponse> create(@Valid @RequestBody TagDtoRequest createRequest) {
        TagDtoResponse tagDtoResponse = service.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDtoResponse);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TagDtoResponse> update(@PathVariable Long id, @Valid @RequestBody TagDtoRequest updateRequest) {
       TagDtoRequest updatedRequest = new TagDtoRequest(id, updateRequest.name());
        TagDtoResponse tagDtoResponse = service.update(updatedRequest);
        return ResponseEntity.status(HttpStatus.OK).body(tagDtoResponse);
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

    @GetMapping("/news/{id}/tag")
    public ResponseEntity<List<TagDtoResponse>> readTagsByNewsId(@Valid @PathVariable Long id) {
        List<TagDtoResponse> tagsByNewsId = service.readTagsByNewsId(id);
        return ResponseEntity.ok(tagsByNewsId);
    }

}
