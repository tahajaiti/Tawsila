package com.kyojin.tawsila.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String traceId;
    private List<String> validationErrors;


    public ErrorResponse(int status, String error, String message, String path, String traceId) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.traceId = traceId;
    }

    public ErrorResponse(int status, String error, String message, String path, String traceId, List<String> validationErrors) {
        this(status, error, message, path, traceId);
        this.validationErrors = validationErrors;
    }

}
