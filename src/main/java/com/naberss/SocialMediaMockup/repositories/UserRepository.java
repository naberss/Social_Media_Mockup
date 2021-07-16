package com.naberss.SocialMediaMockup.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.naberss.SocialMediaMockup.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
	public List<User> findbyName(String name);

	@Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
	public List<User> findbyEmail(String email);

}
