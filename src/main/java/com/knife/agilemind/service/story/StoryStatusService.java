package com.knife.agilemind.service.story;

import com.knife.agilemind.constant.story.StoryStatusConstant;
import com.knife.agilemind.domain.story.StoryStatusEntity;
import com.knife.agilemind.dto.story.StoryStatusDTO;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.story.StoryStatusRepository;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to manage story status
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class StoryStatusService {
    @Autowired
    private StoryStatusRepository storyStatusRepository;

    @Autowired
    private UserService userService;

    /**
     * Get all story status
     *
     * @return All story status DTO
     */
    public List<StoryStatusDTO> getAll() {
        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        List<StoryStatusDTO> results = new ArrayList<>();
        List<StoryStatusEntity> entities = this.storyStatusRepository.findAll();

        for (StoryStatusEntity entity : entities) {
            results.add(new StoryStatusDTO().setId(entity.getId()).setKey(entity.getKey()));
        }

        return results;
    }

    /**
     * Find story status in database by the specified ID
     *
     * @param id The status id to find
     */
    public StoryStatusEntity findById(Long id) {
        TechnicalAssert.notNull(id);

        StoryStatusEntity results = this.storyStatusRepository.findById(id).orElse(null);

        BusinessAssert.notNull(results, StoryStatusConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        return results;
    }

    /**
     * Assert the specified story status ID exists in database
     *
     * @param id The story status id to check
     */
    public void assertExists(Long id) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(id);

        BusinessAssert.notNull(this.findById(id), StoryStatusConstant.Error.NOT_FOUND, Status.NOT_FOUND);
    }
}
