package com.bcnc.test.infrastructure.output.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.bcnc.test.domain.model.BrandDomain;
import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.infrastructure.output.database.entity.PriceEntity;
import com.bcnc.test.infrastructure.output.database.mapper.PriceMapper;
import com.bcnc.test.infrastructure.output.database.repository.PriceRepositoryImpl;
import com.bcnc.test.infrastructure.output.database.repository.jpa.JpaPriceRepository;
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
    Long brandId = 1L;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    PriceEntity priceEntity = new PriceEntity();
    BrandDomain brandDomain = new BrandDomain(brandId, "ZARA");
    PriceDomain priceDomain =
        new PriceDomain(1L, brandDomain, date, date, 1, productId, 1, 35.50, "EUR");

    when(jpaPriceRepository
            .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, date, date))
        .thenReturn(Optional.of(priceEntity));
    when(priceMapper.toDomain(priceEntity)).thenReturn(priceDomain);

    Optional<PriceDomain> result = priceRepository.findPrice(brandId, productId, date);

    assertTrue(result.isPresent());
    assertEquals(priceDomain, result.get());
    assertEquals(brandId, result.get().brand().id());
    assertEquals(productId, result.get().productId());
  }

  @Test
  @DisplayName("Should return an empty optional when price is not found")
  void testFindPrice_whenNotFound() {
    Long brandId = 1L;
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
