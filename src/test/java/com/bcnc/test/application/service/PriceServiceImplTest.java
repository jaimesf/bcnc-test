package com.bcnc.test.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.bcnc.test.application.repository.PriceRepository;
import com.bcnc.test.domain.model.PriceDomain;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Price service impl test. */
@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

  @Mock private PriceRepository priceRepository;

  @InjectMocks private PriceServiceImpl priceService;

  /** Test find price. */
  @Test
  @DisplayName("Should return a price when found")
  void testFindPrice() {
    Integer brandId = 1;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    PriceDomain priceDomain =
        new PriceDomain(1L, brandId, date, date, 1, productId, 1, 35.50, "EUR");
    when(priceRepository.findPrice(brandId, productId, date)).thenReturn(Optional.of(priceDomain));

    Optional<PriceDomain> result = priceService.findPrice(brandId, productId, date);

    assertTrue(result.isPresent());
    assertEquals(priceDomain, result.get());
    assertEquals(brandId, result.get().brandId());
    assertEquals(productId, result.get().productId());
  }

  /** Test find price when not found. */
  @Test
  @DisplayName("Should return an empty optional when price is not found")
  void testFindPrice_whenNotFound() {
    Integer brandId = 1;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    when(priceRepository.findPrice(brandId, productId, date)).thenReturn(Optional.empty());

    Optional<PriceDomain> result = priceService.findPrice(brandId, productId, date);

    assertTrue(result.isEmpty());
  }
}
