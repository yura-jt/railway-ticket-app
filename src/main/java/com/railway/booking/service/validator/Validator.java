package com.railway.booking.service.validator;

public interface Validator<E> {

    void validate(E entity);

    void validateId(Integer id);
}