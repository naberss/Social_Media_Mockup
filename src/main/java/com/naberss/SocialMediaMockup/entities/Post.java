package com.naberss.SocialMediaMockup.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.naberss.SocialMediaMockup.DTO.AuthorDTO;
import com.naberss.SocialMediaMockup.DTO.CommentDTO;

@Document
public class Post implements Serializable {

	@Transient
	private static final long serialVersionUID = 7942424004115260724L;

	@Id
	private String id;
	private Instant date;
	private String title;
	private String body;
	private AuthorDTO author = new AuthorDTO();
	private List<CommentDTO> comments = new ArrayList<>();

	public Post() {
	}

	public Post(String id, Instant date, String title, String body, AuthorDTO author) {
		super();
		this.id = id;
		this.date = date;
		this.title = title;
		this.body = body;
		this.author = author;
	}

	public Post(String id, Instant date, String title, String body) {
		super();
		this.id = id;
		this.date = date;
		this.title = title;
		this.body = body;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return Objects.equals(getId(), post.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
