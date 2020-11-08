package com.knife.agilemind.mapper.user;

import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.user.UserDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link UserMapper}.
 */
class UserMapperTest {

    private static final String DEFAULT_LOGIN = "johndoe";
    private static final Long DEFAULT_ID = 1L;

    private UserMapper userMapper;
    private UserEntity user;
    private UserDTO userDto;

    @BeforeEach
    void init() {
        userMapper = new UserMapper();
        user = new UserEntity();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setImageUrl("image_url");
        user.setLangKey("en");

        userDto = new UserDTO(user);
    }

    @Test
    void testUsersToUserDTOsShouldMapOnlyNonNullUsers() {
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        users.add(null);

        List<UserDTO> userDTOS = userMapper.usersToUserDTOs(users);

        assertThat(userDTOS).isNotEmpty();
        assertThat(userDTOS).size().isEqualTo(1);
    }

    @Test
    void testUserDTOsToUsersShouldMapOnlyNonNullUsers() {
        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);
        usersDto.add(null);

        List<UserEntity> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
    }

    @Test
    void testUserDTOsToUsersWithAuthoritiesStringShouldMapToUsersWithAuthoritiesDomain() {
        Set<String> authoritiesAsString = new HashSet<>();
        authoritiesAsString.add("ADMIN");
        userDto.setAuthorities(authoritiesAsString);

        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<UserEntity> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getAuthorities()).isNotNull();
        assertThat(users.get(0).getAuthorities()).isNotEmpty();
        assertThat(users.get(0).getAuthorities().iterator().next().getName()).isEqualTo("ADMIN");
    }

    @Test
    void testUserDTOsToUsersMapWithNullAuthoritiesStringShouldReturnUserWithEmptyAuthorities() {
        userDto.setAuthorities(null);

        List<UserDTO> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        List<UserEntity> users = userMapper.userDTOsToUsers(usersDto);

        assertThat(users).isNotEmpty();
        assertThat(users).size().isEqualTo(1);
        assertThat(users.get(0).getAuthorities()).isNotNull();
        assertThat(users.get(0).getAuthorities()).isEmpty();
    }

    @Test
    void testUserDTOToUserMapWithAuthoritiesStringShouldReturnUserWithAuthorities() {
        Set<String> authoritiesAsString = new HashSet<>();
        authoritiesAsString.add("ADMIN");
        userDto.setAuthorities(authoritiesAsString);

        UserEntity user = userMapper.userDTOToUser(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotNull();
        assertThat(user.getAuthorities()).isNotEmpty();
        assertThat(user.getAuthorities().iterator().next().getName()).isEqualTo("ADMIN");
    }

    @Test
    void testUserDTOToUserMapWithNullAuthoritiesStringShouldReturnUserWithEmptyAuthorities() {
        userDto.setAuthorities(null);

        UserEntity user = userMapper.userDTOToUser(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotNull();
        assertThat(user.getAuthorities()).isEmpty();
    }

    @Test
    void testUserDTOToUserMapWithNullUserShouldReturnNull() {
        assertThat(userMapper.userDTOToUser(null)).isNull();
    }

    @Test
    void testUserFromId() {
        assertThat(userMapper.idToUserEntity(DEFAULT_ID).getId()).isEqualTo(DEFAULT_ID);
        assertThat(userMapper.idToUserEntity(null)).isNull();
    }
}
