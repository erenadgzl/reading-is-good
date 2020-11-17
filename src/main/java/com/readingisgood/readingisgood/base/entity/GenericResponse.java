package com.readingisgood.readingisgood.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenericResponse implements Serializable {

    private String status;
    private Object data;
    private GenericErrorResponse error;
}