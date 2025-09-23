package com.ofss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseEntityDto {
    private String code;
    private String message;
    private Object data;

    public ResponseEntityDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseEntityDto(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // getters and setters
}

