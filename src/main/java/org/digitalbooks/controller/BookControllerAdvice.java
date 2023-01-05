package org.digitalbooks.controller;

import org.digitalbooks.exception.BookServiceException;
import org.digitalbooks.exception.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookControllerAdvice {

    @ExceptionHandler(BookServiceException.class)
    public ResponseEntity<ErrorInfo> handleBookServiceException(BookServiceException ex){
        ErrorInfo errorInfo = ErrorInfo.builder().errorId(ex.getId()).errorMessage(ex.getMessage()).build();
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleGeneralError(Exception ex){
        ErrorInfo errorInfo = ErrorInfo.builder().errorId(0L).errorMessage(ex.getMessage()).build();
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
