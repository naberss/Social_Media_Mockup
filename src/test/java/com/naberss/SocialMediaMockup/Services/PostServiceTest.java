package com.naberss.SocialMediaMockup.Services;

import com.naberss.SocialMediaMockup.Exceptions.MongoResourceNotFound;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.repositories.PostRepository;
import com.naberss.SocialMediaMockup.repositories.UserRepository;
import com.naberss.SocialMediaMockup.services.PostService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Tag("Service_Test")
@DisplayName("Post Service - Tests")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
    }

    @Test
    @DisplayName("Post Service - Insert Test")
    void insertTest() {
        Mockito.when(postRepository.insert(any(Post.class))).thenReturn(post);
        assertNotNull(postRepository.insert(post));
        Mockito.verify(postRepository, Mockito.times(1)).insert(any(Post.class));
    }

    @Test
    @DisplayName("Post Service - Find By ID Test")
    void findByIdTest() {
        Mockito.when(postRepository.findById(anyString())).thenReturn(Optional.of(post));
        assertEquals(Optional.of(post), postRepository.findById(anyString()));
        Mockito.verify(postRepository, Mockito.times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Post Service - Find All Test")
    void findAll() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postRepository.findAll()).thenReturn(posts);
        assertEquals(posts, postRepository.findAll());
        Mockito.verify(postRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Post Service - Find By Author ID Test")
    void findbyAuthorId() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postRepository.findbyAuthorId(anyString())).thenReturn(posts);
        assertEquals(posts, postRepository.findbyAuthorId(anyString()));
        Mockito.verify(postRepository, Mockito.times(1)).findbyAuthorId(anyString());
    }

    @Test
    @DisplayName("Post Service - Find By Author Name Test")
    void findbyAuthorName() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postRepository.findbyAuthorName(anyString())).thenReturn(posts);
        assertEquals(posts, postRepository.findbyAuthorName(anyString()));
        Mockito.verify(postRepository, Mockito.times(1)).findbyAuthorName(anyString());
    }

    @Test
    @DisplayName("Post Service - Find By Author Body Test")
    void findbyBody() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postRepository.findbyBody(anyString())).thenReturn(posts);
        assertEquals(posts, postRepository.findbyBody(anyString()));
        Mockito.verify(postRepository, Mockito.times(1)).findbyBody(anyString());
    }

    @Test
    @DisplayName("Post Service - Find By Title Test")
    void findbyTitle() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postRepository.findbyTitle(anyString())).thenReturn(posts);
        assertEquals(posts, postRepository.findbyTitle(anyString()));
        Mockito.verify(postRepository, Mockito.times(1)).findbyTitle(anyString());
    }

    @Test
    @DisplayName("Post Service - Update Test")
    void update() {
        Mockito.when(postRepository.save(post)).thenReturn(post);
        assertEquals(post, postRepository.save(post));
        Mockito.verify(postRepository, Mockito.times(1)).save(post);
    }

    @Test
    @DisplayName("Post Service - Delete Test")
    void delete() {
        postRepository.deleteById(anyString());
        Mockito.verify(postRepository, Mockito.times(1)).deleteById(anyString());
    }
}