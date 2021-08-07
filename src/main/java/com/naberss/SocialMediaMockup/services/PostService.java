package com.naberss.SocialMediaMockup.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.naberss.SocialMediaMockup.DTO.CommentDTO;
import com.naberss.SocialMediaMockup.Exceptions.MongoDatabaseViolation;
import com.naberss.SocialMediaMockup.Exceptions.MongoResourceNotFound;
import com.naberss.SocialMediaMockup.entities.Post;
import com.naberss.SocialMediaMockup.entities.User;
import com.naberss.SocialMediaMockup.repositories.PostRepository;
import com.naberss.SocialMediaMockup.repositories.UserRepository;

@Service
public class PostService {

	@Autowired
	public PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	public Post insert(Post post) {
		Post myPost = postRepository.insert(post);

		User user = userRepository.findById(post.getAuthor().getId()).orElse(null);
		user.getPosts().add(myPost);
		userRepository.save(user);

		return myPost;
	}

	public Post findById(String id) {
		return postRepository.findById(id).orElseThrow(() -> new MongoResourceNotFound(id));
	}

	public List<Post> findAll() {
		List<Post> posts = new ArrayList<>();
		posts = postRepository.findAll();

		if (posts.size() != 0) {
			return posts;
		} else {
			throw new MongoResourceNotFound();
		}

	}

	public List<Post> findbyAuthorId(String id) {
		List<Post> posts = new ArrayList<>();
		posts = postRepository.findbyAuthorId(id);

		if (posts.size() != 0) {
			return posts;
		} else {
			throw new MongoResourceNotFound();
		}

	}

	public List<Post> findbyAuthorName(String name) {
		List<Post> posts = new ArrayList<>();
		posts = postRepository.findbyAuthorName(name);

		if (posts.size() != 0) {
			return posts;
		} else {
			throw new MongoResourceNotFound();
		}

	}

	public List<Post> findbyBody(String body) {
		List<Post> posts = new ArrayList<>();
		posts = postRepository.findbyBody(body);

		if (posts.size() != 0) {
			return posts;
		} else {
			throw new MongoResourceNotFound();
		}

	}

	public List<Post> findbyTitle(String title) {
		List<Post> posts = new ArrayList<>();
		posts = postRepository.findbyTitle(title);

		if (posts.size() != 0) {
			return posts;
		} else {
			throw new MongoResourceNotFound();
		}

	}

	public Post Update(String id, Post newPost) {
		try {
			Post post = findById(id);
			updateData(post, newPost);
			postRepository.save(post);
			return post;
		} catch (NullPointerException e) {
			throw new MongoResourceNotFound(id);
		}
	}

	public void updateData(Post oldPost, Post newPost) {
		oldPost.setBody(newPost.getBody());
		oldPost.setTitle(newPost.getTitle());
		oldPost.setDate(newPost.getDate());
	}

	public Post addComment(String postID,String text) {
		Post post = findById(postID);
		post.getComments().add(new CommentDTO(text, Instant.now(), post.getAuthor()));
		postRepository.save(post);
		return post;
	}

	public void delete(String id) {
		try {
			postRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new MongoDatabaseViolation();
		} catch (EmptyResultDataAccessException f) {
			throw new MongoDatabaseViolation();
		}

	}

}
