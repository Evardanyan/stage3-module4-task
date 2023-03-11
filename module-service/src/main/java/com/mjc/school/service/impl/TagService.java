package com.mjc.school.service.impl;

import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.ValidateTagDto;
import com.mjc.school.service.annotation.ValidateTagId;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ServiceErrorCodeMessage;
import com.mjc.school.service.mapper.TagModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService implements BaseService<TagDtoRequest, TagDtoResponse, Long> {
    private final JpaRepository<TagModel, Long> baseRepository;

    @Autowired
    private JpaRepository<NewsModel, Long> newsRepository;

    private TagModelMapper mapper;

    @Autowired
    public TagService(JpaRepository<TagModel, Long> baseRepository, TagModelMapper mapper) {
        this.baseRepository = baseRepository;
        this.mapper = mapper;
    }

//    @ValidateTagId
    public List<TagDtoResponse> readTagsByNewsId(Long newsId) {
        Optional<NewsModel> newsModelOptional = newsRepository.findById(newsId);
        if (newsModelOptional.isPresent()) {
            List<TagModel> tagModels = newsModelOptional.get().getTagModels();
            return mapper.modelListToDtoList(tagModels);

        }
        throw new NotFoundException(String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), newsId));

    }

    @Override
    public List<TagDtoResponse> readAll() {
        return this.mapper.modelListToDtoList(this.baseRepository.findAll());
    }

    @Override
//    @ValidateTagId
    public TagDtoResponse readById(Long id) {
        Optional<TagModel> tagModelOptional = this.baseRepository.findById(id);
        if (tagModelOptional.isPresent()) {
            TagModel tagModel = tagModelOptional.get();
            return this.mapper.modelToDto(tagModel);
        }
        throw new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id));
    }

    @Override
//    @ValidateTagDto
    public TagDtoResponse create(TagDtoRequest dtoRequest) {
        if (!this.baseRepository.existsById(dtoRequest.id())) {
            TagModel model = this.mapper.dtoToModel(dtoRequest);
            TagModel tagModel = this.baseRepository.save(model);
            return this.mapper.modelToDto(tagModel);
        }
        throw new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_ALREADY_EXIST.getCodeMsg(), dtoRequest.id()));
    }


    @Override
//    @ValidateTagId
//    @ValidateTagDto
    public TagDtoResponse update(TagDtoRequest dtoRequest) {
        Optional<TagModel> tagModelOptional = this.baseRepository.findById(dtoRequest.id());
        if (tagModelOptional.isPresent()) {
            TagModel model = this.mapper.dtoToModel(dtoRequest);
            TagModel tagModel = this.baseRepository.save(model);
            return this.mapper.modelToDto(tagModel);
        }
        throw new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id()));

    }

    @Override
//    @ValidateTagId
    public boolean deleteById(Long id) {
        Optional<TagModel> tagModelOptional = this.baseRepository.findById(id);
        if (tagModelOptional.isPresent()) {
            this.baseRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id));
    }

    @Override
    public TagDtoResponse readAuthorByNewsId(Long id) {
        return null;
    }

    @Override
    public TagDtoResponse getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
        return null;
    }
}

