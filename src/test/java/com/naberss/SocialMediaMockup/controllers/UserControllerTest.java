package com.naberss.SocialMediaMockup.controllers;

import com.naberss.SocialMediaMockup.DTO.UserDTO;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.repositories.UserRepository;
import com.naberss.SocialMediaMockup.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    User user = new User("1", "teste", "teste_email");
    User user2 = new User("1", "teste", "teste_email");
    UserDTO userDTO = new UserDTO(user);

    @BeforeEach
    void setUp() {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
    }

    @Test
    void findById() {
        ResponseEntity<User> user2 = userController.findById2(Mockito.anyString());
        System.out.println(user2);
        /*assertEquals(user, userService.findById(Mockito.anyString()));*/

    }
}