package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorModelMapper.class})
public interface NewsModelMapper {

    public List<NewsDtoResponse> modelListToDtoList(List<NewsModel> var1);


    @Mapping(target = "authorId", source = "authorModel.id")
    @Mapping(target = "tagList", source = "tagModels")
//    @Mapping(target = "comments", source = "commentId", qualifiedByName = "commentIdToCommentModel")
    public NewsDtoResponse modelToDto(NewsModel var1);

//    @Named("commentIdToCommentModel")
//    default List<CommentModel> commentIdToCommentModel(Long commentId) {
//        if (commentId == null) {
//            return Collections.emptyList();
//        } else {
//            return Collections.singletonList(new CommentModel(commentId));
//        }
//    }


    @Mappings(value = {@Mapping(target = "createDate", ignore = true), @Mapping(target = "lastUpdatedDate", ignore = true),
            @Mapping(target="comments", ignore = true), @Mapping(target = "authorModel.id", source = "authorId")})
    @Mapping(target = "tagModels", source = "tagId", qualifiedByName = "tagIdToTagModel")

    public NewsModel dtoToModel(NewsDtoRequest var1);


    @Named("tagIdToTagModel")
    default List<TagModel> tagIdToTagModel(Long tagId) {
        if (tagId == null) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(new TagModel(tagId));
        }
    }

}
