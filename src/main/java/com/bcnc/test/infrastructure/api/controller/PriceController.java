package com.bcnc.test.infrastructure.api.controller;

import com.bcnc.test.application.service.PriceService;
import com.bcnc.test.infrastructure.api.mapper.PriceResponseMapper;
import com.bcnc.test.infrastructure.api.model.response.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for retrieving price information.
 *
 * <p>This controller exposes an endpoint to find the applicable price for a product based on the
 * brand, product, and a specific date.
 */
@Slf4j
@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

  private final PriceService priceService;
  private final PriceResponseMapper priceResponseMapper;

  /**
   * Retrieves the applicable price for a given brand, product, and date.
   *
   * @param brandId The identifier of the brand.
   * @param productId The identifier of the product.
   * @param date The date for which to find the applicable price, in ISO DATE_TIME format.
   * @return A {@link ResponseEntity} containing the {@link PriceResponse} if a price is found, or a
   *     404 Not Found response otherwise.
   */
  @Operation(
      summary = "Find applicable price",
      description = "Finds the applicable price for a given brand, product, and date.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Price found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PriceResponse.class))
            }),
        @ApiResponse(responseCode = "404", description = "Price not found", content = @Content)
      })
  @GetMapping
  public ResponseEntity<PriceResponse> getPrice(
      @Parameter(description = "ID of the brand", example = "1") @RequestParam Integer brandId,
      @Parameter(description = "ID of the product", example = "35455") @RequestParam
          Integer productId,
      @Parameter(
              description = "Date to check for the price in ISO DATE_TIME format",
              example = "2020-06-14T10:00:00")
          @RequestParam
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime date) {
    log.debug(
        "Request received for brandId: {}, productId: {}, date: {}", brandId, productId, date);
    return priceService
        .findPrice(brandId, productId, date)
        .map(priceResponseMapper::toResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
