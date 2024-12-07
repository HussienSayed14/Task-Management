package com.vofaone.Task_Manager.controller;


import com.vofaone.Task_Manager.dto.request.LoginRequest;
import com.vofaone.Task_Manager.dto.request.SignUpRequest;
import com.vofaone.Task_Manager.dto.response.LoginResponse;
import com.vofaone.Task_Manager.service.UserAuthService;
import com.vofaone.Task_Manager.util.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "User Authentication" , description = "Apis That is Responsible User Authenticated Operations, does not need JWT token")
public class UserAuthController {
    private final UserAuthService userAuthService;



    @Operation(summary = "Sign Up User", description = "Validate and Create a new user and save it into the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Input Validation error",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Email Already Exist",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "An unexpected exception happened in the server",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class),
                            examples = @ExampleObject(
                            name = "ServerErrorResponse",
                            value = """
                                      {
                                       responseCode: "-1",
                                       success: false,
                                       message: "Sorry Something Wrong Happened, please try again later"
                                       }
                                    """
                    )))
    })
    @PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> signUp(@Valid @RequestBody SignUpRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return userAuthService.signUp(request);
    }


    @Operation(summary = "Login User", description = "Check user password for Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Logged successfully (Create and HttpOnly Cookie), " +
                    "No need to add the token as a Bearer token since it is added as an HttpOnly Cookie",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Input Validation error",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "403", description = "User entered wrong password",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "500", description = "An unexpected exception happened in the server",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class),
                    examples = @ExampleObject(
                            name = "ServerErrorResponse",
                            value = """
                                      {
                                      responseCode: "-1",
                                       success: false,
                                       message: "Sorry Something Wrong Happened, please try again later"
                                       }
                                    """
                    )
                    ))
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body((LoginResponse) new GenericResponse(errorMessage));
        }
        return userAuthService.login(request,response);
    }



}
