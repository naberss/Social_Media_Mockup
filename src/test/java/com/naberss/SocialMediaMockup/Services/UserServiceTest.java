package com.naberss.SocialMediaMockup.Services;

import com.naberss.SocialMediaMockup.Exceptions.MongoResourceNotFound;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.repositories.UserRepository;
import com.naberss.SocialMediaMockup.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@Tag("Service_Test")
@ExtendWith(MockitoExtension.class)
@DisplayName("User Service - Tests")
class UserServiceTest {

    User user;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("User Service -> Insert Test")
    void InsertTest() {
        Mockito.when(userService.insert(any(User.class))).thenReturn(user);
        assertEquals(user, userService.insert(user));
        Mockito.verify(userRepository, Mockito.times(1)).insert(any(User.class));
    }

    @Test
    @DisplayName("User Service -> Insert Lambda Test")
    void InsertLambdaTest() {
        user.setName("teste");
        Mockito.when(userService.insert(argThat(x -> x.getName().equals("teste")))).thenReturn(user);
        assertEquals(user, userService.insert(user));
        Mockito.verify(userRepository, Mockito.times(1)).insert(any(User.class));
    }

    @Test
    @DisplayName("User Service -> Find By ID Test")
    void findByIdTest() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        assertEquals(Optional.of(user), userRepository.findById(anyString()));
        Mockito.verify(userRepository, Mockito.atMostOnce()).findById(anyString());
    }

    @Test
    @DisplayName("User Service -> Find By ID - BDD Mockito")
    void findByIdBddMockito() {
        given(userRepository.findById(anyString())).willReturn(Optional.of(user));
        assertEquals(Optional.of(user), userRepository.findById(anyString()));
        then(userRepository).should(times(1)).findById(anyString());
        then(userRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("User Service -> Find By Name Test")
    void findByNameTest() {
        List<User> users = new ArrayList<>();
        Mockito.when(userRepository.findbyName(anyString())).thenReturn(users);
        assertEquals(users, userRepository.findbyName(anyString()));
        Mockito.verify(userRepository, Mockito.times(1)).findbyName(anyString());
        //------------------------------------------------------------------------------------------//
        assertThrows(MongoResourceNotFound.class, () -> userService.findByName(anyString()));
        Mockito.verify(userRepository, Mockito.times(2)).findbyName(anyString());
    }

    @Test
    @DisplayName("User Service -> Find By Email Test")
    void findByEmailTest() {
        assertThrows(MongoResourceNotFound.class, () -> userService.findbyEmail(anyString()));
        Mockito.verify(userRepository, Mockito.times(1)).findbyEmail(anyString());
    }

    @Test
    @DisplayName("User Service -> Find All Test")
    void findByAllTest() {
        assertThrows(MongoResourceNotFound.class, () -> userService.findAll());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }


    @Test
    @DisplayName("User Service -> Update Test")
    void UpdateTest() {
        assertThrows(MongoResourceNotFound.class, () -> userService.Update("test", new User()));
    }

    @Test
    @DisplayName("User Service -> Delete Test")
    void deleteTest() {
        userService.delete("1");
        Mockito.verify(userRepository, Mockito.atMostOnce()).deleteById("1");
    }

}