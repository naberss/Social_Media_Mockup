package com.naberss.SocialMediaMockup.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.naberss.SocialMediaMockup.entities.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	@Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
	public List<Post> findbyTitle(String title);
	
	@Query(value = "{'body': {$regex : ?0, $options: 'i'}}")
	public List<Post> findbyBody(String body);
	
	@Query(value = "{'author.id': {$regex : ?0, $options: 'i'}}")
	public List<Post> findbyAuthorId(String id);
	
	@Query(value = "{'author.name': {$regex : ?0, $options: 'i'}}")
	public List<Post> findbyAuthorName(String name);
	
}
