package com.springboot.crudProject.Controller;

import com.springboot.crudProject.Model.userDTO;
import com.springboot.crudProject.Model.userModel;
import com.springboot.crudProject.Model.userResponse;
import com.springboot.crudProject.Model.userSearch;
import com.springboot.crudProject.Service.userService;
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
    public ResponseEntity<userResponse<userDTO>> createUser(@RequestBody userModel user) {
        userResponse<userDTO> response = userService.createUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/Users")
    public ResponseEntity<userResponse<List<userDTO>>> searchUsers(@RequestBody userSearch searchRequest) {
        userResponse<List<userDTO>> response = userService.getAllUsers(searchRequest.getSearch());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/UsersById/{id}")
    public ResponseEntity<userResponse<userDTO>> getUserById(@PathVariable Long id) {
        userResponse<userDTO> response = userService.getUserById(id);
        return response.getData() != null ? 
            new ResponseEntity<>(response, HttpStatus.OK) : 
            new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<userResponse<userDTO>> updateUser(@PathVariable Long id, @RequestBody userModel userDetails) {
        userResponse<userDTO> response = userService.updateUser(id, userDetails);
        return response.getData() != null ? 
            new ResponseEntity<>(response, HttpStatus.OK) : 
            new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<userResponse<Void>> deleteUser(@PathVariable Long id) {
        userResponse<Void> response = userService.deleteUser(id);
        return response.getData() == null ? 
            new ResponseEntity<>(response, HttpStatus.NOT_FOUND) : 
            new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}