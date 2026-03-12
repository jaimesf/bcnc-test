package com.bcnc.test.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Exception thrown when a price is not found for the given criteria. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends RuntimeException {

  /**
   * Constructs a new PriceNotFoundException with the specified detail message.
   *
   * @param message the detail message.
   */
  public PriceNotFoundException(String message) {
    super(message);
  }
}
