package org.digitalbooks.exception;

import lombok.Getter;

@Getter
public class BookServiceException extends RuntimeException {
    private long id = 1L;
    public BookServiceException(String message) {
        super(message);
    }
    public BookServiceException(Long id,String message) {
        super(message);
        this.id = id;
    }
}
