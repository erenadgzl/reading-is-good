package com.readingisgood.readingisgood.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserLogin implements Serializable {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
