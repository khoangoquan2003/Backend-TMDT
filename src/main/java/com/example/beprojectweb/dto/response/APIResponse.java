package com.example.beprojectweb.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class APIResponse<T> {
    int code;
    String message;
    T result;

    @Builder
    public APIResponse(String message, T result) {
        this.code = 1000; // Mặc định thành công
        this.message = message;
        this.result = result;
    }
}
