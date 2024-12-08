package com.vodafone.Task_Manager.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "Password Cannot be blank")
    @NotNull(message = "Password Cannot be null")
    @Size(min = 8, message = "Password must contain at least one letter and one number, and be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must contain at least one letter and one number, and be at least 8 characters long"
    )
    private String password;

    @NotBlank(message = "Name Cannot be blank")
    @NotNull(message = "Name Cannot be Null")
    @Size(max = 100, message = "name must be less than or equal to 100 characters")
    private String name;

    @NotBlank(message = "Email Cannot be blank")
    @Email(message = "Please enter a valid email")
    @NotNull(message = "email Cannot be Null")
    @Size(max = 150, message = "Email must be less than or equal to 150 characters")
    private String email;

}
