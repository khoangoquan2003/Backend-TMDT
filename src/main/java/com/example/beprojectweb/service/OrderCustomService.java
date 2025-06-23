package com.example.beprojectweb.service;

import com.example.beprojectweb.dto.request.order.QuoteOrderCustomRequest;
import com.example.beprojectweb.dto.request.order.RejectOrderCustomRequest;
import com.example.beprojectweb.dto.response.order.OrderCustomResponse;
import com.example.beprojectweb.entity.OrderCustom;
import com.example.beprojectweb.entity.User;
import com.example.beprojectweb.enums.OrderCustomStatus;
import com.example.beprojectweb.exception.AppException;
import com.example.beprojectweb.exception.ErrorCode;
import com.example.beprojectweb.mapper.OrderCustomMapper;
import com.example.beprojectweb.repository.OrderCustomRepository;
import com.example.beprojectweb.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderCustomService {
    OrderCustomRepository orderCustomRepository;
    UserRepository userRepository;
    OrderCustomMapper orderCustomMapper;

    @PreAuthorize("hasRole('USER')")
    public OrderCustomResponse createCustomOrderWithMultipleFiles(int quantity, String description, List<MultipartFile> designFiles) {
        if (designFiles.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        List<String> fileUrls = new ArrayList<>();
        Path root = Paths.get("uploads/designs");

        try {
            Files.createDirectories(root);

            for (MultipartFile designFile : designFiles) {
                if (designFile.isEmpty() || !designFile.getContentType().startsWith("image/")) {
                    throw new AppException(ErrorCode.INVALID_FILE_TYPE);
                }

                String ext = designFile.getOriginalFilename()
                        .substring(designFile.getOriginalFilename().lastIndexOf("."));
                String filename = "design_" + user.getId() + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + ext;
                Path filePath = root.resolve(filename);
                Files.copy(designFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String fileUrl = "/uploads/designs/" + filename;

                fileUrls.add(fileUrl);
            }

            OrderCustom order = OrderCustom.builder()
                    .user(user)
                    .quantity(quantity)
                    .description(description)
                    .designFileUrl(fileUrls) // hoặc convert sang JSON nếu thích
                    .status(OrderCustomStatus.PENDING_QUOTE)
                    .createdAt(LocalDateTime.now())
                    .build();

            OrderCustom savedOrder = orderCustomRepository.save(order);
            return orderCustomMapper.toOrderResponse(savedOrder);

        } catch (IOException e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")

    public List<OrderCustomResponse> getAllCustomOrders() {
        List<OrderCustom> orders = orderCustomRepository.findAll();
        return orders.stream()
                .map(orderCustomMapper::toOrderResponse)
                .toList();
    }

    @PreAuthorize("hasRole('USER')")
    public List<OrderCustomResponse> getMyOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        List<OrderCustom> orders = orderCustomRepository.findByUser(user);
        return orders.stream().map(orderCustomMapper::toOrderResponse).toList();
    }

    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'USER')")
    public OrderCustomResponse getCustomOrderById(UUID id) {
        OrderCustom order = orderCustomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderCustomMapper.toOrderResponse(order);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public OrderCustomResponse quoteCustomOrder(QuoteOrderCustomRequest request) {
        OrderCustom order = orderCustomRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderCustomStatus.PENDING_QUOTE) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }

        order.setQuotedPrice(request.getPrice());
        order.setStatus(OrderCustomStatus.QUOTED);

        return orderCustomMapper.toOrderResponse(orderCustomRepository.save(order));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public OrderCustomResponse rejectOrder(RejectOrderCustomRequest request) {
        OrderCustom order = orderCustomRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderCustomStatus.PENDING_QUOTE) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }

        order.setStatus(OrderCustomStatus.REJECTED);

        return orderCustomMapper.toOrderResponse(orderCustomRepository.save(order));
    }

    public OrderCustomResponse confirmQuote(UUID id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        OrderCustom order = orderCustomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNATHENTICATIED); // Bảo vệ người khác xác nhận hộ
        }

        if (order.getStatus() != OrderCustomStatus.QUOTED) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }

        order.setStatus(OrderCustomStatus.AWAITING_PAYMENT);
        return orderCustomMapper.toOrderResponse(orderCustomRepository.save(order));
    }

    @PreAuthorize("hasRole('USER')")
    public OrderCustomResponse payCustomOrder(UUID id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        OrderCustom order = orderCustomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // Chỉ chủ đơn mới được thanh toán
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNATHENTICATIED);
        }

        if (order.getStatus() != OrderCustomStatus.AWAITING_PAYMENT) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }

        order.setStatus(OrderCustomStatus.PAID);
        order.setPaidAt(LocalDateTime.now());

        return orderCustomMapper.toOrderResponse(orderCustomRepository.save(order));
    }

    @PreAuthorize("hasRole('USER')")
    public OrderCustomResponse cancelOrder(UUID id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        OrderCustom order = orderCustomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // Chỉ chủ đơn mới có thể hủy
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNATHENTICATIED);
        }

        // Chỉ được hủy nếu chưa thanh toán
        if (order.getStatus() == OrderCustomStatus.PAID || order.getStatus() == OrderCustomStatus.REJECTED) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }

        order.setStatus(OrderCustomStatus.CANCELLED);
        return orderCustomMapper.toOrderResponse(orderCustomRepository.save(order));
    }

    @PreAuthorize("hasRole('USER')")
    public List<OrderCustomResponse> getMyOrdersByStatus(OrderCustomStatus status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        List<OrderCustom> orders = orderCustomRepository.findByUserAndStatus(user, status);
        return orders.stream().map(orderCustomMapper::toOrderResponse).toList();
    }

}
