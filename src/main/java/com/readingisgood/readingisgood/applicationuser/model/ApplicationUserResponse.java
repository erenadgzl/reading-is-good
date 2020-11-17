package com.readingisgood.readingisgood.applicationuser.model;

import com.readingisgood.readingisgood.base.entity.Address;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationUserResponse implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private Address address;
}
