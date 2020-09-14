package com.adda.quiz.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="topic")
public class Topic {

	@Id
	private String id;
	
	private String topicname;
	
	private List<Question> questions;
	
	public Topic() {}
	
	public Topic(String topicname, List<Question> questions) {
		this.topicname=topicname;
		this.questions=questions;
	}
}
