package com.knife.agilemind.service.story;

import com.knife.agilemind.constant.story.StoryTypeConstant;
import com.knife.agilemind.domain.story.StoryTypeEntity;
import com.knife.agilemind.dto.story.StoryTypeDTO;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.story.StoryTypeRepository;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to manage story type
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class StoryTypeService {
    @Autowired
    private StoryTypeRepository storyTypeRepository;

    @Autowired
    private UserService userService;

    /**
     * Get all story type
     *
     * @return All story type DTO
     */
    public List<StoryTypeDTO> getAll() {
        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        List<StoryTypeDTO> results = new ArrayList<>();
        List<StoryTypeEntity> entities = this.storyTypeRepository.findAll();

        for (StoryTypeEntity entity : entities) {
            results.add(new StoryTypeDTO().setId(entity.getId()).setKey(entity.getKey()));
        }

        return results;
    }

    /**
     * Find story type in database by the specified ID
     *
     * @param id The type id to find
     */
    public StoryTypeEntity findById(Long id) {
        StoryTypeEntity results = null;

        if (id != null) {
            results = this.storyTypeRepository.findById(id).orElse(null);
        }


        return results;
    }

    /**
     * Assert the specified story type ID exists in database
     *
     * @param id The story type id to check
     */
    public void assertExists(Long id) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(id);

        BusinessAssert.notNull(this.findById(id), StoryTypeConstant.Error.NOT_FOUND, Status.NOT_FOUND);
    }
}
