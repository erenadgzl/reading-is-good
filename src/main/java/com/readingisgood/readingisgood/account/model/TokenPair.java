package com.readingisgood.readingisgood.account.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenPair implements Serializable {

    private final String jwt;
    private final String refreshToken;
}
