package com.knife.agilemind.mapper;

import java.util.List;
import java.util.Set;

/**
 * Default entity mapper
 *
 * @param <E> Entity type
 * @param <D> DTO type
 */
public interface EntityMapper<E, D> {
    /**
     * DTO to entity
     *
     * @param dto The DTO
     *
     * @return The entity
     */
    E toEntity(D dto);

    /**
     * Entity to DTO
     *
     * @param entity The entity
     *
     * @return The DTO
     */
    D toDTO(E entity);

    /**
     * DTOs to entities
     *
     * @param dtos The DTOs
     *
     * @return The entities
     */
    List<E> toEntities(List<D> dtos);

    /**
     * Entities to DTOs
     *
     * @param entities The entities
     *
     * @return The DTOs
     */
    List<D> toDTOs(List<E> entities);

    /**
     * DTOs to entities
     *
     * @param dtos The DTOs
     *
     * @return The entities
     */
    Set<E> toEntities(Set<D> dtos);

    /**
     * Entities to DTOs
     *
     * @param entities The entities
     *
     * @return The DTOs
     */
    Set<D> toDTOs(Set<E> entities);
}
