package com.adda.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adda.quiz.exceptions.TopicNotFoundException;
import com.adda.quiz.model.Question;
import com.adda.quiz.model.Topic;
import com.adda.quiz.repo.TopicRepository;

@Service
public class TopicService {

	@Autowired
	TopicRepository repo;

	public List<Topic> getAllTopics() {
		List<Topic> topics = repo.findAll();
		return topics;
	}

	public Topic getTopicByName(String topicname) throws TopicNotFoundException {
		Optional<Topic> topic = repo.findByTopicname(topicname);
		if (topic.isPresent()) {
			return topic.get();
		} else {
			throw new TopicNotFoundException("No record Found for the topic : " + topic);
		}
	}

	public void saveTopic(Topic topic) {
		repo.save(topic);
	}

	public void addMoreTopicQuestion(String topicname, Topic topic) {
		Optional<Topic> topicRecord = repo.findByTopicname(topicname);
		if (topicRecord.isPresent()) {
			topic.setId(topicRecord.get().getId());
			topic.setTopicname(topicRecord.get().getTopicname());
			List<Question> questions = topicRecord.get().getQuestions();
			questions.addAll(topic.getQuestions());
			topic.setQuestions(questions);
			repo.save(topic);
		} else {
			repo.save(topic);
		}
	}

	public void deleteTopicQuestion(String topicname, Topic topic) throws TopicNotFoundException {
		Optional<Topic> topicRecord = repo.findByTopicname(topicname);
		if (topicRecord.isPresent()) {
			topic.setId(topicRecord.get().getId());
			topic.setTopicname(topicRecord.get().getTopicname());
			List<Question> questions = topicRecord.get().getQuestions();
			for (Question question : questions) {
				if (question.getQuestion().equals(topic.getQuestions().get(0).getQuestion())) {
					questions.remove(question);
					break;
				}
			}
			topic.setQuestions(questions);
			if (topic.getQuestions() != null && !topic.getQuestions().isEmpty()) {
				repo.save(topic);
			} else {
				repo.delete(topic);
			}
		} else {
			throw new TopicNotFoundException("No record Found for the topic : " + topic.getTopicname());
		}
	}

	public void deleteTopicByName(String topicname) throws TopicNotFoundException {
		Optional<Topic> topic = repo.findByTopicname(topicname);
		if (topic.isPresent()) {
			repo.delete(topic.get());
		} else {
			throw new TopicNotFoundException("No record Found for the user : " + topicname);
		}
	}

}
