package com.readingisgood.readingisgood.account.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RefreshToken implements Serializable{

    @NotBlank
    @JsonProperty("refresh_token")
    private final String refreshToken;

    @JsonCreator
    public RefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}