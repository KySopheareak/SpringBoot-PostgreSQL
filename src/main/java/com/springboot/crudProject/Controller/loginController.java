package com.springboot.crudProject.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.springboot.crudProject.Model.loginDTO;
import com.springboot.crudProject.Service.loginService;
import com.springboot.crudProject.Utils.JwtUtil;
import com.springboot.crudProject.Utils.utilResponse;

@RestController
@RequestMapping("/api")
public class loginController {

    @Autowired
    private loginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<utilResponse<String>> login(@RequestBody loginDTO loginDto) {
        boolean authenticated = loginService.authenticate(loginDto);
        utilResponse<String> response = new utilResponse<>();
        if (authenticated) {
            String token = jwtUtil.generateToken(loginDto.getUsername());
            response.setData(token);
            response.setMessage("Login successful");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setData(null);
            response.setMessage("Invalid credentials.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<utilResponse<String>> logout(@RequestHeader("Authorization") String token) {
        utilResponse<String> response = new utilResponse<>();
        response.setData(null);
        response.setMessage("Logged out successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}