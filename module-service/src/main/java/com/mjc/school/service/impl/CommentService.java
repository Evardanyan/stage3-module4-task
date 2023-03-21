package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.NewsRepository;
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

//    @Autowired
//    private JpaRepository<NewsModel, Long> newsRepository;
//    @Autowired
    private NewsRepository newsRepository;

    private CommentModelMapper mapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository, CommentModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
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
    public CommentDtoResponse readById(Long id) {
        CommentModel commentModel = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.COMMENT_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        return mapper.modelToDto(commentModel);
    }

    @Override
    public CommentDtoResponse create(CommentDtoRequest dtoRequest) {
        CommentModel model = mapper.dtoToModel(dtoRequest);
        CommentModel commentModel = commentRepository.save(model);
        return mapper.modelToDto(commentModel);
    }


    @Override
    public CommentDtoResponse update(CommentDtoRequest dtoRequest) {
        CommentModel commentModel = commentRepository.findById(dtoRequest.id())
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.COMMENT_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id())));
        commentModel = mapper.dtoToModel(dtoRequest);
        commentModel = commentRepository.save(commentModel);
        return mapper.modelToDto(commentModel);
    }

    @Override
    public boolean deleteById(Long id) {
        commentRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException(String.format(ServiceErrorCodeMessage.COMMENT_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        commentRepository.deleteById(id);
        return true;
    }
}

