package com.example.demo.payload;

import com.example.demo.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
@Builder
public class UserSummary {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String number;
    private String address;
    private String city;
    private HashSet<String> role;
    private String profilePicture;
}
