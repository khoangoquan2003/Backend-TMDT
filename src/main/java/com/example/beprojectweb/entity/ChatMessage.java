package com.example.beprojectweb.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mapstruct.Builder;

@Setter
@Getter
@ToString

public class ChatMessage {
    private String sender;
    private String receiver; // Thêm trường nhận
    private String content;
    private String type;

    // getter/setter
}
