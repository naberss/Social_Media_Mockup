package com.naberss.SocialMediaMockup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naberss.SocialMediaMockup.DTO.UserDTO;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("Controller_Test")
@WebMvcTest(UserController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@DisplayName("User Controller - Tests")
class UserController_Test {

    @MockBean
    UserController userController;

    User user;

    List<UserDTO> usersDto;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user = new User("1", "teste", "teste@hotmail.com");
    }

    /*@Test
    @DisplayName("User Controller - JsonPath")
    void jsonPathTest() throws Exception {
        User myTestUser = new User("1", "lucas", "lucas-berto@hotmail.com");
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("1", Instant.now(), "title 1", "body 1"));
        posts.add(new Post("2", Instant.now(), "title 2", "body 2"));
        myTestUser.setPosts(posts);

        Mockito.when(userService.findById(Mockito.anyString())).thenReturn(myTestUser);

        //Dont have to uso mockito before perform
        //wont be able to call controllers by name now
        mockMvc.perform(MockMvcRequestBuilders.get("/users/findById2/{id}", "1"))
                .andExpect(status().isIAmATeapot())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(myTestUser.getName())))
                .andExpect(jsonPath("$.id", is(myTestUser.getId())))
                .andExpect(jsonPath("$.email", is(myTestUser.getEmail())))
                //Managing List Values
                .andExpect(jsonPath("$.posts", hasSize(2)))
                .andExpect(jsonPath("$.posts[0].title", is("title 1")))
                .andExpect(jsonPath("$.posts[1].title", is("title 2")));
    }*/

    @Test
    @DisplayName("User Controller - Insert Test")
    void Insert() throws Exception {
        // Mock Servlet Context
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI uri = ServletUriComponentsBuilder.fromRequestUri(request).path("/{name}").buildAndExpand(user.getId()).toUri();
        Mockito.when(userController.insert(user)).thenReturn(ResponseEntity.created(uri).body(user));
        //Mock Controller Request
        String param = new ObjectMapper().writer().writeValueAsString(user);

        ConstrainedFields fields = new ConstrainedFields(User.class);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/users/Insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("teste")))
                .andExpect(jsonPath("$.email", is("teste@hotmail.com")))
                .andDo(document("/Insert-new", requestFields(
                        fields.withPath("id").description("User ID")
                        , fields.withPath("name").description("User Name")
                        , fields.withPath("email").description("User Email")
                        , fields.withPath("posts").ignored())));
    }

    @Test
    @DisplayName("User Controller - Find By ID (2) Test")
    void findById2() throws Exception {
        //Mock Controller Request
        Mockito.when(userController.findById2("1")).thenReturn(ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(user));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/findById2/{id}", "1"))
                .andExpect(status().isIAmATeapot())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("teste")))
                .andExpect(jsonPath("$.email", is("teste@hotmail.com")))
                .andDo(document("/findById2", pathParameters(parameterWithName("id").description("User ID"))
                        , responseFields(fieldWithPath("id").description("User ID")
                                , fieldWithPath("email").description("User Email")
                                , fieldWithPath("name").description("User Name")
                                , fieldWithPath("posts").description("User Posts"))));
    }

    @Test
    @DisplayName("User Controller - Find By ID Test")
    void FindById() throws Exception {
        Mockito.when(userController.findById("1")).thenReturn(ResponseEntity.status(HttpStatus.OK).body(new UserDTO(user)));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/findById/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("teste")))
                .andExpect(jsonPath("$.email", is("teste@hotmail.com")))
                .andDo(document("/findById", pathParameters(parameterWithName("id").description("User ID"))));
    }

    @Test
    @DisplayName("User Controller - Find By Name Test")
    void FindByName() throws Exception {
        List<UserDTO> users = new ArrayList<>(List.of(new UserDTO(user)));
        Mockito.when(userController.findByName("teste")).thenReturn(ResponseEntity.ok().body(users));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/findByName/{name}", "teste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is("1")))
                .andExpect(jsonPath("$.[0].name", is("teste")))
                .andExpect(jsonPath("$.[0].email", is("teste@hotmail.com")));
    }

    @Test
    @DisplayName("User Controller - Find By Email Test")
    void FindByEmail() throws Exception {
        List<UserDTO> users = new ArrayList<>(List.of(new UserDTO(user)));
        Mockito.when(userController.findByEmail("teste@hotmail.com")).thenReturn(ResponseEntity.ok().body(users));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/findByEmail/{email}", "teste@hotmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is("1")))
                .andExpect(jsonPath("$.[0].name", is("teste")))
                .andExpect(jsonPath("$.[0].email", is("teste@hotmail.com")));
    }

    @Test
    @DisplayName("User Controller - Find All Test")
    void FindAll() throws Exception {
        List<UserDTO> users = new ArrayList<>(List.of(new UserDTO(user)));
        Mockito.when(userController.findAll()).thenReturn(ResponseEntity.ok().body(users));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is("1")))
                .andExpect(jsonPath("$.[0].name", is("teste")))
                .andExpect(jsonPath("$.[0].email", is("teste@hotmail.com")));
    }

    @Test
    @DisplayName("User Controller - Get Posts Test")
    void getPosts() throws Exception {
        List<Post> posts = new ArrayList<>(List.of(new Post("1", null, "test title", "test body")));
        Mockito.when(userController.getPosts("1")).thenReturn(ResponseEntity.ok().body(posts));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/{id}/posts", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is("1")))
                .andExpect(jsonPath("$.[0].title", is("test title")))
                .andExpect(jsonPath("$.[0].body", is("test body")));
    }

    @Test
    @DisplayName("User Controller - Update Test")
    void update() throws Exception {
        Mockito.when(userController.update("1", user)).thenReturn(ResponseEntity.accepted().body(new UserDTO(user)));
        String param = new ObjectMapper().writer().writeValueAsString(user);
        mockMvc.perform(RestDocumentationRequestBuilders.put("/users/update/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("teste")))
                .andExpect(jsonPath("$.email", is("teste@hotmail.com")));
    }

    @Test
    @DisplayName("User Controller - Delete Test")
    void delete() throws Exception {
        Mockito.when(userController.delete("1")).thenReturn(ResponseEntity.noContent().build());
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/users/delete/{id}", "1"))
                .andExpect(status().isNoContent());
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }


}