package com.readingisgood.readingisgood.base.handler;


import com.readingisgood.readingisgood.base.entity.CustomResponseStatus;
import com.readingisgood.readingisgood.base.entity.GenericErrorResponse;
import com.readingisgood.readingisgood.base.entity.GenericResponse;
import com.readingisgood.readingisgood.base.exception.BadRequestException;
import com.readingisgood.readingisgood.base.exception.ConflictException;
import com.readingisgood.readingisgood.base.exception.ForbiddenException;
import com.readingisgood.readingisgood.base.exception.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Server Error. Request:"+request, ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericErrorResponse error = new GenericErrorResponse("Server Error", details);

        return getObjectResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        logger.warn("Record Not Found. Request:"+request, ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericErrorResponse error = new GenericErrorResponse("Record Not Found", details);

        return getObjectResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        logger.warn("Conflict. Request:"+request, ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericErrorResponse error = new GenericErrorResponse("Conflict", details);

        return getObjectResponseEntity(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        logger.warn("Forbidden. Request:"+request, ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericErrorResponse error = new GenericErrorResponse("Forbidden", details);

        return getObjectResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        logger.warn("Bad Request. Request:"+request, ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericErrorResponse error = new GenericErrorResponse("Bad Request", details);

        return getObjectResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleBadRequestException(AccessDeniedException ex, WebRequest request) {
        logger.warn("Access Denied. Request:"+request, ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        GenericErrorResponse error = new GenericErrorResponse("Yetkiniz bulunmuyor!", details);

        return getObjectResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("Validation Failed. Request:"+request, ex);
        List<String> details = new ArrayList<>();
        ex.getAllErrors().forEach( error -> details.add(error.getDefaultMessage()));
        GenericErrorResponse error = new GenericErrorResponse("Validation Failed", details);

        return getObjectResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getObjectResponseEntity(GenericErrorResponse error, HttpStatus httpStatus) {
        GenericResponse apiResponse = new GenericResponse();
        apiResponse.setStatus(CustomResponseStatus.ERROR.getValue());
        apiResponse.setError(error);
        return new ResponseEntity(apiResponse, httpStatus);
    }
}