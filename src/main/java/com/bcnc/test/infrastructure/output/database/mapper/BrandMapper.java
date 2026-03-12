package com.bcnc.test.infrastructure.output.database.mapper;

import com.bcnc.test.domain.model.BrandDomain;
import com.bcnc.test.infrastructure.output.database.entity.BrandEntity;
import org.mapstruct.Mapper;

/**
 * Maps a {@link BrandEntity} to a {@link BrandDomain} domain object.
 *
 * <p>This mapper is used to convert the database entity to the internal domain model.
 */
@Mapper(componentModel = "spring")
public interface BrandMapper {

  /**
   * Converts a {@link BrandEntity} to a {@link BrandDomain} domain object.
   *
   * @param brandEntity The {@link BrandEntity} to convert.
   * @return The corresponding {@link BrandDomain} domain object.
   */
  BrandDomain toDomain(BrandEntity brandEntity);
}
