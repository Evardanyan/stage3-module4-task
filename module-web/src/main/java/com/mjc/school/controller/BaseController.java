package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

public interface BaseController<T, R, K> {

    ResponseEntity<List<R>> readAll();

    ResponseEntity<R> readById(@Valid K id);

   default ResponseEntity<List<R>> readTagsByNewsId(@Valid K id) {
       return ResponseEntity.notFound().build();
   }

   default ResponseEntity<R> readAuthorByNewsId(@Valid K id) {
        return ResponseEntity.notFound().build();
    }

    default ResponseEntity<R> getNewsByParams(@Valid String tagName,@Valid Long tagId,@Valid String authorName,@Valid String title,@Valid String content) {
        return ResponseEntity.notFound().build();
    }

    ResponseEntity<R> create(@Valid T createRequest);

    ResponseEntity<R> update(@Valid T updateRequest);

    ResponseEntity<Void> deleteById(@Valid K id);

}

