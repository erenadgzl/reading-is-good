package com.readingisgood.readingisgood.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException
{
    public ConflictException(String exception) {
        super(exception);
    }
}
