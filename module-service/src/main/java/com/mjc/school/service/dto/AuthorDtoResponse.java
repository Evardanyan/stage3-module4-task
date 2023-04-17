package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

public record AuthorDtoResponse(Long id, String name, @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") OffsetDateTime createDate,@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") OffsetDateTime lastUpdatedDate) {

    @Override
    public String toString() {
        if (id == null && createDate == null && lastUpdatedDate == null) {
            return "AuthorDtoResponse{name='" + name + '\'' +
                    '}';
        } else {
            return "AuthorDtoResponse{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", createDate=" + createDate +
                    ", lastUpdatedDate=" + lastUpdatedDate +
                    '}';
        }
    }
}
