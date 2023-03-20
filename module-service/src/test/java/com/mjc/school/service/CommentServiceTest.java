package com.mjc.school.service;


import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.impl.CommentService;
import com.mjc.school.service.mapper.CommentModelMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentModelMapper mapper;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentModel commentModel;
    private CommentDtoResponse commentDtoResponse;

    Date now = new Date();


    @DisplayName("JUnit test for create method")
    @Test
    void shouldCreateCommentModelSuccessfully() throws NotFoundException {

        NewsModel newsModel = new NewsModel();
        CommentDtoRequest commentDtoRequest = new CommentDtoRequest(1L, "New Comment", 1L);
        CommentModel commentModel = new CommentModel(1L, "New Comment", newsModel);
        CommentModel savedCommentModel = new CommentModel(1L, "New Comment", newsModel);

        when(mapper.dtoToModel(commentDtoRequest)).thenReturn(commentModel);
        when(commentRepository.save(commentModel)).thenReturn(savedCommentModel);
        when(mapper.modelToDto(savedCommentModel)).thenReturn(new CommentDtoResponse(1L, "New Comment"));

        CommentDtoResponse result = commentService.create(commentDtoRequest);

        assertEquals(new CommentDtoResponse(1L, "New Comment"), result);
        verify(mapper, times(1)).dtoToModel(commentDtoRequest);
        verify(commentRepository, times(1)).save(commentModel);
        verify(mapper, times(1)).modelToDto(savedCommentModel);
    }


//    @DisplayName("JUnit test for update method")
//    @Test
//    void shouldUpdateAuthorModelSuccessFully() throws NotFoundException {
//        Long id = 1L;
//
//        AuthorModel authorModel = new AuthorModel();
//        authorModel.setId(id);
//        authorModel.setName("Author Name");
//        authorModel.setCreateDate(now);
//        authorModel.setLastUpdatedDate(now);
//
//        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(id, "Author Name");
//
//        AuthorDtoResponse authorDtoResponse = new AuthorDtoResponse(id, "Author Name", now, now);
//
//        when(commentRepository.findById(id)).thenReturn(Optional.of(authorModel));
//        when(commentRepository.save(any(AuthorModel.class))).thenReturn(authorModel);
//        when(mapper.modelToDto(any(AuthorModel.class))).thenReturn(authorDtoResponse);
//
//        AuthorDtoResponse result = commentService.update(authorDtoRequest);
//
//        assertEquals(authorDtoResponse, result);
//    }
//
//    @DisplayName("JUnit test for readAll method")
//    @Test
//    void testReadAll() {
//        List<AuthorModel> authorModels = new ArrayList<>();
//        authorModels.add(new AuthorModel(1L, "Author Name 1"));
//        authorModels.add(new AuthorModel(2L, "Author Name 2"));
//
//        List<AuthorDtoResponse> authorDtoResponses = new ArrayList<>();
//        authorDtoResponses.add(new AuthorDtoResponse(1L, "Author Name 1", now, now));
//        authorDtoResponses.add(new AuthorDtoResponse(2L, "Author Name 2", now, now));
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
//        Page<AuthorModel> authorPage = new PageImpl<>(authorModels, pageable, authorModels.size());
//        when(commentRepository.findAll(pageable)).thenReturn(authorPage);
//        when(mapper.modelToDto(authorModels.get(0))).thenReturn(authorDtoResponses.get(0));
//        when(mapper.modelToDto(authorModels.get(1))).thenReturn(authorDtoResponses.get(1));
//
//        Page<AuthorDtoResponse> result = commentService.readAll(pageable);
//
//        assertEquals(authorDtoResponses, result.getContent());
//    }
//
//
//
//    @DisplayName("JUnit test for readById method")
//    @Test
//    void readByIdTest() {
//        Long id = 1L;
//
//        AuthorModel authorModel = new AuthorModel();
//        authorModel.setId(id);
//        authorModel.setName("Author Name");
//        authorModel.setCreateDate(now);
//        authorModel.setLastUpdatedDate(now);
//
//        AuthorDtoResponse authorDtoResponse = new AuthorDtoResponse(id, "Author Name", now, now);
//
//        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(authorModel));
//        when(mapper.modelToDto(authorModel)).thenReturn(authorDtoResponse);
//
//        AuthorDtoResponse result = commentService.readById(1L);
//
//        assertEquals(authorDtoResponse, result);
//    }
//
//
//    @DisplayName("JUnit test for deleteById method")
//    @Test
//    void testDeleteById() {
//        Long id = 1L;
//        AuthorModel authorModel = new AuthorModel();
//        authorModel.setId(id);
//
//        when(commentRepository.findById(id)).thenReturn(Optional.of(authorModel));
//        doNothing().when(commentRepository).deleteById(id);
//
//        boolean deleted = commentService.deleteById(id);
//
//        assertTrue(deleted);
//        verify(commentRepository, times(1)).findById(id);
//        verify(commentRepository, times(1)).deleteById(id);
//    }
//
//    @DisplayName("JUnit test for deleteByIdNonExisting")
//    @Test
//    void testDeleteByIdNonExisting() {
//        Long id = 1L;
//        when(commentRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> commentService.deleteById(id));
//        verify(commentRepository, times(1)).findById(id);
//        verify(commentRepository, never()).deleteById(id);
//    }


}
