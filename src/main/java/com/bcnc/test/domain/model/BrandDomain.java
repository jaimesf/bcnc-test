package com.bcnc.test.domain.model;

/**
 * Represents a Brand in the domain layer.
 *
 * @param id The unique identifier of the brand.
 * @param name The name of the brand.
 */
public record BrandDomain(Long id, String name) {}
