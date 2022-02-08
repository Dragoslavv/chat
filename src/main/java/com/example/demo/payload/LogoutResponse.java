package com.example.demo.payload;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LogoutResponse {
    @NonNull
    private boolean status;
}
