package com.adda.quiz.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

	private String username;

	private String email;

	// private Set<String> roles;

	private String password;

}