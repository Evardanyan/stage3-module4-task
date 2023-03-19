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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
public class TagService implements BaseService<TagDtoRequest, TagDtoResponse, Long> {
//    private final JpaRepository<TagModel, Long> baseRepository;
    private final TagRepository tagRepository;

    @Autowired
    private JpaRepository<NewsModel, Long> newsRepository;

    private TagModelMapper mapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagModelMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TagDtoResponse> readTagsByNewsId(Long newsId) {
        NewsModel newsModel = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), newsId)));
        List<TagModel> tagModels = newsModel.getTagModels();
        return mapper.modelListToDtoList(tagModels);
    }

//    @Override
//    public List<TagDtoResponse> readAll() {
//        return mapper.modelListToDtoList(this.tagrepository.findAll());
//    }


    @Override
    public Page<TagDtoResponse> readAll(Pageable pageable) {
        Page<TagModel> tagsPage = tagRepository.findAll(pageable);
        return tagsPage.map(tagsModel -> this.mapper.modelToDto(tagsModel));
    }

    @Override
    public TagDtoResponse readById(Long id) {
        TagModel tagModel = this.tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        return this.mapper.modelToDto(tagModel);
    }

    @Override
    public TagDtoResponse create(TagDtoRequest dtoRequest) {
        TagModel model = this.mapper.dtoToModel(dtoRequest);
        TagModel tagModel = this.tagRepository.save(model);
        return this.mapper.modelToDto(tagModel);
    }

    @Override
    public TagDtoResponse update(TagDtoRequest dtoRequest) {
        TagModel tagModel = tagRepository.findById(dtoRequest.id())
                .orElseThrow(() -> new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id())));
        tagModel = mapper.dtoToModel(dtoRequest);
        tagModel = tagRepository.save(tagModel);
        return mapper.modelToDto(tagModel);
    }

    @Override
    public boolean deleteById(Long id) {
        tagRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException(String.format(ServiceErrorCodeMessage.TAG_ID_DOES_NOT_EXIST.getCodeMsg(), id)));
        tagRepository.deleteById(id);
        return true;
    }
}

