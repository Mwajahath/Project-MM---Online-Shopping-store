package com.massmutual.demo.handler;

import com.massmutual.demo.exceptions.*;
import com.massmutual.demo.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public final ResponseEntity handleNoRecordFoundException(NoRecordFoundException ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public final ResponseEntity handleDuplicateRecordException(DuplicateRecordException ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<AppException> handleAppException(AppException ex, WebRequest request){
        return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


}