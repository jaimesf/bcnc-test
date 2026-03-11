package com.bcnc.test.infrastructure.repository;

import com.bcnc.test.application.repository.PriceRepository;
import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.infrastructure.mapper.PriceMapper;
import com.bcnc.test.infrastructure.repository.jpa.JpaPriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the price repository that interacts with the database.
 *
 * <p>This class implements the {@link PriceRepository} interface and uses a Spring Data JPA
 * repository to fetch price data from the database.
 */
@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

  private final JpaPriceRepository jpaPriceRepository;
  private final PriceMapper priceMapper;

  /**
   * Finds the applicable price for a given brand, product, and date by querying the database.
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
    return jpaPriceRepository
        .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            brandId, productId, date, date)
        .map(priceMapper::toDomain);
  }
}
