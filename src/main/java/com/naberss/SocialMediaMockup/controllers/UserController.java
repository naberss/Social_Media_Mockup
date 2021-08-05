package com.naberss.SocialMediaMockup.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.naberss.SocialMediaMockup.DTO.UserDTO;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, path = "/Insert")
    public @ResponseBody
    ResponseEntity<User> insert(@RequestBody User user) {

        userService.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findById/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") String id) {
        UserDTO userDto = new UserDTO(userService.findById(id));
        return ResponseEntity.ok().body(userDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findById2/{id}")
    public ResponseEntity<User> findById2(@PathVariable(name = "id") String id) {
        User userDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(userDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByName/{name}")
    public ResponseEntity<List<UserDTO>> findByName(@PathVariable(name = "name") String name) {

        List<User> users = userService.findByName(name);
        List<UserDTO> usersDto = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(usersDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByEmail/{email}")
    public ResponseEntity<List<UserDTO>> findByEmail(@PathVariable(name = "email") String email) {

        List<User> users = userService.findByName(email);
        List<UserDTO> usersDto = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(usersDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findAll")
    public ResponseEntity<List<UserDTO>> findAll() {

        List<User> users = userService.findAll();
        List<UserDTO> usersDto = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(usersDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}/posts")
    public ResponseEntity<List<Post>> getPosts(@PathVariable(name = "id") String id) {

        User user = userService.findById(id);
        List<Post> posts = user.getPosts();
        return ResponseEntity.ok().body(posts);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable(name = "id") String id, @RequestBody User user) {
        UserDTO userDto = new UserDTO(userService.Update(id, user));
        return ResponseEntity.accepted().body(userDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    public ResponseEntity<User> update(@PathVariable(name = "id") String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
