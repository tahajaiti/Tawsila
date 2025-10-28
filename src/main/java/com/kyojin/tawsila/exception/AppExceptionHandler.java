package com.kyojin.tawsila.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        String traceId = genId();
        log.warn("Trace ID: {}, Exception: {}, Message: {}, Request URI: {}",
                traceId, ex.getClass().getSimpleName(), ex.getMessage(), request.getRequestURI());

        ErrorResponse err = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                traceId
        );
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all "Bad Request" exceptions, including:
     * - @Valid validation failures (MethodArgumentNotValidException)
     * - Manual validation failures (BadRequestException)
     * - Service-layer data errors (IllegalArgumentException, IllegalStateException)
     */
    @ExceptionHandler({
            BadRequestException.class,
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            RuntimeException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {
        String traceId = genId();
        log.warn("Trace ID: {}, Exception: {}, Message: {}, Request URI: {}",
                traceId, ex.getClass().getSimpleName(), ex.getMessage(), request.getRequestURI());

        List<String> validationErrors = null;
        String message = ex.getMessage();

        if (ex instanceof MethodArgumentNotValidException e) {
            message = "Validation failed";
            validationErrors = e.getBindingResult().getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .toList();
        }

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                request.getRequestURI(),
                traceId,
                validationErrors
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles malformed JSON and gives a more specific error message.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String traceId = genId();
        log.warn("Trace ID: {}, JSON parse error: {}, Request URI: {}",
                traceId, ex.getMessage(), request.getRequestURI());

        String message = "Malformed JSON request";

        if (ex.getCause() instanceof InvalidFormatException ifx) {
            String path = ifx.getPath().stream()
                    .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
                    .collect(Collectors.joining("."));
            String expectedType = ifx.getTargetType() != null ? ifx.getTargetType().getSimpleName() : "Unknown";

            message = String.format("Invalid format for field '%s'. Got '%s', expected %s",
                    path, ifx.getValue(), expectedType);
        }

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                request.getRequestURI(),
                traceId
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is the catch-all for any unexpected errors.
     * The message is deliberately generic to avoid leaking server details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        String traceId = genId();
        log.error("Trace ID: {}, Unhandled Exception: {}, Message: {}, Request URI: {}",
                traceId, ex.getClass().getSimpleName(), ex.getMessage(), request.getRequestURI(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected internal error occurred. Please contact support with the trace ID.",
                request.getRequestURI(),
                traceId
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String genId() {
        return UUID.randomUUID().toString();
    }
}