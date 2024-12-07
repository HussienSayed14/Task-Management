package com.vofaone.Task_Manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotNull(message = "{password.notNull}")
    @Size(min = 8, message = "{password.size}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message ="{password.size}"
    )
    private String password;

    @NotNull(message = "Name Cannot be Null")
    @Size(max = 100, message = "name must be less than or equal to 100 characters")
    private String name;

    @Email(message = "Please enter a valid email")
    @NotNull(message = "email Cannot be Null")
    @Size(max = 150, message = "Email must be less than or equal to 150 characters")
    private String email;

}
