package com.railway.booking.dao;

import com.railway.booking.dao.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E> {
    void save(E entity);

    Optional<E> findById(Integer id);

    List<E> findAll(Page page);

    void update(E entity);

    void deleteById(Integer id);

    long count();
}