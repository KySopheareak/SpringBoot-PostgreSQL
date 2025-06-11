package com.springboot.crudProject.Repository;

import com.springboot.crudProject.Model.categoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepo extends JpaRepository<categoryModel, Long> {
}