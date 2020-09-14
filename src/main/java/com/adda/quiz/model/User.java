package com.adda.quiz.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="user")
public class User {

	@Id
	private String id;

	private String username;

	private String email;

	private String password;
	
	private List<TopicAttempted> topics;
	
	private int totalScore;

	@DBRef
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String username, String email, String password,List<TopicAttempted> topics,int totalScore ) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.topics = topics;
		this.totalScore = totalScore;
	}

}
