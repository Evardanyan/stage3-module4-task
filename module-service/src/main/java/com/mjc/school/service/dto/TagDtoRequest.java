package com.mjc.school.service.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record TagDtoRequest(@Digits(integer = 10, fraction = 0) Long id, @NotBlank @Size(min = 3, max = 15) String name) {
    public TagDtoRequest(String name) {
        this(null, name);
    }
}
