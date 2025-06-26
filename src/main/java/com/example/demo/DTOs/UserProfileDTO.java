package com.example.demo.DTOs;

import com.example.demo.Enums.GenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull(message = "gender must not be null")
    private GenderType gender;
}
