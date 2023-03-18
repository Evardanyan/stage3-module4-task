package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.ValidateAuthorDto;
import com.mjc.school.service.annotation.ValidateAuthorId;
import com.mjc.school.service.annotation.ValidateTagId;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
//import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ServiceErrorCodeMessage;
import com.mjc.school.service.mapper.AuthorModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

//    private final JpaRepository<AuthorModel, Long> authorRepository;
    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;
    private final AuthorModelMapper mapper;

//    public AuthorService(JpaRepository<AuthorModel, Long> authorRepository, NewsRepository newsRepository, AuthorModelMapper mapper) {
//        this.authorRepository = authorRepository;
//        this.newsRepository = newsRepository;
//        this.mapper = mapper;
//    }

    public AuthorService(AuthorRepository authorRepository, NewsRepository newsRepository, AuthorModelMapper mapper) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        List<AuthorModel> authorModels = this.authorRepository.findAll();
        return this.mapper.modelListToDtoList(authorModels);
    }

//    @Override
////    @ValidateAuthorId
//    public AuthorDtoResponse readById(Long id) {
//        Optional<AuthorModel> authorModelOptional = this.authorRepository.findById(id);
//        if (authorModelOptional.isPresent()) {
//            AuthorModel authorModel = authorModelOptional.get();
//            return this.mapper.modelToDto(authorModel);
//        }
//        throw new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_DOES_NOT_EXIST.getCodeMsg(), id));
//    }

    @Override
//    @ValidateAuthorId
    public AuthorDtoResponse readById(Long id) {
        AuthorModel authorModel = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
            return this.mapper.modelToDto(authorModel);
    }

//    public AuthorDtoResponse readAuthorByNewsId(Long newsId) {
//        Optional<NewsModel> newsModelOptional = newsRepository.findById(newsId);
//        if (newsModelOptional.isPresent()) {
//            AuthorModel authorModel = newsModelOptional.get().getAuthorModel();
//            return mapper.modelAuthorNameToDto(authorModel);
//        }
//        throw new NotFoundException(String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), newsId));
//    }

    public AuthorDtoResponse readAuthorByNewsId(Long newsId) {
        NewsModel newsModel = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), newsId)));
                AuthorModel authorModel = newsModel.getAuthorModel();
            return mapper.modelAuthorNameToDto(authorModel);
        }



//    @Override
////    @ValidateAuthorDto
//    public AuthorDtoResponse create(AuthorDtoRequest dtoRequest) {
//        if (!this.authorRepository.existsById(dtoRequest.id())) {
//            AuthorModel model = this.mapper.dtoToModel(dtoRequest);
//            AuthorModel authorModel = this.authorRepository.save(model);
//            return this.mapper.modelToDto(authorModel);
//        }
//        throw new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_ALREADY_EXIST.getCodeMsg(), dtoRequest.id()));
//    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest dtoRequest) {
        // Check if an author with the same ID or name already exists
//        authorRepository.findById(dtoRequest.id())
//                .ifPresent(existingAuthor -> {
//                    throw new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_ALREADY_EXIST.getCodeMsg(), dtoRequest.id()));
//                });

//        authorRepository.findByName(dtoRequest.name())
//                .ifPresent(existingAuthor -> {
//                    throw new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_NAME_ALREADY_EXIST.getCodeMsg(), dtoRequest.name()));
//                });
        AuthorModel model = mapper.dtoToModel(dtoRequest);
        AuthorModel authorModel = authorRepository.save(model);
        return this.mapper.modelToDto(authorModel);
    }


//    @Override
////    @ValidateAuthorId
////    @ValidateAuthorDto
//    public AuthorDtoResponse update(AuthorDtoRequest dtoRequest) {
//        if (this.authorRepository.existsById(dtoRequest.id())) {
//            AuthorModel model = this.mapper.dtoToModel(dtoRequest);
//            AuthorModel authorModel = this.authorRepository.save(model);
//            return this.mapper.modelToDto(authorModel);
//        }
//        throw new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id()));
//
//    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest dtoRequest) {
        AuthorModel author = authorRepository.findById(dtoRequest.id())
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id())));
        author.setName(dtoRequest.name());
        AuthorModel updatedAuthorModel = authorRepository.save(author);
        return mapper.modelToDto(updatedAuthorModel);
    }


//    @Override
////    @ValidateAuthorId
//    public boolean deleteById(Long id) {
//        if (this.authorRepository.existsById(id)) {
//            this.authorRepository.deleteById(id);
//            return true;
//        }
//        throw new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_DOES_NOT_EXIST.getCodeMsg(), id));
//    }

    @Override
    public boolean deleteById(Long id) {
        AuthorModel author = this.authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.AUTHOR_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        authorRepository.deleteById(id);
        return true;
    }


    @Override
    public List<AuthorDtoResponse> readTagsByNewsId(Long id) {
        return new ArrayList<>();
    }
}






