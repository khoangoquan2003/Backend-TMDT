package com.example.beprojectweb.service;

import com.example.beprojectweb.dto.request.order.OrderRequest;
import com.example.beprojectweb.dto.response.order.OrderDetails;
import com.example.beprojectweb.dto.response.order.OrderItemDetail;
import com.example.beprojectweb.dto.response.order.OrderResponse;
import com.example.beprojectweb.entity.*;
import com.example.beprojectweb.enums.OrderStatus;
import com.example.beprojectweb.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());

        Order order = Order.builder()
                .user(user)
                .address(orderRequest.getAddressId())
                .totalAmount(orderRequest.getTotalAmount())
                .orderDate(orderRequest.getOrderDate())
                .expectedDeliveryDate(orderRequest.getExpectedDeliveryDate())
                .paymentMethod(orderRequest.getPaymentMethod())
                .orderStatus(OrderStatus.PENDING)
                .build();

        // Tạo list OrderItem, mỗi item phải có giá (itemPrice)
        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream().map(orderItemRequest -> {
            Product product = null;
            try {
                product = productService.fetchProductById(orderItemRequest.getProductId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return OrderItem.builder()
                    .product(product)
                    .quantity(orderItemRequest.getQuantity())
                    .itemPrice(product.getPrice().doubleValue())
                    .order(order)
                    .build();
        }).toList();

        order.setOrderItemList(orderItems);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .paymentMethod(savedOrder.getPaymentMethod())
                .build();
    }

    @Transactional
    public void updateOrderStatus(UUID orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        switch (status.toUpperCase()) {
            case "COMPLETED":
                order.setOrderStatus(OrderStatus.PENDING);
                break;
            case "CANCELLED":
                order.setOrderStatus(OrderStatus.CANCELLED);
                break;
            default:
                throw new IllegalArgumentException("Invalid status");
        }
        orderRepository.save(order);
    }

    public List<OrderDetails> getOrdersByUser(String name) {
        User user = (User) userDetailsService.loadUserByUsername(name);
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> OrderDetails.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .orderItemList(getItemDetails(order.getOrderItemList()))
                .expectedDeliveryDate(order.getExpectedDeliveryDate())
                .build()
        ).toList();
    }

    private List<OrderItemDetail> getItemDetails(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(orderItem -> {
            Product product = orderItem.getProduct();
            return OrderItemDetail.builder()
                    .id(orderItem.getId())
                    .itemPrice(orderItem.getItemPrice())
                    .productId(product.getProduct_ID())
                    .productName(product.getProductName())
                    .productImg(product.getUrlImage())
                    .category(product.getCategory() != null ? product.getCategory().getName() : null)
                    .quantity(orderItem.getQuantity())
                    .build();
        }).toList();
    }


    @Transactional
    public void cancelOrder(UUID id, Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));



        if (order.getOrderStatus() != OrderStatus.PENDING && order.getOrderStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Chỉ được hủy đơn ở trạng thái Chờ xác nhận hoặc Đã xác nhận.");
        }

        if (order.getUser().getId().equals(user.getId())) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Bạn không có quyền hủy đơn hàng này.");
        }


    }
    public List<OrderDetails> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> {
                    User user = order.getUser(); // lazy nhưng đã ignore json

                    return OrderDetails.builder()
                            .id(order.getId())
                            .orderDate(order.getOrderDate())
                            .orderStatus(order.getOrderStatus())
                            .totalAmount(order.getTotalAmount())
                            .total(String.format("%,.0f ₫", order.getTotalAmount())) // format tiền tệ
                            .customer(user != null ? user.getFullName() : "N/A")
                            .customerEmail(user != null ? user.getEmail() : "N/A")
                            .customerPhone(user != null ? user.getPhoneNumber() : "N/A")
                            .shippingAddress(order.getAddress())
                            .paymentMethod(order.getPaymentMethod())
                            .expectedDeliveryDate(order.getExpectedDeliveryDate())
                            .items(order.getOrderItemList() != null ? order.getOrderItemList().size() : 0)
                            .orderItemList(getItemDetails(order.getOrderItemList()))
                            .build();
                })
                .toList();
    }

    public Double getTotalRevenueFromCompletedOrders() {
        List<OrderDetails> orders = getAllOrders(); // Sử dụng lại hàm đã có
        return orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.DELIVERED)
                .mapToDouble(OrderDetails::getTotalAmount)
                .sum();
    }


}
