package com.bcnc.test.infrastructure.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bcnc.test.application.service.PriceService;
import com.bcnc.test.domain.model.PriceDomain;
import com.bcnc.test.infrastructure.api.mapper.PriceResponseMapper;
import com.bcnc.test.infrastructure.api.model.response.PriceResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** Price controller test. */
@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  @InjectMocks private PriceController priceController;

  @Mock private PriceService priceService;

  @Mock private PriceResponseMapper priceResponseMapper;

  @Test
  @DisplayName("Should return a price when found")
  void testGetPrice() {
    Integer brandId = 1;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    PriceDomain priceDomain = new PriceDomain();
    PriceResponse priceResponse = new PriceResponse(productId, brandId, 1, date, date, 35.50);
    when(priceService.findPrice(brandId, productId, date)).thenReturn(Optional.of(priceDomain));
    when(priceResponseMapper.toResponse(priceDomain)).thenReturn(priceResponse);

    ResponseEntity<PriceResponse> result = priceController.getPrice(brandId, productId, date);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(priceResponse, result.getBody());
    assertEquals(brandId, result.getBody().getBrandId());
    assertEquals(productId, result.getBody().getProductId());
  }

  @Test
  @DisplayName("Should return not found when price is not found")
  void testGetPrice_whenNotFound() {
    Integer brandId = 1;
    Integer productId = 35455;
    LocalDateTime date = LocalDateTime.now();
    when(priceService.findPrice(brandId, productId, date)).thenReturn(Optional.empty());

    ResponseEntity<PriceResponse> result = priceController.getPrice(brandId, productId, date);

    assertEquals(ResponseEntity.notFound().build(), result);
  }
}
