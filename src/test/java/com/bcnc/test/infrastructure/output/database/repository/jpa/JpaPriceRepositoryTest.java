package com.bcnc.test.infrastructure.output.database.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.bcnc.test.infrastructure.output.database.entity.BrandEntity;
import com.bcnc.test.infrastructure.output.database.entity.PriceEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class JpaPriceRepositoryTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private JpaPriceRepository jpaPriceRepository;

  private BrandEntity zaraBrand;

  @BeforeEach
  void setUp() {
    zaraBrand = new BrandEntity();
    zaraBrand.setName("ZARA");
    entityManager.persist(zaraBrand);

    // Price 1 (Priority 0)
    PriceEntity price1 = new PriceEntity();
    price1.setBrand(zaraBrand);
    price1.setProductId(35455);
    price1.setPriceList(1);
    price1.setPriority(0);
    price1.setPrice(35.50);
    price1.setCurr("EUR");
    price1.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
    price1.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
    entityManager.persist(price1);

    // Price 2 (Priority 1)
    PriceEntity price2 = new PriceEntity();
    price2.setBrand(zaraBrand);
    price2.setProductId(35455);
    price2.setPriceList(2);
    price2.setPriority(1);
    price2.setPrice(25.45);
    price2.setCurr("EUR");
    price2.setStartDate(LocalDateTime.parse("2020-06-14T15:00:00"));
    price2.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
    entityManager.persist(price2);

    entityManager.flush();
  }

  @Test
  @DisplayName("Should return the price with the highest priority when multiple prices match")
  void shouldReturnHighestPriorityPrice() {
    LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");

    Optional<PriceEntity> foundPrice =
        jpaPriceRepository
            .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                zaraBrand.getId(), 35455, applicationDate, applicationDate);

    assertThat(foundPrice).isPresent();
    assertThat(foundPrice.get().getPriceList()).isEqualTo(2);
    assertThat(foundPrice.get().getPriority()).isEqualTo(1);
    assertThat(foundPrice.get().getPrice()).isEqualTo(25.45);
  }

  @Test
  @DisplayName("Should set audit fields on creation")
  void shouldSetAuditFieldsOnCreation() {
    BrandEntity newBrand = new BrandEntity();
    newBrand.setName("Pull&Bear");

    BrandEntity savedBrand = entityManager.persist(newBrand);
    entityManager.flush();
    entityManager.clear();

    BrandEntity foundBrand = entityManager.find(BrandEntity.class, savedBrand.getId());

    assertThat(foundBrand.getCreationDate()).isNotNull();
    assertThat(foundBrand.getCreationUser()).isEqualTo("system");
    assertThat(foundBrand.getModificationDate()).isNotNull();
    assertThat(foundBrand.getModificationUser()).isEqualTo("system");
    assertThat(foundBrand.getCreationDate()).isEqualTo(foundBrand.getModificationDate());
  }

  @Test
  @DisplayName("Should update modification audit fields on update")
  void shouldUpdateAuditFieldsOnUpdate() throws InterruptedException {
    // Persist initial entity
    BrandEntity newBrand = new BrandEntity();
    newBrand.setName("Stradivarius");
    BrandEntity savedBrand = entityManager.persistAndFlush(newBrand);
    Long brandId = savedBrand.getId();

    // Detach the entity to get a fresh state
    entityManager.clear();

    // Find the entity again to get its initial state
    BrandEntity brandToUpdate = entityManager.find(BrandEntity.class, brandId);
    LocalDateTime initialCreationDate = brandToUpdate.getCreationDate();
    LocalDateTime initialModificationDate = brandToUpdate.getModificationDate();

    // Ensure modification time will be different
    Thread.sleep(10);

    // Update entity
    brandToUpdate.setName("Stradivarius Updated");
    entityManager.flush();
    entityManager.clear();

    // Find the updated entity
    BrandEntity updatedBrand = entityManager.find(BrandEntity.class, brandId);

    assertThat(updatedBrand.getCreationDate()).isEqualTo(initialCreationDate);
    assertThat(updatedBrand.getModificationDate()).isAfter(initialModificationDate);
    assertThat(updatedBrand.getModificationUser()).isEqualTo("system");
  }
}
