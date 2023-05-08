package com.pmso.product_management_system_original.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionsHandler {

    //      Resource Not Found

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ErrorsDescription errorDescription = new ErrorsDescription(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandling(Exception exception, WebRequest request){
        ErrorsDescription errorDescription = new ErrorsDescription(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDescription, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //      Deleting table with a foreign key in other table

    @ExceptionHandler(DeletingException.class)
    public ResponseEntity<?> deletingTableException(DeletingException exception, WebRequest request){
        ErrorsDescription errorDescription = new ErrorsDescription(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDescription, HttpStatus.CONFLICT);
    }

}
