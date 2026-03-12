package com.bcnc.test.infrastructure.output.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Represents a price record in the database.
 *
 * <p>This entity is mapped to the "PRICES" table and contains all the fields related to a price.
 */
@Entity
@Table(name = "PRICES")
@Data
public class PriceEntity {

  /** The unique identifier of the price record. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** The brand associated with this price. */
  @ManyToOne
  @JoinColumn(name = "BRAND_ID")
  private BrandEntity brand;

  /** The start date and time from which this price is valid. */
  private LocalDateTime startDate;

  /** The end date and time until which this price is valid. */
  private LocalDateTime endDate;

  /** The price list associated with this price. */
  private Integer priceList;

  /** The identifier of the product for which this price is defined. */
  private Integer productId;

  /**
   * The priority of the price. Higher values indicate higher priority in case of overlapping price
   * rules.
   */
  private Integer priority;

  /** The final selling price of the product. */
  private Double price;

  /** The currency of the price (e.g., "EUR"). */
  private String curr;
}
