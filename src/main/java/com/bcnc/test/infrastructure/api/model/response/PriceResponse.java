package com.bcnc.test.infrastructure.api.model.response;

import java.time.LocalDateTime;

/**
 * Represents the price information returned by the API.
 *
 * <p>This DTO contains the details of an applicable price for a product, including the product and
 * brand identifiers, the price list, the validity date range, and the final price.
 *
 * @param productId The identifier of the product.
 * @param brandId The identifier of the brand.
 * @param priceList The price list associated with this price.
 * @param startDate The start date and time from which this price is valid.
 * @param endDate The end date and time until which this price is valid.
 * @param price The final selling price of the product.
 */
public record PriceResponse(
    Integer productId,
    Integer brandId,
    Integer priceList,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Double price) {}
