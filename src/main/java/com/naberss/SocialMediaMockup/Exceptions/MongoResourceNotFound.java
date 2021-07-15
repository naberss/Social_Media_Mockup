package com.naberss.SocialMediaMockup.Exceptions;

public class MongoResourceNotFound extends RuntimeException {

	private static final long serialVersionUID = 1109297284638846266L;

	public MongoResourceNotFound(Object id) {
		super("Mongo Resource Not Found -> ID: " + id);
	}

	public MongoResourceNotFound() {
	}

}
