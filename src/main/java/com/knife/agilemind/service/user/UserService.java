package com.knife.agilemind.service.user;

import com.knife.agilemind.domain.user.AuthorityEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.HashSet;
import java.util.Set;

/**
 * Service to manage user
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Service
public class UserService extends DefaultUserService {
    @Autowired
    private UserValidator userValidator;

    /**
     * Get the user with the specified ID
     *
     * @param id The ID
     *
     * @return The founded user
     */
    public UserEntity findById(Long id) {
        this.assertLogged();

        TechnicalAssert.notNull(id);

        return this.userValidator.findById(id);
    }

    /**
     * Get the authenticated user
     *
     * @return The authenticated user
     */
    public UserEntity getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authenticatedUser = null;

        if (authentication != null &&
            (authentication.getPrincipal() != null && !authentication.getPrincipal().equals("anonymousUser"))
        ) {
            authenticatedUser = this.userRepository.findOneByLogin(authentication.getName())
                .orElseThrow(TechnicalException::new);
        }

        return authenticatedUser;
    }

    /**
     * Check if the user is not logged
     *
     * @return TRUE if the user is not logged, FALSE otherwise
     */
    public boolean userIsNotLogged() {
        return this.getLoggedUser() == null;
    }

    /**
     * Check if the user is logged
     *
     * @return TRUE if the user is logged, FALSE otherwise
     */
    public boolean userIsLogged() {
        return this.getLoggedUser() != null;
    }

    /**
     * Assert the current user is logged
     */
    public void assertLogged() {
        if (!this.userIsLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }
    }

    /**
     * Assert the current user is logged
     */
    public void assertAdmin() {
        if (!this.loggedUserIsAdmin()) {
            throw new BusinessException(Status.NOT_FOUND);
        }
    }

    /**
     * Check if the specified user is admin
     *
     * @return TRUE if the specified user is admin, FALSE otherwise
     */
    public boolean userIsAdmin(UserEntity user) {
        boolean isAdmin = false;

        if (user != null) {
            for (AuthorityEntity authority : user.getAuthorities()) {
                if (authority.getName().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }
        }

        return isAdmin;
    }

    /**
     * Check if the logged user is admin
     *
     * @return TRUE if he is admin, FALSE otherwise
     */
    public boolean loggedUserIsAdmin() {
        boolean isAdmin = false;

        UserEntity authenticatedUser = this.getLoggedUser();

        if (authenticatedUser != null) {
            for (AuthorityEntity authority : authenticatedUser.getAuthorities()) {
                if (authority.getName().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }
        }

        return isAdmin;
    }

    /**
     * Convert the entities to IDs
     *
     * @param entities The entities
     *
     * @return The IDs
     */
    public Set<Long> toIds(Set<UserEntity> entities) {
        Set<Long> ids = new HashSet<>();

        if (entities != null) {
            for (UserEntity entity : entities) {
                if (entity != null) {
                    ids.add(entity.getId());
                }
            }
        }

        return ids;
    }

    /**
     * Find all users by the specified ids
     *
     * @param ids The IDs
     *
     * @return The users
     */
    public Set<UserEntity> findAllById(Set<Long> ids) {
        return new HashSet<>(this.userRepository.findAllById(ids));
    }
}
