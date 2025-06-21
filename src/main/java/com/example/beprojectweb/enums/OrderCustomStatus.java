package com.example.beprojectweb.enums;

public enum OrderCustomStatus {
    PENDING_QUOTE,   // Mới gửi yêu cầu, chưa có giá
    QUOTED,          // Admin đã báo giá
    AWAITING_PAYMENT,// Người dùng xác nhận đang chờ thanh toán
    PAID,            // Đã thanh toán
    REJECTED,        // Admin từ chối
    CANCELED         // Người dùng hủy
}
