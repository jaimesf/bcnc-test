package com.bcnc.test.infrastructure.output.database.repository.jpa;

import com.bcnc.test.infrastructure.output.database.entity.PriceEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA repository for {@link PriceEntity} objects. */
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

  /**
   * Finds the applicable price for a given brand, product, and date, returning the one with the
   * highest priority.
   *
   * @param brandId The identifier of the brand.
   * @param productId The identifier of the product.
   * @param startDate The date to check against the start date of the price.
   * @param endDate The date to check against the end date of the price.
   * @return An {@link Optional} containing the {@link PriceEntity} with the highest priority, or an
   *     empty {@link Optional} if no applicable price is found.
   */
  Optional<PriceEntity>
      findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
          Long brandId, Integer productId, LocalDateTime startDate, LocalDateTime endDate);
}
