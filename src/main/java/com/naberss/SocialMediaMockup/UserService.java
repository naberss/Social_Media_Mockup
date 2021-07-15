package com.naberss.SocialMediaMockup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.naberss.SocialMediaMockup.Exceptions.MongoDatabaseViolation;
import com.naberss.SocialMediaMockup.Exceptions.MongoResourceNotFound;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;

	public User insert(User user) {
		return userRepository.insert(user);
	}

	public User findById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new MongoResourceNotFound(id));
	}

	public List<User> findByName(String name) {
		List<User> users = new ArrayList<>();
		users = userRepository.findbyName(name);

		if (users.size() != 0) {
			return users;
		} else {
			throw new MongoResourceNotFound(name);
		}

	}

	public List<User> findbyEmail(String email) {
		List<User> users = new ArrayList<>();
		users = userRepository.findbyEmail(email);

		if (users.size() != 0) {
			return users;
		} else {
			throw new MongoResourceNotFound(email);
		}

	}

	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		users = userRepository.findAll();

		if (users.size() != 0) {
			return users;
		} else {
			throw new MongoResourceNotFound();
		}

	}

	public User Update(String id, User newUser) {
		try {
			User user = findById(id);
			updateData(user, newUser);
			userRepository.save(user);
			return user;
		} catch (NullPointerException e) {
			throw new MongoResourceNotFound(id);
		}
	}

	public void updateData(User oldUser, User newUser) {
		oldUser.setName(newUser.getName());
		oldUser.setEmail(newUser.getEmail());
	}

	public void delete(String id) {
		try {
			userRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new MongoDatabaseViolation();
		} catch (EmptyResultDataAccessException f) {
			throw new MongoDatabaseViolation();
		}

	}

}
