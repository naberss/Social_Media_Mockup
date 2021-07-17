package com.naberss.SocialMediaMockup.controllers;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.naberss.SocialMediaMockup.DTO.AuthorDTO;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.services.PostService;
import com.naberss.SocialMediaMockup.services.UserService;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, path = "/Insert")
	public @ResponseBody ResponseEntity<Post> insert(@Param(value = "name") String name,
			                                         @Param(value = "date") Instant date,  
			                                         @Param(value = "title") String title,
			                                         @Param(value = "body") String body, 
			                                         @Param(value = "authorID") String authorID) {

		User user = userService.findById(authorID);

		Post post = new Post(null, date, title, body, new AuthorDTO(user));
		postService.insert(post);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).body(post);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findById/{id}")
	public ResponseEntity<Post> findById(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok().body(postService.findById(id));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findAll")
	public ResponseEntity<List<Post>> findAll() {

		List<Post> posts = postService.findAll();
		return ResponseEntity.ok().body(posts);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findAuthorId/{id}")
	public ResponseEntity<List<Post>> findbyAuthorId(@PathVariable(name = "id") String id) {

		List<Post> posts = postService.findbyAuthorId(id);
		return ResponseEntity.ok().body(posts);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/findbyAuthorName/{name}")
	public ResponseEntity<List<Post>> findbyAuthorName(@PathVariable(name = "name") String name) {

		List<Post> posts = postService.findbyAuthorName(name);
		return ResponseEntity.ok().body(posts);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/findbyTitle/{title}")
	public ResponseEntity<List<Post>> findbyTitle(@PathVariable(name = "title") String title) {

		List<Post> posts = postService.findbyTitle(title);
		return ResponseEntity.ok().body(posts);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/findbyBody/{body}")
	public ResponseEntity<List<Post>> findbyBody(@PathVariable(name = "body") String body) {

		List<Post> posts = postService.findbyBody(body);
		return ResponseEntity.ok().body(posts);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
	public ResponseEntity<Post> update(@PathVariable(name = "id") String id, @RequestBody Post post) {
		return ResponseEntity.accepted().body(postService.Update(id, post));
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
	public ResponseEntity<Post> update(@PathVariable(name = "id") String id) {
		postService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
