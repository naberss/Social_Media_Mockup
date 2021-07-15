package com.naberss.SocialMediaMockup.Exceptions.Controllers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.naberss.SocialMediaMockup.Exceptions.ExceptionBody;
import com.naberss.SocialMediaMockup.Exceptions.MongoDatabaseViolation;
import com.naberss.SocialMediaMockup.Exceptions.MongoResourceNotFound;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = MongoResourceNotFound.class)
	public static ResponseEntity<ExceptionBody> MongoResourceNotFound(MongoResourceNotFound e,
			HttpServletRequest request) {

		String error = "Resource Not Found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		ExceptionBody exceptionBody = new ExceptionBody(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(exceptionBody);
	}
	
	@ExceptionHandler(value = MongoDatabaseViolation.class)
	public static ResponseEntity<ExceptionBody> MongoDatabaseViolation(MongoDatabaseViolation e,
			HttpServletRequest request) {

		String error = "Resource Not Found";
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ExceptionBody exceptionBody = new ExceptionBody(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(exceptionBody);
	}

}
