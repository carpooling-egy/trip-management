package com.example.demo.DTOs;

import com.example.demo.Enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private GenderType gender;
}
