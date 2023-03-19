package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentModelMapper {
    public List<CommentDtoResponse> modelListToDtoList(List<CommentModel> var1);

    public CommentDtoResponse modelToDto(TagModel var1);

    @Mapping(target = "news.id", source = "newsId")
    public CommentModel dtoToModel(TagDtoRequest var1);

}
