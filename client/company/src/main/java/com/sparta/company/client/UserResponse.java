package com.sparta.company.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;
}
