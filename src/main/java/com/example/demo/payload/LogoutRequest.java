package com.example.demo.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogoutRequest {

    @NotBlank
    private String username;
}
