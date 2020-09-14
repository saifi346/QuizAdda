package com.adda.quiz.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adda.quiz.model.Topic;

public interface TopicRepository extends MongoRepository<Topic, String>{

	Optional<Topic> findByTopicname(String topicname);
	
	Boolean existsByTopicname(String topicname);
}
