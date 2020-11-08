package com.knife.agilemind.repository.story;

import com.knife.agilemind.domain.story.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage stories in database
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
}
