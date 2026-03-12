package com.bcnc.test.infrastructure.input.api.controller;

import com.bcnc.test.domain.service.PriceService;
import com.bcnc.test.infrastructure.input.api.mapper.PriceResponseMapper;
import com.bcnc.test.infrastructure.input.api.model.response.PriceResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for retrieving price information. */
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
   * @param applicationDate The date for which to find the applicable price, in ISO DATE_TIME
   *     format.
   * @return A {@link PriceResponse} if a price is found.
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
  public PriceResponse getPrice(
      @Parameter(description = "ID of the brand", example = "1") @RequestParam("brand-id")
          Long brandId,
      @Parameter(description = "ID of the product", example = "35455") @RequestParam("product-id")
          Integer productId,
      @Parameter(
              description = "Date to check for the price in ISO DATE_TIME format",
              example = "2020-06-14T10:00:00")
          @RequestParam("application-date")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime applicationDate) {
    log.debug(
        "Request received for brandId: {}, productId: {}, date: {}",
        brandId,
        productId,
        applicationDate);
    return priceResponseMapper.toResponse(
        priceService.findPrice(brandId, productId, applicationDate));
  }
}
