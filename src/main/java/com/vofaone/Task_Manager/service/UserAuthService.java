package com.vofaone.Task_Manager.service;


import com.vofaone.Task_Manager.dto.request.SignUpRequest;
import com.vofaone.Task_Manager.entity.User;
import com.vofaone.Task_Manager.repository.UserRepository;
import com.vofaone.Task_Manager.util.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
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

    private boolean emailExists(String email){
        User user = userRepository.findUserByEmail(email);
        if(user != null){
           return true;
        }
        return false;
    }


}
