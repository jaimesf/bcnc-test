package com.bcnc.test.infrastructure.input.api.exception;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response returned by the API.
 *
 * @param message A descriptive message explaining the error.
 * @param timestamp The exact time when the error occurred.
 */
public record ErrorResponse(String message, LocalDateTime timestamp) {}
