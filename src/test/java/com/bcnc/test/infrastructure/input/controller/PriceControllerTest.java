package com.bcnc.test.infrastructure.input.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bcnc.test.domain.model.BrandDomain;
import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.domain.service.PriceService;
import com.bcnc.test.infrastructure.input.api.controller.PriceController;
import com.bcnc.test.infrastructure.input.api.mapper.PriceResponseMapper;
import com.bcnc.test.infrastructure.input.api.model.response.PriceResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Price controller test. */
@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  @InjectMocks private PriceController priceController;

  @Mock private PriceService priceService;

  @Mock private PriceResponseMapper priceResponseMapper;

  @Test
  @DisplayName("Should return a price when found")
  void testGetPrice() {
    Long brandId = 1L;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    BrandDomain brandDomain = new BrandDomain(brandId, "ZARA");
    PriceDomain priceDomain =
        new PriceDomain(1L, brandDomain, date, date, 1, productId, 1, 35.50, "EUR");
    PriceResponse priceResponse = new PriceResponse(productId, brandId, 1, date, date, 35.50);
    when(priceService.findPrice(brandId, productId, date)).thenReturn(priceDomain);
    when(priceResponseMapper.toResponse(priceDomain)).thenReturn(priceResponse);

    PriceResponse result = priceController.getPrice(brandId, productId, date);

    assertEquals(priceResponse, result);
    assertEquals(brandId, result.brandId());
    assertEquals(productId, result.productId());
  }
}
