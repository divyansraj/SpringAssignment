package com.ofss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntityDto {
    private String status;
    private String message;
    private Object data;

    // Constructor for responses without data
    public ResponseEntityDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
    public ResponseEntityDto(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}