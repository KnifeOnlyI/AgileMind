package com.knife.agilemind.repository.story;

import com.knife.agilemind.domain.story.StoryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage story status in database
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Repository
public interface StoryStatusRepository extends JpaRepository<StoryStatusEntity, Long> {
}
