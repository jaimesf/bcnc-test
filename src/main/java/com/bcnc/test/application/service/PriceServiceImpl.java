package com.bcnc.test.application.service;

import com.bcnc.test.domain.exception.PriceNotFoundException;
import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.domain.repository.PriceRepository;
import com.bcnc.test.domain.service.PriceService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation for retrieving price information.
 *
 * <p>This class implements the {@link PriceService} interface and provides the business logic for
 * finding applicable prices.
 */
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

  private final PriceRepository priceRepository;

  /**
   * Finds the applicable price for a given brand, product, and date by delegating to the price
   * repository.
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
  @Override
  public PriceDomain findPrice(Long brandId, Integer productId, LocalDateTime applicationDate) {
    return priceRepository
        .findPrice(brandId, productId, applicationDate)
        .orElseThrow(
            () ->
                new PriceNotFoundException(
                    String.format(
                        "No price found for brand %d, product %d, and date %s",
                        brandId, productId, applicationDate)));
  }
}
