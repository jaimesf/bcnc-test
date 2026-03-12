package com.bcnc.test.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.bcnc.test.domain.exception.PriceNotFoundException;
import com.bcnc.test.domain.model.BrandDomain;
import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.domain.repository.PriceRepository;
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
    Long brandId = 1L;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    BrandDomain brandDomain = new BrandDomain(brandId, "ZARA");
    PriceDomain priceDomain =
        new PriceDomain(1L, brandDomain, date, date, 1, productId, 1, 35.50, "EUR");
    when(priceRepository.findPrice(brandId, productId, date)).thenReturn(Optional.of(priceDomain));

    PriceDomain result = priceService.findPrice(brandId, productId, date);

    assertEquals(priceDomain, result);
    assertEquals(brandId, result.brand().id());
    assertEquals(productId, result.productId());
  }

  /** Test find price when not found. */
  @Test
  @DisplayName("Should throw PriceNotFoundException when price is not found")
  void testFindPrice_whenNotFound() {
    Long brandId = 1L;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    when(priceRepository.findPrice(brandId, productId, date)).thenReturn(Optional.empty());

    assertThrows(
        PriceNotFoundException.class, () -> priceService.findPrice(brandId, productId, date));
  }
}
