package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.annotation.OnDelete;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.ValidateNewsDto;
import com.mjc.school.service.annotation.ValidateNewsId;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ServiceErrorCodeMessage;
import com.mjc.school.service.mapper.NewsModelMapper;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final NewsRepository newsRepository;
    private final NewsModelMapper mapper;

    public NewsService(NewsRepository newsRepository, NewsModelMapper mapper) {
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return mapper.modelListToDtoList(newsRepository.findAll());
    }

    @Override
//    @ValidateNewsId
    public NewsDtoResponse readById(Long id) {
        NewsModel newsModel = newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), id)
                ));
        return mapper.modelToDto(newsModel);
    }

    @Override
//    @ValidateNewsDto
    public NewsDtoResponse create(NewsDtoRequest dtoRequest) {
        if (newsRepository.existsById(dtoRequest.id())) {
            throw new NotFoundException(
                    String.format(ServiceErrorCodeMessage.NEWS_ID_ALREADY_EXIST.getCodeMsg(), dtoRequest.id())
            );
        }
        NewsModel model = mapper.dtoToModel(dtoRequest);
        NewsModel createdNewsModel = newsRepository.save(model);
        return mapper.modelToDto(createdNewsModel);
    }

    @Override
//    @ValidateNewsId
//    @ValidateNewsDto
    public NewsDtoResponse update(NewsDtoRequest dtoRequest) {
        NewsModel newsModel = newsRepository.findById(dtoRequest.id())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), dtoRequest.id())
                ));
//        mapper.updateModelFromDto(dtoRequest, newsModel);
        mapper.dtoToModel(dtoRequest);
        NewsModel updatedNewsModel = newsRepository.save(newsModel);
        return mapper.modelToDto(updatedNewsModel);
    }

    @Override
//    @ValidateNewsId
//    @OnDelete
    public boolean deleteById(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new NotFoundException(
                    String.format(ServiceErrorCodeMessage.NEWS_ID_DOES_NOT_EXIST.getCodeMsg(), newsId)
            );
        }
        newsRepository.deleteById(newsId);
        return true;
    }

    @Override
    public List<NewsDtoResponse> readTagsByNewsId(Long id) {
        return new ArrayList<>();
    }

    @Override
    public NewsDtoResponse readAuthorByNewsId(Long id) {
        return null;
    }

//    public NewsDtoResponse getNewsByParams(String tagNames, Long tagIds, String authorName, String title, String content) {
//        List<NewsModel> newsModels = newsRepository.getNewsByParams(
//                tagNames == null ? null : Arrays.asList(tagNames.split(",")),
//                tagIds == null ? null : Collections.singletonList(tagIds),
//                authorName,
//                title,
//                content);
//        if (newsModels.isEmpty()) {
//            throw new NotFoundException("No news items found with the specified criteria.");
//        }
//        return this.mapper.modelToDto(newsModels.get(0));
//    }

        public NewsDtoResponse getNewsByParams(String tagNames, Long tagIds, String authorName, String title, String content) {
        NewsModel newsModels = newsRepository.getNewsByParams(
                tagNames == null ? null : Arrays.asList(tagNames.split(",")),
                tagIds == null ? null : Collections.singletonList(tagIds),
                authorName,
                title,
                content);
        if (newsModels.equals(null)) {
            throw new NotFoundException("No news items found with the specified criteria.");
        }
        return this.mapper.modelToDto(newsModels);
    }



//    @Override
//    public NewsDtoResponse getNewsByParams(String tagNames, Long tagIds, String authorName, String title, String content) {
//        NewsModel newsModel = newsRepository.getNewsByParams(tagNames, tagIds, authorName, title, content);
//        return mapper.modelToDto(newsModel);
//    }
}
