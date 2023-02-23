package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class NewsRepository implements BaseRepository<NewsModel, Long> {


    @PersistenceContext
    private EntityManager entityManager;

    public Optional<NewsModel> readTagsAndAuthorByNewsId(Long newsId) {
        NewsModel newsModel = entityManager.find(NewsModel.class, newsId);
        return Optional.ofNullable(newsModel);
    }


    public NewsModel getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
        StringBuilder jpql = new StringBuilder("SELECT n FROM NewsModel n LEFT JOIN n.authorModel a LEFT JOIN n.tagModels t ");

        if (tagId != null) {
            jpql.append("WHERE t.id = :tagId ");
        } else if (tagName != null && !tagName.isEmpty()) {
            jpql.append("WHERE t.name = :tagName ");
        } else {
            jpql.append("WHERE 1=1 ");
        }

        if (authorName != null && !authorName.isEmpty()) {
            jpql.append("AND a.name = :authorName ");
        }

        if (title != null && !title.isEmpty()) {
            jpql.append("AND n.title LIKE :title ");
        }

        if (content != null && !content.isEmpty()) {
            jpql.append("AND n.content LIKE :content ");
        }

        jpql.append("GROUP BY n.id");

        TypedQuery<NewsModel> query = entityManager.createQuery(jpql.toString(), NewsModel.class);

        if (tagId != null) {
            query.setParameter("tagId", tagId);
        } else if (tagName != null && !tagName.isEmpty()) {
            query.setParameter("tagName", tagName);
        }

        if (authorName != null && !authorName.isEmpty()) {
            query.setParameter("authorName", authorName);
        }

        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }

        if (content != null && !content.isEmpty()) {
            query.setParameter("content", "%" + content + "%");
        }

        try {
            List<NewsModel> newsList = query.getResultList();
            if (newsList != null && !newsList.isEmpty()) {
                return newsList.get(0);
            } else {
                return null;
            }
        } catch (NoResultException | NonUniqueResultException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }


    @Override
    public List<NewsModel> readAll() {
        return entityManager.createQuery("SELECT n FROM NewsModel n", NewsModel.class).getResultList();
    }

    @Override
    public Optional<NewsModel> readById(Long newsId) {
        NewsModel newsModel = entityManager.find(NewsModel.class, newsId);
        return Optional.ofNullable(newsModel);
    }


    @Override
    public NewsModel create(NewsModel model) {
        if (model.getTagId() != null) {
            List<TagModel> tagModels = entityManager.createQuery("SELECT t FROM TagModel t", TagModel.class).getResultList();
            List<TagModel> existingTagModels = new ArrayList<>();
            for (TagModel tagModel : tagModels) {
                TagModel existingTagModel = entityManager.find(TagModel.class, model.getTagId());
                if (existingTagModel != null) {
                    existingTagModels.add(existingTagModel);
                } else {
                    existingTagModels.add(null);
                }
            }
            model.setTagModels(existingTagModels);
        }
        entityManager.persist(model);
        return model;
    }


    @Override
    public NewsModel update(NewsModel model) {
        NewsModel newsModel = entityManager.find(NewsModel.class, model.getId());
        newsModel.setTitle(model.getTitle());
        newsModel.setContent(model.getContent());
        if (model.getTagId() != null) {
            List<TagModel> tagModels = entityManager.createQuery("SELECT t FROM TagModel t", TagModel.class).getResultList();
            List<TagModel> existingTagModels = new ArrayList<>();
            TagModel existingTagModel = entityManager.find(TagModel.class, model.getTagId());
            if (existingTagModel != null) {
                existingTagModels.add(existingTagModel);
            } else {
                existingTagModels.add(null);
            }
            newsModel.setTagModels(existingTagModels);
        }
        return entityManager.merge(newsModel);
    }

    @Override
    public boolean deleteById(Long newsId) {
        NewsModel newsModel = entityManager.find(NewsModel.class, newsId);
        if (newsModel != null) {
            entityManager.remove(newsModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long newsId) {
        return entityManager.createQuery("SELECT COUNT(n) FROM NewsModel n WHERE n.id = :id", Long.class)
                .setParameter("id", newsId)
                .getSingleResult() > 0;
    }

}
