package com.bcnc.test.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.infrastructure.entity.PriceEntity;
import com.bcnc.test.infrastructure.mapper.PriceMapper;
import com.bcnc.test.infrastructure.repository.jpa.JpaPriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryImplTest {

  @Mock private JpaPriceRepository jpaPriceRepository;

  @Mock private PriceMapper priceMapper;

  @InjectMocks private PriceRepositoryImpl priceRepository;

  @Test
  @DisplayName("Should return a price when found")
  void testFindPrice() {
    Integer brandId = 1;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    PriceEntity priceEntity = new PriceEntity();
    PriceDomain priceDomain = new PriceDomain();

    when(jpaPriceRepository
            .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, date, date))
        .thenReturn(Optional.of(priceEntity));
    when(priceMapper.toDomain(priceEntity)).thenReturn(priceDomain);

    Optional<PriceDomain> result = priceRepository.findPrice(brandId, productId, date);

    assertEquals(Optional.of(priceDomain), result);
  }

  @Test
  @DisplayName("Should return an empty optional when price is not found")
  void testFindPrice_whenNotFound() {
    Integer brandId = 1;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();

    when(jpaPriceRepository
            .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, date, date))
        .thenReturn(Optional.empty());

    Optional<PriceDomain> result = priceRepository.findPrice(brandId, productId, date);

    assertTrue(result.isEmpty());
  }
}
