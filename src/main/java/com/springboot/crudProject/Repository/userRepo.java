package com.springboot.crudProject.Repository;

import com.springboot.crudProject.Model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<userModel, Long> {
    userModel findByUsername(String username);
}