package com.bcnc.test.infrastructure.api.mapper;

import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.infrastructure.api.model.response.PriceResponse;
import org.mapstruct.Mapper;

/**
 * Maps a {@link PriceDomain} domain object to a {@link PriceResponse} DTO.
 *
 * <p>This mapper is used to convert the internal domain model to the data transfer object that is
 * exposed by the API.
 */
@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

  /**
   * Converts a {@link PriceDomain} domain object to a {@link PriceResponse} DTO.
   *
   * @param priceDomain The {@link PriceDomain} object to convert.
   * @return The corresponding {@link PriceResponse} DTO.
   */
  PriceResponse toResponse(PriceDomain priceDomain);
}
