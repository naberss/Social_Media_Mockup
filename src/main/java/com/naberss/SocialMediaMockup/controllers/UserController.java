package com.naberss.SocialMediaMockup.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, path = "/Insert")
	public @ResponseBody ResponseEntity<User> insert(@RequestBody User user) {

		userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findById/{id}")
	public ResponseEntity<User> findById(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok().body(userService.findById(id));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findByName/{name}")
	public ResponseEntity<List<User>> findByName(@PathVariable(name = "name") String name) {
		return ResponseEntity.ok().body(userService.findByName(name));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findByEmail/{email}")
	public ResponseEntity<List<User>> findByEmail(@PathVariable(name = "email") String email) {
		return ResponseEntity.ok().body(userService.findByName(email));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findAll")
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok().body(userService.findAll());
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
	public ResponseEntity<User> update(@PathVariable(name = "id") String id, @RequestBody User user) {
		return ResponseEntity.accepted().body(userService.Update(id, user));
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
	public ResponseEntity<User> update(@PathVariable(name = "id") String id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
