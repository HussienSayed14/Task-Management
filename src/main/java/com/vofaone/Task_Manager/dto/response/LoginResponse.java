package com.vofaone.Task_Manager.dto.response;

import com.vofaone.Task_Manager.util.GenericResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Setter
@Getter
public class LoginResponse extends GenericResponse {

    private int userId;
    private String username;
    private Timestamp createdAt;

    public void emailNonExist() {
        setResponseCode("2");
        setSuccess(false);
        setMessage("This email does not exist");
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public void setWrongPassword() {
        setResponseCode("2");
        setSuccess(false);
        setMessage("You have entered wrong password");
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
