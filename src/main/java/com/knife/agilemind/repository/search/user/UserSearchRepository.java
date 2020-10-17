package com.knife.agilemind.repository.search.user;

import com.knife.agilemind.domain.user.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<UserEntity, Long> {
}
