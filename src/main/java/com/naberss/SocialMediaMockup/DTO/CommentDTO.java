package com.naberss.SocialMediaMockup.DTO;

import java.io.Serializable;
import java.time.Instant;

public class CommentDTO implements Serializable {

	private static final long serialVersionUID = -3259794600300138053L;

	private String text;
	private Instant instant;
	private AuthorDTO authorDTO;

	public CommentDTO() {
	}

	public CommentDTO(String text, Instant instant, AuthorDTO authorDTO) {
		super();
		this.text = text;
		this.instant = instant;
		this.authorDTO = authorDTO;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public AuthorDTO getAuthorDTO() {
		return authorDTO;
	}

	public void setAuthorDTO(AuthorDTO authorDTO) {
		this.authorDTO = authorDTO;
	}

}
