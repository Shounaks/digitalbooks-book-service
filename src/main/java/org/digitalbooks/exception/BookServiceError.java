package org.digitalbooks.exception;

import lombok.Getter;

@Getter
public enum BookServiceError {
    ;

    private final int id;
    private final String message;

    BookServiceError(int id,String message){
        this.id = id;
        this.message = message;
    }
}
