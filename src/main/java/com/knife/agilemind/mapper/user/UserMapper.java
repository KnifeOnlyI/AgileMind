package com.knife.agilemind.mapper.user;

import com.knife.agilemind.domain.user.AuthorityEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link UserEntity} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    private static Set<AuthorityEntity> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<AuthorityEntity> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString.stream().map((String string) -> {
                AuthorityEntity auth = new AuthorityEntity();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public UserDTO userToUserDTO(UserEntity user) {
        return new UserDTO(user);
    }

    public List<UserDTO> usersToUserDTOs(Collection<UserEntity> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public List<UserEntity> userDTOsToUsers(Collection<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public UserEntity userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            UserEntity user = new UserEntity();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<AuthorityEntity> authorities = authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    public UserEntity idToUserEntity(Long id) {
        if (id == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(id);
        return user;
    }

    public Long userEntityToId(UserEntity userEntity) {
        return userEntity != null ? userEntity.getId() : null;
    }
}
