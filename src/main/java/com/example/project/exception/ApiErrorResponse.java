package com.example.project.exception;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private String message;
    private int statusHttp;
    private String customMessage;

    public ApiErrorResponse(String message, int statusHttp, String customMessage) {
        this.message = message;
        this.statusHttp = statusHttp;
        this.customMessage = customMessage;
    }
}
