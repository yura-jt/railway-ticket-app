package com.railway.booking.dao.exception;

public class DatabaseSqlRuntimeException extends RuntimeException {
    public DatabaseSqlRuntimeException(String message) {
        super(message);
    }

    public DatabaseSqlRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}