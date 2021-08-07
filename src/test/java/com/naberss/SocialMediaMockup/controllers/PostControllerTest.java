package com.naberss.SocialMediaMockup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.services.PostService;
import com.naberss.SocialMediaMockup.services.UserService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("Controller_Test")
@ExtendWith(MockitoExtension.class)
@DisplayName("Post Controller - Tests")
class PostControllerTest {

    Post post;

    @Mock
    PostService postService;

    @Mock
    UserService userService;

    @InjectMocks
    PostController postController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        post = new Post();
    }

    @Test
    @DisplayName("Post Controller - Insert Test")
    void insert() throws Exception {
        User user = new User();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Instant instant = Instant.now();
        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/Insert")
                .param("name", "teste")
                .param("date", String.valueOf(instant))
                .param("title", "teste")
                .param("body", "teste")
                .param("authorID", "teste"))
                .andExpect(result -> postController.insert("teste", instant,"teste","teste", "teste"))
                .andExpect(status().isCreated());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        assertEquals(ResponseEntity.created(uri).body(post),postController.insert("teste", instant,"teste","teste", "teste"));
    }

    @Test
    @DisplayName("Post Controller - Find By ID Test")
    void findById() throws Exception {
        Mockito.when(postService.findById(Mockito.anyString())).thenReturn(post);
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/findById/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(result ->  postController.findById(Mockito.anyString()));

        assertEquals(ResponseEntity.ok().body(post),postController.findById(Mockito.anyString()));
    }

    @Test
    @DisplayName("Post Controller - Find All Test")
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/findById/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(result ->  postController.findAll());

        List<Post> posts = new ArrayList<>();
        assertEquals(ResponseEntity.ok().body(posts),postController.findAll());
    }

    @Test
    @DisplayName("Post Controller - Find By Author ID Test")
    void findbyAuthorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/findAuthorId/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(result ->  postController.findbyAuthorId(Mockito.anyString()));

        List<Post> posts = new ArrayList<>();
        assertEquals(ResponseEntity.ok().body(posts),postController.findbyAuthorId(Mockito.anyString()));
    }


    @Test
    @DisplayName("Post Controller - Find By Author Name Test")
    void findbyAuthorName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/findbyAuthorName/{name}","teste"))
                .andExpect(status().isOk())
                .andExpect(result ->  postController.findbyAuthorName(Mockito.anyString()));

        List<Post> posts = new ArrayList<>();
        assertEquals(ResponseEntity.ok().body(posts),postController.findbyAuthorName(Mockito.anyString()));
    }

    @Test
    @DisplayName("Post Controller - Find By Title Test")
    void findbyTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/findbyTitle/{title}","teste"))
                .andExpect(status().isOk())
                .andExpect(result ->  postController.findbyTitle(Mockito.anyString()));

        List<Post> posts = new ArrayList<>();
        assertEquals(ResponseEntity.ok().body(posts),postController.findbyTitle((Mockito.anyString())));
    }

    @Test
    @DisplayName("Post Controller - Find By Body Test")
    void findbyBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/findbyBody/{body}","teste"))
                .andExpect(status().isOk())
                .andExpect(result ->  postController.findbyBody(Mockito.anyString()));

        List<Post> posts = new ArrayList<>();
        assertEquals(ResponseEntity.ok().body(posts),postController.findbyBody((Mockito.anyString())));
    }

    @Test
    @DisplayName("Post Controller - Update Test")
    void update() throws Exception {
        Mockito.when(postService.Update(Mockito.anyString(),Mockito.any(Post.class))).thenReturn(post);
        String param = new ObjectMapper().writer().writeValueAsString(post);
        mockMvc.perform(MockMvcRequestBuilders.put("/posts/update/{id}","1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param))
                .andExpect(status().isAccepted())
                .andExpect(result ->  postController.update("1",post));

        assertEquals(ResponseEntity.accepted().body(post),postController.update("1",post));
    }

    @Test
    @DisplayName("Post Controller - Add Comment Test")
    void addComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/addComment/{postID}","1")
                .param("postID","teste"))
                .andExpect(status().isOk())
                .andExpect(result -> postController.addComment(Mockito.anyString(),Mockito.anyString()));

        Mockito.when(postService.addComment(Mockito.anyString(), Mockito.anyString())).thenReturn(post);
        assertEquals(ResponseEntity.ok().body(post),postController.addComment(Mockito.anyString(),Mockito.anyString()));
    }

    @Test
    @DisplayName("Post Controller - Delete Test")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/delete/{id}","1"))
                .andExpect(status().isNoContent())
                .andExpect(result ->  postController.delete(Mockito.anyString()));

        assertEquals(ResponseEntity.noContent().build(),postController.delete(Mockito.anyString()));
    }
}