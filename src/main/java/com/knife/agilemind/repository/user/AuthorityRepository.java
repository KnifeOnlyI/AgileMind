package com.knife.agilemind.repository.user;

import com.knife.agilemind.domain.user.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link AuthorityEntity} entity.
 */
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
}
