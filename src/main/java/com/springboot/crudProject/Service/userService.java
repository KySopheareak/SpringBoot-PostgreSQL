package com.springboot.crudProject.Service;

import com.springboot.crudProject.Model.userModel;
import com.springboot.crudProject.Model.userDTO;
import com.springboot.crudProject.Repository.userRepo;
import com.springboot.crudProject.Utils.utilResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

    // Convert userModel to userDTO
    private userDTO convertToDTO(userModel user) {
        return new userDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getAddress()
        );
    }

    // Create user
    public utilResponse<userDTO> createUser(userModel user) {
        userModel savedUser = userRepo.save(user);
        return new utilResponse<>("User created successfully", convertToDTO(savedUser));
    }

    // Get all users
    public utilResponse<List<userDTO>> getAllUsers(String search) {
        List<userModel> users;
        if (search == null || search.trim().isEmpty() || !search.matches(".*\\S.*")) {
            users = userRepo.findAll();
        } else {
            String lowerSearch = search.toLowerCase();
            users = userRepo.findAll().stream().filter(user ->
                    (user.getName() != null && user.getName().toLowerCase().contains(lowerSearch)) ||
                    (user.getEmail() != null && user.getEmail().toLowerCase().contains(lowerSearch)) ||
                    (user.getPhone() != null && user.getPhone().toLowerCase().contains(lowerSearch))
                ).collect(Collectors.toList());
        }
        List<userDTO> userDTOs = users.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new utilResponse<>("User list fetched successfully", userDTOs);
    }

    // Get user by id
    public utilResponse<userDTO> getUserById(Long id) {
        Optional<userModel> user = userRepo.findById(id);
        if (user.isPresent()) {
            return new utilResponse<>("User found", convertToDTO(user.get()));
        } else {
            return new utilResponse<>("User not found", null);
        }
    }

    // Update user
    public utilResponse<userDTO> updateUser(Long id, userModel userDetails) {
        Optional<userModel> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            userModel user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            userModel updatedUser = userRepo.save(user);
            return new utilResponse<>("User updated successfully", convertToDTO(updatedUser));
        } else {
            return new utilResponse<>("User not found", null);
        }
    }

    // Delete user
    public utilResponse<Void> deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return new utilResponse<>("User deleted successfully", null);
        } else {
            return new utilResponse<>("User not found", null);
        }
    }
}