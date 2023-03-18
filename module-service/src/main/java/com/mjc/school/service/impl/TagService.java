package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ServiceErrorCodeMessage;
import com.mjc.school.service.mapper.TagModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class TagService implements BaseService<TagDtoRequest, TagDtoResponse, Long> {
//    private final JpaRepository<TagModel, Long> baseRepository;
    private final TagRepository tagrepository;

    @Autowired
    private JpaRepository<NewsModel, Long> newsRepository;

    private TagModelMapper mapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagModelMapper mapper) {
        this.tagrepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TagDtoResponse> readTagsByNewsId(Long newsId) {
        NewsModel newsModel = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), newsId)));
        List<TagModel> tagModels = newsModel.getTagModels();
        return mapper.modelListToDtoList(tagModels);
    }

    @Override
    public List<TagDtoResponse> readAll() {
        return mapper.modelListToDtoList(this.tagrepository.findAll());
    }

    @Override
    public TagDtoResponse readById(Long id) {
        TagModel tagModel = this.tagrepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        return this.mapper.modelToDto(tagModel);
    }

    @Override
    public TagDtoResponse create(TagDtoRequest dtoRequest) {
        TagModel model = this.mapper.dtoToModel(dtoRequest);
        TagModel tagModel = this.tagrepository.save(model);
        return this.mapper.modelToDto(tagModel);
    }

    @Override
    public TagDtoResponse update(TagDtoRequest dtoRequest) {
        TagModel tagModel = tagrepository.findById(dtoRequest.id())
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id())));
        tagModel = mapper.dtoToModel(dtoRequest);
        tagModel = tagrepository.save(tagModel);
        return mapper.modelToDto(tagModel);
    }

    @Override
    public boolean deleteById(Long id) {
        tagrepository.findById(id)
                .orElseThrow(() ->  new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        tagrepository.deleteById(id);
        return true;
    }
}

