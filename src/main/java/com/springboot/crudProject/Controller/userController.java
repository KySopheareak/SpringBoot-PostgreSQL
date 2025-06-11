package com.springboot.crudProject.Controller;

import com.springboot.crudProject.Model.userDTO;
import com.springboot.crudProject.Model.userModel;
import com.springboot.crudProject.Service.userService;
import com.springboot.crudProject.Utils.utilResponse;
import com.springboot.crudProject.Utils.utliSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class userController {

    @Autowired
    private userService userService;

    @PostMapping("/createUser")
    public ResponseEntity<utilResponse<userDTO>> createUser(@RequestBody userModel user) {
        utilResponse<userDTO> response = userService.createUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/Users")
    public ResponseEntity<utilResponse<List<userDTO>>> searchUsers(@RequestBody utliSearch searchRequest) {
        utilResponse<List<userDTO>> response = userService.getAllUsers(searchRequest.getSearch());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/UsersById/{id}")
    public ResponseEntity<utilResponse<userDTO>> getUserById(@PathVariable Long id) {
        utilResponse<userDTO> response = userService.getUserById(id);
        return response.getData() != null ? 
            new ResponseEntity<>(response, HttpStatus.OK) : 
            new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<utilResponse<userDTO>> updateUser(@PathVariable Long id, @RequestBody userModel userDetails) {
        utilResponse<userDTO> response = userService.updateUser(id, userDetails);
        return response.getData() != null ? 
            new ResponseEntity<>(response, HttpStatus.OK) : 
            new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<utilResponse<Void>> deleteUser(@PathVariable Long id) {
        utilResponse<Void> response = userService.deleteUser(id);
        return response.getData() == null ? 
            new ResponseEntity<>(response, HttpStatus.NOT_FOUND) : 
            new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}