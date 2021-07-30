package com.naberss.SocialMediaMockup.Services;

import com.naberss.SocialMediaMockup.Exceptions.MongoResourceNotFound;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.repositories.UserRepository;
import com.naberss.SocialMediaMockup.services.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@Tag("Service_Test")
@ExtendWith(MockitoExtension.class)
@DisplayName("User Service - Tests")
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("User Service -> Insert Test")
    void InsertTest() {
        User user = new User();
        Mockito.when(userService.insert(any(User.class))).thenReturn(user);
        assertEquals(user, userService.insert(user));
        Mockito.verify(userRepository, Mockito.times(1)).insert(any(User.class));
    }

    @Test
    @DisplayName("User Service -> Find By ID Test")
    void findByIdTest() {
        assertThrows(MongoResourceNotFound.class, () -> userService.findById(anyString()));
        Mockito.verify(userRepository, Mockito.atMostOnce()).findById(anyString());
    }

    @Test
    @DisplayName("User Service -> Find By Name Test")
    void findByNameTest() {
        List<User> list = new ArrayList<>();
        assertThrows(MongoResourceNotFound.class, () -> userService.findByName(anyString()));
        Mockito.verify(userRepository, Mockito.times(1)).findbyName(anyString());
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
        assertThrows(MongoResourceNotFound.class, () -> userService.Update("test",new User()));
    }

    @Test
    @DisplayName("User Service -> Delete Test")
    void deleteTest() {
        userService.delete("1");
        Mockito.verify(userRepository, Mockito.atMostOnce()).deleteById("1");
    }

}