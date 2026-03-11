package com.bcnc.test.application.repository;

import com.bcnc.test.domain.model.PriceDomain;
import java.time.LocalDateTime;
import java.util.Optional;

/** Defines the contract for accessing price data from the data source. */
public interface PriceRepository {

  /**
   * Finds the applicable price for a given brand, product, and date.
   *
   * <p>This method returns the price with the highest priority if multiple prices are applicable
   * for the given parameters.
   *
   * @param brandId The identifier of the brand.
   * @param productId The identifier of the product.
   * @param date The date for which to find the applicable price.
   * @return An {@link Optional} containing the applicable {@link PriceDomain}, or an empty {@link
   *     Optional} if no price is found.
   */
  Optional<PriceDomain> findPrice(Integer brandId, Integer productId, LocalDateTime date);
}
