package com.bcnc.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .param("brandId", "1")
                .param("productId", "35455")
                .param("date", "2020-06-14T10:00:00"))
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
                .param("brandId", "1")
                .param("productId", "35455")
                .param("date", "2020-06-14T16:00:00"))
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
                .param("brandId", "1")
                .param("productId", "35455")
                .param("date", "2020-06-14T21:00:00"))
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
                .param("brandId", "1")
                .param("productId", "35455")
                .param("date", "2020-06-15T10:00:00"))
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
                .param("brandId", "1")
                .param("productId", "35455")
                .param("date", "2020-06-16T21:00:00"))
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
    mockMvc
        .perform(
            get("/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("date", "2020-06-16 21:00:00"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test 7: Petición con datos que no existen")
  void shouldReturnNotFoundForNonExistentData() throws Exception {
    mockMvc
        .perform(
            get("/prices")
                .param("brandId", "2")
                .param("productId", "99999")
                .param("date", "2020-06-16T21:00:00"))
        .andExpect(status().isNotFound());
  }
}
