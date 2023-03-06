package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T, R, K> {

    ResponseEntity<List<R>> readAll();

    ResponseEntity<R> readById(K id);

   default ResponseEntity<List<R>> readTagsByNewsId(K id) {
       return ResponseEntity.notFound().build();
   }

    ResponseEntity<R> readAuthorByNewsId(K id);

    default ResponseEntity<R> getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
        return ResponseEntity.notFound().build();
    }

    ResponseEntity<R> create(T createRequest);

    ResponseEntity<R> update(T updateRequest);

    ResponseEntity<Void> deleteById(K id);

}

