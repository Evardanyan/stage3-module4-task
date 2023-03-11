package com.mjc.school.service.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record NewsDtoRequest(@Digits(integer = 10, fraction = 0) Long id, @NotBlank  @Size(min = 5, max = 30) String title,@NotBlank  @Size(min = 5, max = 255) String content, Long authorId, Long tagId) {
    public NewsDtoRequest(String title, String content, Long authorId) {
        this(null, title, content, authorId, null);
    }

    public NewsDtoRequest(Long id, String title, String content, Long authorId) {
        this(id, title, content, authorId, null);
    }

}
