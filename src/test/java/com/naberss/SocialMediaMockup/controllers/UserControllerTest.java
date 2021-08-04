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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    User user;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user = new User();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
    }

    @Test
    void findById() throws Exception {
        //Mock Test Request
        mockMvc.perform(get("/users/findById2/1")).andExpect(status()
                .isOk())
                .andExpect(mvcResult -> userController.findById2(Mockito.anyString()));
        //----------------------------------------------------------------//
        //Mock Test Return
        ResponseEntity<User> user2 = ResponseEntity.ok().body(user);
        assertEquals(user2,userController.findById2(Mockito.anyString()));
    }
}