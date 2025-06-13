package com.springboot.crudProject.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.crudProject.Model.orderDTO;
import com.springboot.crudProject.Model.orderModel;
import com.springboot.crudProject.Repository.orderRepo;
import com.springboot.crudProject.Utils.utilResponse;

public class orderService {
    @Autowired
    private orderRepo orderRepo;

    // Convert orderModel to orderDTO
    private orderDTO convertToDTO(orderModel order) {
        return new orderDTO(
            order.getId(),
            order.getProduct().getId(),
            order.getQuantity(),
            order.getStatus(),
            order.getCustomer().getId()
        );
    }

    // Create order
    public utilResponse<orderDTO> createOrder(orderModel order) {
        orderModel savedOrder = orderRepo.save(order);
        return new utilResponse<>("Order created successfully", convertToDTO(savedOrder));
    }

    // Get all orders
    public utilResponse<List<orderDTO>> getAllOrders(String search) {
        List<orderModel> orders;
        if (search == null || search.trim().isEmpty() || !search.matches(".*\\S.*")) {
            orders = orderRepo.findAll();
        } else {
            String lowerSearch = search.toLowerCase();
            orders = orderRepo.findAll().stream().filter(order ->
                    (order.getProduct() != null && order.getProduct().getName() != null && order.getProduct().getName().toLowerCase().contains(lowerSearch)) ||
                    (order.getCustomer() != null && order.getCustomer().getName() != null && order.getCustomer().getName().toLowerCase().contains(lowerSearch))
                ).collect(Collectors.toList());
        }
        List<orderDTO> orderDTOs = orders.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new utilResponse<>("Order list fetched successfully", orderDTOs);
    }

    // Get order by id
    public utilResponse<orderDTO> getOrderById(Long id) {
        return orderRepo.findById(id)
            .map(order -> new utilResponse<>("Order fetched successfully", convertToDTO(order)))
            .orElse(new utilResponse<>("Order not found", null));
    }

    // Update order
    public utilResponse<orderDTO> updateOrder(Long id, orderModel orderDetails) {
        return orderRepo.findById(id)
            .map(order -> {
                order.setProduct(orderDetails.getProduct());
                order.setQuantity(orderDetails.getQuantity());
                order.setStatus(orderDetails.getStatus());
                order.setCustomer(orderDetails.getCustomer());
                orderModel updatedOrder = orderRepo.save(order);
                return new utilResponse<>("Order updated successfully", convertToDTO(updatedOrder));
            })
            .orElse(new utilResponse<>("Order not found", null));
    }

    // Delete order
    public utilResponse<String> deleteOrder(Long id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
            return new utilResponse<>("Order deleted successfully", null);
        } else {
            return new utilResponse<>("Order not found", null);
        }
    }

    // Get orders by customer ID
    public utilResponse<List<orderDTO>> getOrdersByCustomerId(Long customerId) {
        List<orderModel> orders = orderRepo.findAll().stream()
            .filter(order -> order.getCustomer() != null && Long.valueOf(order.getCustomer().getId()).equals(customerId))
            .collect(Collectors.toList());
        List<orderDTO> orderDTOs = orders.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new utilResponse<>("Orders for customer fetched successfully", orderDTOs);
    }

    // Get orders by product ID
    public utilResponse<List<orderDTO>> getOrdersByProductId(Long productId) {
        List<orderModel> orders = orderRepo.findAll().stream()
            .filter(order -> order.getProduct() != null && Long.valueOf(order.getProduct().getId()).equals(productId))
            .collect(Collectors.toList());
        List<orderDTO> orderDTOs = orders.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new utilResponse<>("Orders for product fetched successfully", orderDTOs);
    }



    // Get orders by status
    public utilResponse<List<orderDTO>> getOrdersByStatus(String status) {
        List<orderModel> orders = orderRepo.findAll().stream()
            .filter(order -> order.getStatus() != null && order.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
        List<orderDTO> orderDTOs = orders.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new utilResponse<>("Orders with status '" + status + "' fetched successfully", orderDTOs);
    }
}
