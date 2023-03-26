package com.mjc.school.service.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

//public record NewsDtoRequest(@Positive @Digits(integer = 10, fraction = 0) Long id, @NotBlank  @Size(min = 5, max = 30) String title, @NotBlank  @Size(min = 5, max = 255) String content, Long authorId, Long tagId) {
public record NewsDtoRequest(
        Long id,
        @NotBlank(message = "News Title cannot be blank")
        @Size(min = 5, max = 30, message = "News Title must be between 5 and 30 characters")
        String title,
        @NotBlank(message = "News Content cannot be blank")
        @Size(min = 5, max = 255, message = "News Content must be between 5 and 255 characters")
        String content,
        Long authorId,
        Long tagId) {

    public NewsDtoRequest(String title, String content, Long authorId) {
        this(null, title, content, authorId, null);
    }

    public NewsDtoRequest(Long id, String title, String content, Long authorId) {
        this(id, title, content, authorId, null);
    }

}

