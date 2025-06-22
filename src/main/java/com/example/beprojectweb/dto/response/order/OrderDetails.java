package com.example.beprojectweb.dto.response.order;

import com.example.beprojectweb.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails {

    private UUID id;
    private Date orderDate;
    private Double totalAmount;
    private String total; // formatted
    private OrderStatus orderStatus;
    private String customer;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private Integer items;
    private String paymentMethod;
    private Date expectedDeliveryDate;
    private List<OrderItemDetail> orderItemList;

}
