package com.knife.agilemind.service.user;

import com.knife.agilemind.domain.user.AuthorityEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service to manage user
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
@Service
public class UserService extends DefaultUserService {
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get the user with the specified ID
     *
     * @param id The ID
     *
     * @return The founded user
     */
    public UserEntity get(Long id) {
        this.userValidator.assertNotNullID(id);

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

        if (authentication != null) {
            authenticatedUser = this.userRepository.findOneByLogin(authentication.getName())
                .orElseThrow(TechnicalException::new);
        }

        return authenticatedUser;
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
     * Check if the authenticated user is admin
     *
     * @return TRUE if he is admin, FALSE otherwise
     */
    public boolean currentIsAdmin() {
        boolean hasAdmin = false;

        UserEntity authenticatedUser = this.getLoggedUser();

        if (authenticatedUser != null) {
            for (AuthorityEntity authority : authenticatedUser.getAuthorities()) {
                if (authority.getName().equals("ROLE_ADMIN")) {
                    hasAdmin = true;
                    break;
                }
            }
        }

        return hasAdmin;
    }
}
