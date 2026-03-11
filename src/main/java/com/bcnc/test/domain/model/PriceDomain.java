package com.bcnc.test.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a price for a product in a specific brand and time range.
 *
 * <p>This class is a domain model that encapsulates all the details of a price, including its
 * validity dates, priority, and currency.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDomain {

  /** The unique identifier of the price record. */
  private Long id;

  /** The identifier of the brand associated with this price. */
  private Integer brandId;

  /** The start date and time from which this price is valid. */
  private LocalDateTime startDate;

  /** The end date and time until which this price is valid. */
  private LocalDateTime endDate;

  /** The price list associated with this price. */
  private Integer priceList;

  /** The identifier of the product for which this price is defined. */
  private Integer productId;

  /**
   * The priority of the price. Higher values indicate higher priority in case of overlapping price
   * rules.
   */
  private Integer priority;

  /** The final selling price of the product. */
  private Double price;

  /** The currency of the price (e.g., "EUR"). */
  private String curr;
}
