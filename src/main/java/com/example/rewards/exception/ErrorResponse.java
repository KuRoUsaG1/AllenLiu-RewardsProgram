package com.example.rewards.exception;

import java.time.LocalDateTime;
import java.util.Date;
public class ErrorResponse {
    private String errorMessage;
    private int errorCode;
    private LocalDateTime timestamp;

    public ErrorResponse(){};

    public ErrorResponse(String errorMessage, int errorCode, LocalDateTime timestamp) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
