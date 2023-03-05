package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsModel, Long> {

    @Query("SELECT n FROM NewsModel n WHERE (:tagNames IS NULL OR n.tagName IN (:tagNames)) " +
            "AND (:tagIds IS NULL OR n.tagId IN (:tagIds)) " +
            "AND (:authorName IS NULL OR n.authorModel.name = :authorName) " +
            "AND (:title IS NULL OR n.title = :title) " +
            "AND (:content IS NULL OR n.content = :content)")
    List<NewsModel> getNewsByParams(@Param("tagNames") List<String> tagNames,
                                    @Param("tagIds") List<Long> tagIds,
                                    @Param("authorName") String authorName,
                                    @Param("title") String title,
                                    @Param("content") String content);
}