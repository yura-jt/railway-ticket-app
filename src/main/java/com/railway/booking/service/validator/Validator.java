package com.railway.booking.service.validator;

public interface Validator<E> {

    boolean isValid(E entity);

    void validateId(Integer id);
}