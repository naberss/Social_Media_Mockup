package com.naberss.SocialMediaMockup.controllers;

import com.naberss.SocialMediaMockup.DTO.UserDTO;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserController_IntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void insert() {
        User user = new User("9999", "teste", "xxx@hotmail.com");
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> result = this.testRestTemplate.exchange("/users/Insert", HttpMethod.POST, request, User.class);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, result.getStatusCode()),
                () -> assertEquals("9999", result.getBody().getId()),
                () -> assertEquals("teste", result.getBody().getName()),
                () -> assertEquals("xxx@hotmail.com", result.getBody().getEmail())
        );
        this.testRestTemplate.delete("/users/delete/9999");
    }

    @Test
    void findById() {
        ResponseEntity<UserDTO> userDTO = testRestTemplate.exchange("/users/findById/2", HttpMethod.GET, null, UserDTO.class);
        System.out.println(userDTO);
        assertAll(
                () -> assertEquals("2", userDTO.getBody().getId()),
                () -> assertEquals("teste", userDTO.getBody().getName()),
                () -> assertEquals("xxx@hotmail.com", userDTO.getBody().getEmail()),
                () -> assertEquals(HttpStatus.OK, userDTO.getStatusCode()));
    }

    @Test
    void findById2() {
        ResponseEntity<User> user = testRestTemplate.exchange("/users/findById2/2", HttpMethod.GET, null, new ParameterizedTypeReference<User>() {});
        assertAll(
                () -> assertEquals(HttpStatus.I_AM_A_TEAPOT, user.getStatusCode()),
                () -> assertEquals("2", user.getBody().getId()),
                () -> assertEquals("teste", user.getBody().getName()),
                () -> assertEquals("xxx@hotmail.com", user.getBody().getEmail())
        );
    }


    @Test
    void findByName() {
        ResponseEntity<List<UserDTO>> users = testRestTemplate
                .exchange("/users/findByName/teste", HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {});

        assertAll(
                () -> assertEquals(1, users.getBody().size()),
                () -> assertEquals("2", users.getBody().get(0).getId()),
                () -> assertEquals("teste", users.getBody().get(0).getName()),
                () -> assertEquals("xxx@hotmail.com", users.getBody().get(0).getEmail()),
                () -> assertEquals(HttpStatus.OK, users.getStatusCode()));
    }

    @Test
    void findByEmail() {
        ResponseEntity<List<UserDTO>> users = testRestTemplate
                .exchange("/users/findByEmail/xxx@hotmail.com", HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {});

        assertAll(
                () -> assertEquals(1, users.getBody().size()),
                () -> assertEquals("2", users.getBody().get(0).getId()),
                () -> assertEquals("teste", users.getBody().get(0).getName()),
                () -> assertEquals("xxx@hotmail.com", users.getBody().get(0).getEmail()),
                () -> assertEquals(HttpStatus.OK, users.getStatusCode()));
    }


    @Test
    void findAll() {
        ResponseEntity<List<UserDTO>> users = testRestTemplate
                .exchange("/users/findAll", HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {});

        assertAll(
                () -> assertEquals(1, users.getBody().size()),
                () -> assertEquals("2", users.getBody().get(0).getId()),
                () -> assertEquals("teste", users.getBody().get(0).getName()),
                () -> assertEquals("xxx@hotmail.com", users.getBody().get(0).getEmail()),
                () -> assertEquals(HttpStatus.OK, users.getStatusCode()));
    }

    @Test
    void getPosts() {
        ResponseEntity<List<Post>> posts  = testRestTemplate
                .exchange("/users/2/posts",HttpMethod.GET,null,new ParameterizedTypeReference<List<Post>>() {});

        assertAll(
                () -> assertEquals("611b98b9fb9c6500b873b597",posts.getBody().get(0).getId()),
                () -> assertEquals("Some thing",posts.getBody().get(0).getTitle()),
                () -> assertEquals("godbless",posts.getBody().get(0).getBody()),
                () -> assertEquals("2",posts.getBody().get(0).getAuthor().getId()),
                () -> assertEquals("teste",posts.getBody().get(0).getAuthor().getName())
        );
    }

    @Test
    void update() {
        HttpEntity<User> request = new HttpEntity<>(new User("9999", "teste", "xxx@hotmail.com"));
        testRestTemplate.exchange("/users/Insert",HttpMethod.POST,request,User.class);
        request.getBody().setName("pereira");
        ResponseEntity<UserDTO> userDTO = testRestTemplate.exchange("/users/update/9999",HttpMethod.PUT,request,UserDTO.class);
        assertAll(
                () -> assertEquals(HttpStatus.ACCEPTED,userDTO.getStatusCode()),
                () -> assertEquals("9999",userDTO.getBody().getId()),
                () -> assertEquals("pereira",userDTO.getBody().getName()),
                () -> assertEquals("xxx@hotmail.com",userDTO.getBody().getEmail())
        );

        testRestTemplate.exchange("/users/delete/9999",HttpMethod.DELETE,null,User.class);

    }
}