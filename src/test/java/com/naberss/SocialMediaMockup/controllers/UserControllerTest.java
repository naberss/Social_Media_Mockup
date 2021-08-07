package com.naberss.SocialMediaMockup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naberss.SocialMediaMockup.DTO.UserDTO;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Controller_Test")
@ExtendWith(MockitoExtension.class)
@DisplayName("User Controller - Tests")
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
        // Mock Servlet Context
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        //Mock Controller Request
        String param = new ObjectMapper().writer().writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/Insert").contentType(MediaType.APPLICATION_JSON).content(param))
                .andExpect(Result -> userController.insert(user));
        URI uri = ServletUriComponentsBuilder.fromRequestUri(request).path("/{id}").buildAndExpand(user.getId()).toUri();
        assertEquals(ResponseEntity.created(uri).body(user), userController.insert(user));
    }

    @Test
    @DisplayName("User Controller - Find By ID (2) Test")
    void findById2() throws Exception {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        //Mock Controller Request
        mockMvc.perform(MockMvcRequestBuilders.get("/users/findById2/{id}","1"))
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
        mockMvc.perform(MockMvcRequestBuilders.get("/users/findById/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findById(Mockito.anyString()));
        UserDTO userDTO = new UserDTO(userService.findById(Mockito.anyString()));
        assertEquals(ResponseEntity.ok().body(userDTO), userController.findById(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find By Name Test")
    void FindByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/findByName/{name}","teste"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findByName("teste"));
        usersDto = userService.findByName(Mockito.anyString()).stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        assertEquals(ResponseEntity.ok().body(usersDto), userController.findByName(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find By Email Test")
    void FindByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/findByEmail/{email}","teste"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findByEmail(Mockito.anyString()));
        usersDto = userService.findbyEmail(Mockito.anyString()).stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        assertEquals(ResponseEntity.ok().body(usersDto), userController.findByEmail(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Find All Test")
    void FindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/findAll"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.findAll());
        usersDto = userService.findAll().stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        assertEquals(ResponseEntity.ok().body(usersDto), userController.findAll());
    }

    @Test
    @DisplayName("User Controller - Get Posts Test")
    void getPosts() throws Exception {
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/posts","1"))
                .andExpect(status().isOk())
                .andExpect(result -> userController.getPosts(Mockito.anyString()));
        List<Post> posts = userService.findById(Mockito.anyString()).getPosts();
        assertEquals(ResponseEntity.ok().body(posts), userController.getPosts(Mockito.anyString()));
    }

    @Test
    @DisplayName("User Controller - Update Test")
    void update() throws Exception {
        Mockito.when(userService.Update("1",user)).thenReturn(user);
        String param = new ObjectMapper().writer().writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update/{id}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isAccepted())
                .andExpect(Result -> userController.update("1", user));
        UserDTO userDTO = new UserDTO(user);
        assertEquals(ResponseEntity.accepted().body(userDTO),userController.update("1", user));
    }

    @Test
    @DisplayName("User Controller - Delete Test")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/{id}","1"))
                .andExpect(status().isNoContent())
                .andExpect(Result -> userController.delete(Mockito.anyString()));
    }
}