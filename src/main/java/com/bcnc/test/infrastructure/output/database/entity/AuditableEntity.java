package com.bcnc.test.infrastructure.output.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract base class for entities that require auditing fields for creation and modification.
 *
 * <p>This class uses JPA lifecycle callbacks ({@code @PrePersist} and {@code @PreUpdate}) to
 * automatically manage the auditing fields.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity {

  /** Audit user. */
  public static final String SYSTEM_USER = "system";

  /** The user who created the record. */
  @Column(name = "CREATION_USER", nullable = false, updatable = false)
  private String creationUser;

  /** The date and time when the record was created. */
  @Column(name = "CREATION_DATE", nullable = false, updatable = false)
  private LocalDateTime creationDate;

  /** The user who last modified the record. */
  @Column(name = "MODIFICATION_USER", nullable = false)
  private String modificationUser;

  /** The date and time when the record was last modified. */
  @Column(name = "MODIFICATION_DATE", nullable = false)
  private LocalDateTime modificationDate;

  /**
   * Sets the creation and modification fields before the entity is first persisted.
   *
   * <p>This method is automatically called by JPA before an entity is inserted into the database.
   */
  @PrePersist
  protected void onCreate() {
    LocalDateTime now = LocalDateTime.now();
    creationDate = now;
    creationUser = SYSTEM_USER; // In a real app, this would come from the security context
    modificationDate = now;
    modificationUser = SYSTEM_USER;
  }

  /**
   * Sets the modification fields before the entity is updated.
   *
   * <p>This method is automatically called by JPA before an entity is updated in the database.
   */
  @PreUpdate
  protected void onUpdate() {
    modificationDate = LocalDateTime.now();
    modificationUser = SYSTEM_USER; // In a real app, this would come from the security context
  }
}
