package com.naberss.SocialMediaMockup.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.naberss.SocialMediaMockup.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query(value = "select a from User a where a.name like %:name%  ")
	public List<User> findbyName(@Param(value = "name") String name);

	@Query(value = "select a from User a where a.name like %:email%  ")
	public List<User> findbyEmail(@Param(value = "email") String email);

}
