package com.naberss.SocialMediaMockup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naberss.SocialMediaMockup.DTO.UserDTO;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

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

    List<UserDTO> usersDto;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user = new User();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("User Controller - Insert Test")
    void Insert() throws Exception {
        //Mock Controller Request
        String requestJson = new ObjectMapper().writer().writeValueAsString(user);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        mockMvc.perform(post("/users/Insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(result -> userController.insert(user));
        //URI uri = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}").buildAndExpand(user.getId()).toUri();
    }

    @Test
    @DisplayName("User Controller - Find By ID (2) Test")
    void findById2() throws Exception {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        //Mock Controller Request
        mockMvc.perform(get("/users/findById2/1"))
                        .andExpect(status()
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
        UserDTO userDTO = new  UserDTO(userService.findById(Mockito.anyString()));
        assertEquals(ResponseEntity.ok().body(userDTO),userController.findById(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find By Name Test")
    void FindByName() throws Exception {
        mockMvc.perform(get("/users/findByName/teste"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findByName("teste"));
        usersDto = userService.findByName(Mockito.anyString()).stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        assertEquals(ResponseEntity.ok().body(usersDto),userController.findByName(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find By Email Test")
    void FindByEmail() throws Exception {
        mockMvc.perform(get("/users/findByEmail/teste"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findByEmail(Mockito.anyString()));
           usersDto = userService.findbyEmail(Mockito.anyString()).stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
           assertEquals(ResponseEntity.ok().body(usersDto),userController.findByEmail(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find All Test")
    void FindAll() throws Exception {
        mockMvc.perform(get("/users/findAll"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findAll());
        usersDto = userService.findAll().stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        assertEquals(ResponseEntity.ok().body(usersDto), userController.findAll());
    }


    @Test
    @DisplayName("User Controller - Get Posts Test")
    void getPosts() throws Exception {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        mockMvc.perform(get("/users/1/posts"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.getPosts(Mockito.anyString()));
        List<Post> posts = userService.findById(Mockito.anyString()).getPosts();
        assertEquals(ResponseEntity.ok().body(posts), userController.getPosts(Mockito.anyString()));
    }
}