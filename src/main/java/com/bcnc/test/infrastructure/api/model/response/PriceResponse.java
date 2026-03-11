package com.bcnc.test.infrastructure.api.model.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the price information returned by the API.
 *
 * <p>This DTO contains the details of an applicable price for a product, including the product and
 * brand identifiers, the price list, the validity date range, and the final price.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {

  /** The identifier of the product. */
  private Integer productId;

  /** The identifier of the brand. */
  private Integer brandId;

  /** The price list associated with this price. */
  private Integer priceList;

  /** The start date and time from which this price is valid. */
  private LocalDateTime startDate;

  /** The end date and time until which this price is valid. */
  private LocalDateTime endDate;

  /** The final selling price of the product. */
  private Double price;
}
