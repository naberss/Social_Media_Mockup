package com.naberss.SocialMediaMockup.Exceptions;

public class MongoDatabaseViolation extends RuntimeException {

	private static final long serialVersionUID = 5644640663199370004L;

	public MongoDatabaseViolation(Object id) {
		super("Mongo Internal Database Error");
	}

	public MongoDatabaseViolation() {
	}

}
