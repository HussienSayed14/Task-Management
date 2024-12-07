package com.vofaone.Task_Manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email(message = "Please enter a valid email")
    @NotNull(message = "email Cannot be Null")
    @Size(max = 150, message = "Email must be less than or equal to 150 characters")
    private String email;

    @NotNull(message = "Password Cannot be null")
    @Size(min = 8, message = "Password must contain at least one letter and one number, and be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message ="Password must contain at least one letter and one number, and be at least 8 characters long"
    )
    private String password;
}