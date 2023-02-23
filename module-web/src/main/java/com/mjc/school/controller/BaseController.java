package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.List;

public interface BaseController<T, R, K> {

    List<R> readAll();

    R readById(K id);

    List<R> readTagsByNewsId(K id);

    R readAuthorByNewsId(K id);

    R getNewsByParams(String tagName, Long tagId, String authorName, String title, String content);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);

}
