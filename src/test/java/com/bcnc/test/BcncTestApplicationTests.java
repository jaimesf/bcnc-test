package com.bcnc.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** BCNC test application tests. */
@SpringBootTest
@AutoConfigureMockMvc
class BcncTestApplicationTests {

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("Test 1: Petición a las 10:00 del día 14 para producto 35455 y brand 1 (ZARA)")
  void shouldReturnPriceForZaraAt10AmOnDay14() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T10:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Test 2: Petición a las 16:00 del día 14 para producto 35455 y brand 1 (ZARA)")
  void shouldReturnPriceForZaraAt4PmOnDay14() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T16:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Test 3: Petición a las 21:00 del día 14 para producto 35455 y brand 1 (ZARA)")
  void shouldReturnPriceForZaraAt9PmOnDay14() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T21:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Test 4: Petición a las 10:00 del día 15 para producto 35455 y brand 1 (ZARA)")
  void shouldReturnPriceForZaraAt10AmOnDay15() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-15T10:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(3))
        .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
        .andExpect(jsonPath("$.price").value(30.50));
  }

  @Test
  @DisplayName("Test 5: Petición a las 21:00 del día 16 para producto 35455 y brand 1 (ZARA)")
  void shouldReturnPriceForZaraAt9PmOnDay16() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-16T21:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(4))
        .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(38.95));
  }

  @Test
  @DisplayName("Test 6: Petición con formato de fecha incorrecto")
  void shouldReturnBadRequestForInvalidDateFormat() throws Exception {
    String invalidDate = "2020-06-16 21:00:00";
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", invalidDate))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid parameter value: '" + invalidDate + "'"))
        .andExpect(jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Test 7: Petición con datos que no existen")
  void shouldReturnNotFoundForNonExistentData() throws Exception {
    long brandId = 2L;
    int productId = 99999;
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0, 0);
    String formattedDate = applicationDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    String expectedMessage =
        String.format(
            "No price found for brand %d, product %d, and date %s",
            brandId, productId, applicationDate);

    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", String.valueOf(brandId))
                .param("product-id", String.valueOf(productId))
                .param("application-date", formattedDate))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value(expectedMessage))
        .andExpect(jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Test 8: Petición en el límite inferior de la fecha")
  void shouldReturnPriceAtStartDateBoundary() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T15:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Test 9: Petición en el límite superior de la fecha")
  void shouldReturnPriceAtEndDateBoundary() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T18:30:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Test 10: Petición para los precios con rangos de fechas altos")
  void shouldReturnPriceForDateOnLongRangeDate() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-07-01T12:30:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(4))
        .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(38.95));
  }

  @Test
  @DisplayName("Should return Bad Request when brand-id is missing")
  void shouldReturnBadRequestWhenBrandIdIsMissing() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T10:00:00"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Required parameter 'brand-id' is not present"));
  }

  @Test
  @DisplayName("Should return Bad Request when product-id is missing")
  void shouldReturnBadRequestWhenProductIdIsMissing() throws Exception {
    mockMvc
        .perform(
            get("/prices").param("brand-id", "1").param("application-date", "2020-06-14T10:00:00"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Required parameter 'product-id' is not present"));
  }

  @Test
  @DisplayName("Should return Bad Request when application-date is missing")
  void shouldReturnBadRequestWhenApplicationDateIsMissing() throws Exception {
    mockMvc
        .perform(get("/prices").param("brand-id", "1").param("product-id", "35455"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message").value("Required parameter 'application-date' is not present"));
  }

  @Test
  @DisplayName("Should return Bad Request for non-numeric brand-id")
  void shouldReturnBadRequestForNonNumericBrandId() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "abc")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T10:00:00"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid parameter value: 'abc'"));
  }

  @Test
  @DisplayName("Should apply higher priority price at the exact start time")
  void shouldApplyHigherPriorityPriceAtExactStartTime() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T15:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Should apply lower priority price just before the higher priority one starts")
  void shouldApplyLowerPriorityPriceJustBeforeHigherPriorityStarts() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brand-id", "1")
                .param("product-id", "35455")
                .param("application-date", "2020-06-14T14:59:59"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(35.50));
  }
}
