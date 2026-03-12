package com.bcnc.test.domain.service;

import com.bcnc.test.domain.exception.PriceNotFoundException;
import com.bcnc.test.domain.model.PriceDomain;
import java.time.LocalDateTime;

/** Defines the contract for retrieving price information. */
public interface PriceService {

  /**
   * Finds the applicable price for a given brand, product, and date.
   *
   * <p>This method returns the price with the highest priority if multiple prices are applicable
   * for the given parameters.
   *
   * @param brandId The identifier of the brand.
   * @param productId The identifier of the product.
   * @param applicationDate The date for which to find the applicable price.
   * @return The applicable {@link PriceDomain}.
   * @throws PriceNotFoundException if no price is found for the given criteria.
   */
  PriceDomain findPrice(Long brandId, Integer productId, LocalDateTime applicationDate);
}
