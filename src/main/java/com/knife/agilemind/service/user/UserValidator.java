package com.knife.agilemind.service.user;

import com.knife.agilemind.constant.user.UserConstant;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

/**
 * Service to manage users validations
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class UserValidator {
    @Autowired
    private UserRepository userRepository;

    /**
     * Assert the specified ID is not null
     *
     * @param id The user ID to check
     */
    public void assertNotNullID(Long id) {
        if (id == null) {
            throw new TechnicalException();
        }
    }

    /**
     * Assert the specified user id is valid
     *
     * @param userId The user id to check
     */
    public void assertExists(Long userId) {
        if (userId == null) {
            throw new TechnicalException();
        }

        if (this.userRepository.findById(userId).orElse(null) == null) {
            throw new BusinessException(UserConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }

    /**
     * Assert and get a user with the specified ID exists in database
     *
     * @param id The ID of user to check
     */
    public UserEntity findById(Long id) {
        UserEntity entity = this.userRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new BusinessException(UserConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        return entity;
    }
}
