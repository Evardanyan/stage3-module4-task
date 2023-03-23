package com.mjc.school.service.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public record CommentDtoRequest(Long id, @NotBlank @Size(min = 3, max = 15) String content, Long newsId) {

    public CommentDtoRequest(Long id, String content) {
        this(id, content, null);
    }
}
