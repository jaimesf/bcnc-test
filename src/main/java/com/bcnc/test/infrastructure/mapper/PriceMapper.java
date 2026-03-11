package com.bcnc.test.infrastructure.mapper;

import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.infrastructure.entity.PriceEntity;
import org.mapstruct.Mapper;

/**
 * Maps a {@link PriceEntity} to a {@link PriceDomain} domain object.
 *
 * <p>This mapper is used to convert the database entity to the internal domain model.
 */
@Mapper(componentModel = "spring")
public interface PriceMapper {

  /**
   * Converts a {@link PriceEntity} to a {@link PriceDomain} domain object.
   *
   * @param priceEntity The {@link PriceEntity} to convert.
   * @return The corresponding {@link PriceDomain} domain object.
   */
  PriceDomain toDomain(PriceEntity priceEntity);
}
