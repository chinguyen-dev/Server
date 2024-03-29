package com.yody.Server.api.auth;

import com.yody.Server.dto.AuthenticationRequest;
import com.yody.Server.dto.AuthenticationResponse;
import com.yody.Server.dto.user.UserDTO;
import com.yody.Server.dto.user.UserRegisterRequest;
import com.yody.Server.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class AuthenticationController {
    private final IUserService userService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse response = this.userService.authentication(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        return this.userService.saveUser(userRegisterRequest);
    }
    @GetMapping("/validate/{email}")
    public boolean validateEmail(@PathVariable String email){
        return this.userService.validateEmail(email);
    }
}
