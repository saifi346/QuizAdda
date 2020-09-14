package com.adda.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adda.quiz.exceptions.UserNotFoundException;
import com.adda.quiz.model.TopicAttempted;
import com.adda.quiz.model.User;
import com.adda.quiz.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;

	public List<User> getAllUsers() {
		List<User> users = repo.findAll();
		return users;
	}

	public User getUserByName(String username) throws UserNotFoundException {
		Optional<User> user = repo.findByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserNotFoundException("No record Found for the user : " + username);
		}
	}

	public void updateUser(String username, User user) throws UserNotFoundException {
		Optional<User> userRecord = repo.findByUsername(username);
		List<TopicAttempted> topics = new ArrayList<>();
		int totalScore = 0;
		boolean flag = true;
		if (userRecord.isPresent()) {
			user.setId(userRecord.get().getId());
			user.setUsername(userRecord.get().getUsername());
			user.setPassword(userRecord.get().getPassword());
			user.setRoles(userRecord.get().getRoles());
			user.setEmail(userRecord.get().getEmail());
			//updating topics and score
			if (userRecord.get().getTopics() != null) {
				topics = userRecord.get().getTopics();
				for (TopicAttempted topic : topics) {
					if (user.getTopics().get(0).getTopicname().equals(topic.getTopicname())) {
						topic.setScore(user.getTopics().get(0).getScore());
						flag = false;
						break;
					}
				}
			}
			if (flag) {
				topics.add(user.getTopics().get(0));
			}
			user.setTopics(topics);
			//updating totalscore
			for (TopicAttempted topic : topics) {
				totalScore += topic.getScore();
			}
            user.setTotalScore(totalScore);
			repo.save(user);
		} else {
			throw new UserNotFoundException("No record Found for the user : " + user.getUsername());
		}
	}

	public void deleteUserByName(String username) throws UserNotFoundException {
		Optional<User> user = repo.findByUsername(username);
		if (user.isPresent()) {
			repo.delete(user.get());
		} else {
			throw new UserNotFoundException("No record Found for the user : " + username);
		}
	}
}