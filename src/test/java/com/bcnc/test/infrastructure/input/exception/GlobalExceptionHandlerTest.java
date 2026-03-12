package com.bcnc.test.infrastructure.input.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bcnc.test.domain.exception.PriceNotFoundException;
import com.bcnc.test.infrastructure.input.api.exception.ErrorResponse;
import com.bcnc.test.infrastructure.input.api.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  @DisplayName("Should handle PriceNotFoundException and return 404 Not Found")
  void shouldHandlePriceNotFoundException() {
    PriceNotFoundException ex = new PriceNotFoundException("Price not found");

    ResponseEntity<ErrorResponse> responseEntity =
        globalExceptionHandler.handlePriceNotFoundException(ex);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals("Price not found", responseEntity.getBody().message());
    assertNotNull(responseEntity.getBody().timestamp());
  }

  @Test
  @DisplayName("Should handle MethodArgumentTypeMismatchException and return 400 Bad Request")
  void shouldHandleMethodArgumentTypeMismatchException() {
    MethodArgumentTypeMismatchException ex =
        new MethodArgumentTypeMismatchException("invalid", null, "param", null, null);

    ResponseEntity<ErrorResponse> responseEntity =
        globalExceptionHandler.handleMethodArgumentTypeMismatch(ex);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals("Invalid parameter value: 'invalid'", responseEntity.getBody().message());
    assertNotNull(responseEntity.getBody().timestamp());
  }

  @Test
  @DisplayName("Should handle MissingServletRequestParameterException and return 400 Bad Request")
  void shouldHandleMissingServletRequestParameterException() {
    MissingServletRequestParameterException ex =
        new MissingServletRequestParameterException("param", "String");

    ResponseEntity<ErrorResponse> responseEntity =
        globalExceptionHandler.handleMissingServletRequestParameter(ex);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals("Required parameter 'param' is not present", responseEntity.getBody().message());
    assertNotNull(responseEntity.getBody().timestamp());
  }
}
