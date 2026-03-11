package com.bcnc.test.application.service;

import com.bcnc.test.application.repository.PriceRepository;
import com.bcnc.test.domain.model.PriceDomain;
import java.time.LocalDateTime;
import java.util.Optional;
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
   * @param date The date for which to find the applicable price.
   * @return An {@link Optional} containing the applicable {@link PriceDomain}, or an empty {@link
   *     Optional} if no price is found.
   */
  @Override
  public Optional<PriceDomain> findPrice(Integer brandId, Integer productId, LocalDateTime date) {
    return priceRepository.findPrice(brandId, productId, date);
  }
}
