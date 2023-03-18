package com.mjc.school.service;

import java.util.Collections;
import java.util.List;

public interface BaseService<T, R, K> {
    List<R> readAll();

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);

   default List<R> readTagsByNewsId(K id) {
       return Collections.emptyList();
   }

   default R readAuthorByNewsId(K id) {
       return null;
    }

   default R getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
       return null;
   }
}
