package com.example.tsekh_task.exception_handler;


import com.example.tsekh_task.exception.InvalidFileFormatException;
import com.example.tsekh_task.exception.UserAlreadyExistsException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.AuthenticationFailedException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLDataException;

@ControllerAdvice
public class CustomExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { InvalidFileFormatException.class})
    protected ResponseEntity<Object> handleFileFormatException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Server does not support file format";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    @ExceptionHandler(value
            = { ObjectNotFoundException.class})
    protected ResponseEntity<Object> handleObjectNotFoundException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = { UserAlreadyExistsException.class})
    protected ResponseEntity<Object> handleUserAlreadyExistsException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value
            = { AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthenticationException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value
            = { AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value
            = { IllegalArgumentException.class,SQLDataException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value
            = {AuthenticationFailedException.class})
    protected ResponseEntity<Object> handleAuthenticationFailedException(
            AuthenticationFailedException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }


}
