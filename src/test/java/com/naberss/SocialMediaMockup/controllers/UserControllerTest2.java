package com.naberss.SocialMediaMockup.controllers;

import com.naberss.SocialMediaMockup.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest2 {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void insert() {
        User user = testRestTemplate.getForObject("/users/findById2/2",User.class);
        System.out.println(user);
        assertEquals("2",user.getId());
    }
}