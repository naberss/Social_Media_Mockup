package com.naberss.SocialMediaMockup.DTO;

import java.io.Serializable;
import java.util.Objects;

import com.naberss.SocialMediaMockup.entities.User;

public class AuthorDTO implements Serializable {

	private static final long serialVersionUID = -7344957375324017076L;
	private String id;
	private String name;

	public AuthorDTO() {
	}

	public AuthorDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	} 

}
