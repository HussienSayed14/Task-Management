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

    public void setSuccessful(){
        setResponseCode("0");
        setSuccess(true);
        setMessage("Request Successful");
        setHttpStatus(HttpStatus.OK);
    }

}
