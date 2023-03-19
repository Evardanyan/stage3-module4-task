package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ServiceErrorCodeMessage;
import com.mjc.school.service.mapper.CommentModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
public class CommentService implements BaseService<CommentDtoRequest, CommentDtoResponse, Long> {
//    private final JpaRepository<TagModel, Long> baseRepository;
    private final CommentRepository commentRepository;

    @Autowired
    private JpaRepository<NewsModel, Long> newsRepository;

    private CommentModelMapper mapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CommentDtoResponse> readCommentsByNewsId(Long newsId) {
        NewsModel newsModel = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), newsId)));
        List<CommentModel> commentsModels = newsModel.getComments();
        return mapper.modelListToDtoList(commentsModels);
    }


    @Override
    public Page<CommentDtoResponse> readAll(Pageable pageable) {
        Page<CommentModel> commentsPage = commentRepository.findAll(pageable);
        return commentsPage.map(commentsModel -> mapper.modelToDto(commentsModel));
    }

    @Override
    public TagDtoResponse readById(Long id) {
        TagModel tagModel = this.commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        return this.mapper.modelToDto(tagModel);
    }

    @Override
    public TagDtoResponse create(TagDtoRequest dtoRequest) {
        TagModel model = this.mapper.dtoToModel(dtoRequest);
        TagModel tagModel = this.commentRepository.save(model);
        return this.mapper.modelToDto(tagModel);
    }

    @Override
    public TagDtoResponse update(TagDtoRequest dtoRequest) {
        TagModel tagModel = commentRepository.findById(dtoRequest.id())
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id())));
        tagModel = mapper.dtoToModel(dtoRequest);
        tagModel = commentRepository.save(tagModel);
        return mapper.modelToDto(tagModel);
    }

    @Override
    public boolean deleteById(Long id) {
        commentRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        commentRepository.deleteById(id);
        return true;
    }
}
