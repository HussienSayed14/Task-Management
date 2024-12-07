package com.vofaone.Task_Manager.util;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponse {

    private String responseCode;
    private boolean success;
    @JsonIgnore
    private HttpStatus httpStatus;
    private String message;


    public void setServerError(){
        setResponseCode("-1");
        setSuccess(false);
        setMessage("Sorry Something Wrong Happened, please try again later");
        setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public GenericResponse(String message){
        setResponseCode("-2");
        setSuccess(false);
        setMessage(message);
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }


    public void setSuccessful(){
        setResponseCode("0");
        setSuccess(true);
        setMessage("Request Successful");
        setHttpStatus(HttpStatus.OK);
    }


    public void setSuccessful(String message){
        setResponseCode("0");
        setSuccess(true);
        setMessage(message);
        setHttpStatus(HttpStatus.OK);
    }

    public void emailAlreadyExist() {
        setResponseCode("1");
        setSuccess(false);
        setMessage("This email already exists");
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public void taskDoesNotExist() {
        setResponseCode("2");
        setSuccess(false);
        setMessage("This task does not exist");
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public void userDoesNotOwnTask() {
        setResponseCode("3");
        setSuccess(false);
        setMessage("You are not authorized to update this task");
        setHttpStatus(HttpStatus.UNAUTHORIZED);
    }
}
