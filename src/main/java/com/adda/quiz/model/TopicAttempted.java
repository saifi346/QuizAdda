package com.adda.quiz.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicAttempted {
	
	private String topicname;
	
	private int score;
	
	public TopicAttempted() {
		
	}

	public TopicAttempted(String topicname, int score) {
		this.topicname = topicname;
		this.score = score;
	}
	
	

}
