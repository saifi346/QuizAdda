package com.adda.quiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adda.quiz.model.LeaderBoard;
import com.adda.quiz.model.TopicAttempted;
import com.adda.quiz.model.User;

@Service
public class LeaderBoardService {

	@Autowired
	UserService userservice;

	public List<LeaderBoard> getLeaderBoard() {
		List<User> users = userservice.getAllUsers();
		List<LeaderBoard> leaderBoard = new ArrayList<>();
		for (User user : users) {
			LeaderBoard leader = new LeaderBoard();
			leader.setUsername(user.getUsername());
			leader.setScore(user.getTotalScore());
			leaderBoard.add(leader);
		}
		leaderBoard.sort((LeaderBoard l1, LeaderBoard l2) -> l2.getScore() - l1.getScore());
		return leaderBoard;
	}

	public List<LeaderBoard> getLeaderBoardByTopic(String topicname) {
		List<User> users = userservice.getAllUsers();
		List<TopicAttempted> topics = new ArrayList<>();
		List<LeaderBoard> leaderBoard = new ArrayList<>();
		for (User user : users) {
			LeaderBoard leader = new LeaderBoard();
			if (user.getTopics() != null) {
				topics = user.getTopics();
				for (TopicAttempted topic : topics) {
					if (topic.getTopicname().equals(topicname)) {
						leader.setUsername(user.getUsername());
						leader.setScore(topic.getScore());
						leaderBoard.add(leader);
						break;
					}
				}
			}
		}
		leaderBoard.sort((LeaderBoard l1, LeaderBoard l2) -> l2.getScore() - l1.getScore());
		return leaderBoard;
	}

}
