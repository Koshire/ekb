package com.ktaksv.ekb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T, E> extends
        JpaRepository<T, E>, PagingAndSortingRepository<T, E> {
}
