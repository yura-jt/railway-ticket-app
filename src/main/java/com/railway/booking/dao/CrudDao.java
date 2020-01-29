package com.railway.booking.dao;

import java.util.Optional;

public interface CrudDao<E> {
    void save(E entity);

    Optional<E> findById(Integer id);

    long count();

    void update(E entity);

    void deleteById(Integer id);
}