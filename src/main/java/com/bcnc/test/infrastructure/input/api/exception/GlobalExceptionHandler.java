package com.bcnc.test.infrastructure.input.api.exception;

import com.bcnc.test.domain.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler for the application.
 *
 * <p>This class captures exceptions thrown from the controllers and translates them into a
 * consistent JSON error response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles the case where a price is not found.
   *
   * @param ex The {@link PriceNotFoundException} that was thrown.
   * @return A {@link ResponseEntity} with a 404 Not Found status and a standardized error body.
   */
  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles the case where a request parameter has an invalid type.
   *
   * @param ex The {@link MethodArgumentTypeMismatchException} that was thrown.
   * @return A {@link ResponseEntity} with a 400 Bad Request status and a standardized error body.
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(
            String.format("Invalid parameter value: '%s'", ex.getValue()), LocalDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles the case where a required request parameter is missing.
   *
   * @param ex The {@link MissingServletRequestParameterException} that was thrown.
   * @return A {@link ResponseEntity} with a 400 Bad Request status and a standardized error body.
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(
            String.format("Required parameter '%s' is not present", ex.getParameterName()),
            LocalDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }
}
