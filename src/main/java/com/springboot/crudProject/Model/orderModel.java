package com.springboot.crudProject.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class orderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private productModel product;

    private Integer quantity;
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private userModel customer;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public productModel getProduct() {
        return product;
    }

    public void setProduct(productModel product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public userModel getCustomer() {
        return customer;
    }

    public void setCustomer(userModel customer) {
        this.customer = customer;
    }
}