package com.adda.quiz.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderBoard {

	private String username;
	
	private int score;
	
	public LeaderBoard() {
		
	}
	
	public LeaderBoard(String username, int score) {
		this.username=username;
		this.score=score;
	}
}
