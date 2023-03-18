package com.mjc.school.service;


import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.mapper.NewsModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private NewsModelMapper mapper;

    @InjectMocks
    private NewsService newsService;

    private NewsDtoRequest newsDtoRequest;

    private NewsDtoResponse newsDtoResponse;

    private NewsModel newsModel;

    private AuthorModel authorModel;

    private List<NewsModel> newsModels;

    private List<NewsDtoResponse> expectedDtos;

    Date now = new Date();

    @BeforeEach
    void setUp() {

        newsDtoRequest = new NewsDtoRequest(1L, "Test News Title", "Test News Content", 1L);

        newsDtoResponse = new NewsDtoResponse(1L, "Test News Title", "Test News Content", now, 1L);

        authorModel = new AuthorModel();
        authorModel.setId(1L);
        authorModel.setName("Author name");
        authorModel.setCreateDate(now);

        AuthorModel authorSecondModel = new AuthorModel();
        authorSecondModel.setId(2L);
        authorSecondModel.setName("Author name 2");
        authorSecondModel.setCreateDate(now);


        newsModel = new NewsModel();
        newsModel.setId(1L);
        newsModel.setTitle("Test News Title");
        newsModel.setContent("Test News Content");
        newsModel.setAuthorModel(authorModel);
        newsModel.setCreateDate(now);


        List<NewsModel> newsModels = Arrays.asList(
                new NewsModel(1L, "News 1", "This is news 1", authorModel),
                new NewsModel(2L, "News 2", "This is news 2", authorSecondModel)
        );
        List<NewsDtoResponse> expectedDtos = Arrays.asList(
                new NewsDtoResponse(1L, "Test News Title", "Test News Content", now, 1L),
                new NewsDtoResponse(2L, "Test News Title 2", "Test News Content 2", now, 2L)
        );
    }

    @DisplayName("JUnit test for create method")
    @Test
    void createNews_Success() {
        when(mapper.dtoToModel(any(NewsDtoRequest.class))).thenReturn(newsModel);
        when(newsRepository.save(any(NewsModel.class))).thenReturn(newsModel);
        when(mapper.modelToDto(any(NewsModel.class))).thenReturn(newsDtoResponse);

        NewsDtoResponse result = newsService.create(newsDtoRequest);

        assertNotNull(result);
        assertEquals(newsDtoResponse.id(), result.id());
        assertEquals(newsDtoResponse.title(), result.title());
        assertEquals(newsDtoResponse.content(), result.content());
        assertEquals(newsDtoResponse.authorId(), result.authorId());
        assertEquals(newsDtoResponse.createDate(), result.createDate());
    }

    @DisplayName("JUnit test for update method")
    @Test
    void updateNews_Success() {
        Long newsId = 1L;
        Long authorId = 1L;

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsModel));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorModel));
        when(newsRepository.save(newsModel)).thenReturn(newsModel);
        when(mapper.modelToDto(newsModel)).thenReturn(newsDtoResponse);

        NewsDtoResponse result = newsService.update(newsDtoRequest);

        assertNotNull(result);
        assertEquals(newsDtoResponse.id(), result.id());
        assertEquals(newsDtoResponse.title(), result.title());
        assertEquals(newsDtoResponse.content(), result.content());
        assertEquals(newsDtoResponse.authorId(), result.authorId());
        assertEquals(newsDtoResponse.createDate(), result.createDate());

        verify(newsRepository, times(1)).findById(newsId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(newsRepository, times(1)).save(newsModel);
        verify(mapper, times(1)).modelToDto(newsModel);
    }



    @DisplayName("JUnit test for readAll method")
    @Test
    void testReadAll() {

        when(newsRepository.findAll()).thenReturn(newsModels);
        when(mapper.modelListToDtoList(newsModels)).thenReturn(expectedDtos);

        List<NewsDtoResponse> actualDtos = newsService.readAll();

        assertEquals(expectedDtos, actualDtos);
        verify(newsRepository).findAll();
        verify(mapper).modelListToDtoList(newsModels);
    }

    @DisplayName("JUnit test for readById method")
    @Test
    void testReadById() {
        Long id = 1L;

        NewsDtoResponse expectedDto = new NewsDtoResponse(1L, "Test News Title", "Test News Content", now, 1L);
        when(newsRepository.findById(id)).thenReturn(Optional.of(newsModel));
        when(mapper.modelToDto(newsModel)).thenReturn(expectedDto);

        NewsDtoResponse actualDto = newsService.readById(id);

        assertEquals(expectedDto, actualDto);
        verify(newsRepository).findById(id);
        verify(mapper).modelToDto(newsModel);
    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    void testDeleteById() {
        Long id = 1L;
        when(newsRepository.existsById(id)).thenReturn(true);
        doNothing().when(newsRepository).deleteById(id);

        boolean deleted = newsService.deleteById(id);

        assertTrue(deleted);
        verify(newsRepository).existsById(id);
        verify(newsRepository).deleteById(id);
    }

    @DisplayName("JUnit test for deleteByIdNonExisting")
    @Test
    void testDeleteByIdNonExisting() {
        Long id = 1L;
        when(newsRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> newsService.deleteById(id));
        verify(newsRepository, times(1)).existsById(id);
        verify(newsRepository, never()).deleteById(id);
    }
}
