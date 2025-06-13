package com.springboot.crudProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.crudProject.Model.orderModel;

public interface orderRepo extends JpaRepository<orderModel, Long>{

}
