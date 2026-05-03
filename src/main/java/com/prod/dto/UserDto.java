package com.prod.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.prod.entities.User}
 */
@Value
public class UserDto implements Serializable {

    private String email;
    private String password;
    private String name;
}