package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.order.QuoteOrderCustomRequest;
import com.example.beprojectweb.dto.request.order.RejectOrderCustomRequest;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.order.OrderCustomResponse;
import com.example.beprojectweb.service.OrderCustomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-custom")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderCustomController {
    OrderCustomService orderCustomService;
    @PostMapping("/create")
    public APIResponse<OrderCustomResponse> createCustomOrder(
            @RequestParam("quantity") int quantity,
            @RequestParam("description") String description,
            @RequestPart("files") List<MultipartFile> files) {
        return APIResponse.<OrderCustomResponse>builder()
                .result(orderCustomService.createCustomOrderWithMultipleFiles(quantity, description,files))
                .build();
    }

    @GetMapping
    public APIResponse<List<OrderCustomResponse>> getAllCustomOrders() {
        return APIResponse.<List<OrderCustomResponse>>builder()
                .result(orderCustomService.getAllCustomOrders())
                .build();
    }

    @GetMapping("/me")
    public APIResponse<List<OrderCustomResponse>> getMyOrders() {
        return APIResponse.<List<OrderCustomResponse>>builder()
                .result(orderCustomService.getMyOrders())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse<OrderCustomResponse> getCustomOrderById(@PathVariable UUID id) {
        return APIResponse.<OrderCustomResponse>builder()
                .result(orderCustomService.getCustomOrderById(id))
                .build();
    }

    @PutMapping("/quote")
    public APIResponse<OrderCustomResponse> quoteCustomOrder(
            @RequestBody QuoteOrderCustomRequest request
    ) {
        return APIResponse.<OrderCustomResponse>builder()
                .result(orderCustomService.quoteCustomOrder(request))
                .build();
    }

    @PutMapping("/reject")
    public APIResponse<OrderCustomResponse> rejectOrder(@RequestBody RejectOrderCustomRequest request) {
        return APIResponse.<OrderCustomResponse>builder()
                .result(orderCustomService.rejectOrder(request))
                .build();
    }

    @PutMapping("/{id}/confirm")
    public APIResponse<OrderCustomResponse> confirmQuote(@PathVariable UUID id) {
        return APIResponse.<OrderCustomResponse>builder()
                .result(orderCustomService.confirmQuote(id))
                .build();
    }
}
