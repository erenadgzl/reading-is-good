package com.readingisgood.readingisgood.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class GenericErrorResponse implements Serializable {

    private String message;
    private List<String> details;
}