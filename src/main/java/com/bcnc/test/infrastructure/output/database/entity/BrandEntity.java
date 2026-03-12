package com.bcnc.test.infrastructure.output.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a brand record in the database.
 *
 * <p>This entity is mapped to the "BRAND" table.
 */
@Entity
@Table(name = "BRAND")
@Getter
@Setter
public class BrandEntity {

  /** The unique identifier of the brand. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** The name of the brand. */
  private String name;
}
