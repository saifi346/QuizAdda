package com.adda.quiz.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {

	private String question;
	
	private List<String> options;
	
	private String answer;
	
	public Question() {
		
	}
	
	public Question(String question, List<String> options, String answer) {
		this.question=question;
		this.options=options;
		this.answer=answer;
	}
}
