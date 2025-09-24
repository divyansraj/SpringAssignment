package com.ofss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
