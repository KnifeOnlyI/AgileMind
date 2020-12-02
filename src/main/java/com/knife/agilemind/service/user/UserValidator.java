package com.knife.agilemind.service.user;

import com.knife.agilemind.constant.user.UserConstant;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.TechnicalAssert;
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
     * Assert and get a user with the specified ID exists in database
     *
     * @param id The ID of user to check
     */
    public UserEntity findById(Long id) {
        TechnicalAssert.notNull(id);

        UserEntity entity = this.userRepository.findById(id).orElse(null);

        BusinessAssert.notNull(entity, UserConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        return entity;
    }
}
