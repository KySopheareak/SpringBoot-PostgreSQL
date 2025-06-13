package com.springboot.crudProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.crudProject.Model.loginDTO;
import com.springboot.crudProject.Model.userModel;
import com.springboot.crudProject.Repository.userRepo;

@Service
public class loginService {

    @Autowired
    private userRepo userRepository;

    public boolean authenticate(loginDTO loginDto) {
        userModel user = userRepository.findByUsername(loginDto.getUsername());
        if (user != null && user.getPassword().equals(loginDto.getPassword())) {
            return true;
        }
        return false;
    }
}