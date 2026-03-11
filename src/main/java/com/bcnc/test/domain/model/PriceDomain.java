package com.bcnc.test.domain.model;

import java.time.LocalDateTime;

/**
 * Represents a price for a product in a specific brand and time range.
 *
 * <p>This class is a domain model that encapsulates all the details of a price, including its
 * validity dates, priority, and currency.
 *
 * @param id The unique identifier of the price record.
 * @param brandId The identifier of the brand associated with this price.
 * @param startDate The start date and time from which this price is valid.
 * @param endDate The end date and time until which this price is valid.
 * @param priceList The price list associated with this price.
 * @param productId The identifier of the product for which this price is defined.
 * @param priority The priority of the price. Higher values indicate higher priority in case of
 *     overlapping price rules.
 * @param price The final selling price of the product.
 * @param curr The currency of the price (e.g., "EUR").
 */
public record PriceDomain(
    Long id,
    Integer brandId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Integer priceList,
    Integer productId,
    Integer priority,
    Double price,
    String curr) {}
