package com.knife.agilemind.repository.story;

import com.knife.agilemind.domain.story.StoryTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage story type in database
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Repository
public interface StoryTypeRepository extends JpaRepository<StoryTypeEntity, Long> {
}
