package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.impl.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {
    Optional<AuthorModel> findByName(String name);

}
