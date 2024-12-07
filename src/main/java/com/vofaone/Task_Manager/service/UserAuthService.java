package com.vofaone.Task_Manager.service;


import com.vofaone.Task_Manager.config.JwtService;
import com.vofaone.Task_Manager.dto.request.LoginRequest;
import com.vofaone.Task_Manager.dto.request.SignUpRequest;
import com.vofaone.Task_Manager.dto.response.LoginResponse;
import com.vofaone.Task_Manager.entity.User;
import com.vofaone.Task_Manager.repository.UserRepository;
import com.vofaone.Task_Manager.util.GenericResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);


    public ResponseEntity<GenericResponse> signUp(SignUpRequest request) {
        GenericResponse response = new GenericResponse();
        try {

            if(emailExists(request.getEmail())){
                response.emailAlreadyExist();
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            User user = User.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();


            userRepository.save(user);
            response.setSuccessful();
        }catch (Exception e){
            response.setServerError();
            logger.error("An Error happened while creating user: "+ request.getEmail() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request, HttpServletResponse httpResponse) {
        LoginResponse response = new LoginResponse();

        try{
            User user = userRepository.findUserByEmail(request.getEmail());

            if(user == null){
                response.emailNonExist();
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            if(isPasswordCorrect(request.getPassword(), user)){
                String token = jwtService.generateToken(user,user.getId());
                response.setSuccessful();
                response.setUsername(user.getUsername());
                response.setUserId(user.getId());
                response.setCreatedAt(user.getCreatedAt());
                generateHttpOnlyCookie(token, httpResponse);

            }else{
                response.setWrongPassword();
            }


        }catch (Exception e){
            response.setServerError();
            logger.error("An Error happened while logging user: "+ request.getEmail() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    private boolean isPasswordCorrect(String plainPassword, User user){
        if(passwordEncoder.matches(plainPassword,user.getPassword())){
            return true;
        }
        return false;
    }

    private void generateHttpOnlyCookie(String token, HttpServletResponse httpResponse){
        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(true);  // Make it HttpOnly
        jwtCookie.setSecure(false);    // Set secure flag if you're using HTTPS
        jwtCookie.setPath("/");       // Cookie available for all endpoints
        jwtCookie.setMaxAge(60 * 60 * 24);  // 1 day expiration

        // Add the cookie to the response
        httpResponse.addCookie(jwtCookie);

    }




    private boolean emailExists(String email){
        User user = userRepository.findUserByEmail(email);
        if(user != null){
           return true;
        }
        return false;
    }


}
