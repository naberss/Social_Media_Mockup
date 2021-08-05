package com.naberss.SocialMediaMockup.controllers;

import com.naberss.SocialMediaMockup.DTO.UserDTO;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.repositories.UserRepository;
import com.naberss.SocialMediaMockup.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    User user;

    ResponseEntity<List<UserDTO>> usersDto;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user = new User();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("User Controller - Find By ID (2) Test")
    void findById2() throws Exception {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        //Mock Controller Request
        mockMvc.perform(get("/users/findById2/1")).andExpect(status()
                        .isIAmATeapot())
                .andExpect(result -> userController.findById2(Mockito.anyString()));
        //----------------------------------------------------------------//
        //Mock Controller Return
        ResponseEntity<User> user2 = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(user);
        assertEquals(user2, userController.findById2(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find By ID Test")
    void FindById() throws Exception {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        mockMvc.perform(get("/users/findById/1"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findById(Mockito.anyString()));
        ResponseEntity<UserDTO> userDto = userController.findById(Mockito.anyString());
        assertEquals(userDto, userController.findById(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find By Name Test")
    void FindByName() throws Exception {
        mockMvc.perform(get("/users/findByName/teste"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findByName("teste"));
        usersDto = userController.findByName(Mockito.anyString());
        assertEquals(usersDto, userController.findByName(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find By Email Test")
    void FindByEmail() throws Exception {
        mockMvc.perform(get("/users/findByEmail/teste"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findByEmail(Mockito.anyString()));
        usersDto = userController.findByEmail(Mockito.anyString());
    }

    @Test
    @DisplayName("User Controller - Find All Test")
    void FindAll() throws Exception {
        mockMvc.perform(get("/users/findAll"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findAll());
        usersDto = userController.findAll();
    }

    @Test
    @DisplayName("User Controller - Get Posts Test")
    void getPosts() throws Exception {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        mockMvc.perform(get("/users/1/posts"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.getPosts(Mockito.anyString()));
        ResponseEntity<List<Post>> posts = new ResponseEntity<List<Post>>(HttpStatus.OK);
        assertEquals(ResponseEntity.ok().body(posts),userController.getPosts(Mockito.anyString()));
    }
}