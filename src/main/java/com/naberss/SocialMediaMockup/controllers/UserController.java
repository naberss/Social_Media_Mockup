package com.naberss.SocialMediaMockup.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.naberss.SocialMediaMockup.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@RequestMapping(method = RequestMethod.GET, path = "/findall")
	public ResponseEntity<List<User>> findAll() {
		List<User> users = new ArrayList<User>();
		users.add(new User("1", "teste", "email@teste.com"));
		return ResponseEntity.ok().body(users);
	}

}
