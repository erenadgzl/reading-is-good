package com.readingisgood.readingisgood.account.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessToken implements Serializable {
    private final String jwt;
}
