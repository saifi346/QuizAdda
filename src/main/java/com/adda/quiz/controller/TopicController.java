package com.adda.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adda.quiz.exceptions.TopicNotFoundException;
import com.adda.quiz.model.Topic;
import com.adda.quiz.response.MessageResponse;
import com.adda.quiz.service.TopicService;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	TopicService service;
	
	@GetMapping("/alltopics")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Topic>> getTopics() {
		List<Topic> topics = service.getAllTopics();

		return new ResponseEntity<>(topics, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{topicname}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Topic> getTopicByName(@PathVariable("topicname") String topicname) throws TopicNotFoundException {
		Topic topic = service.getTopicByName(topicname);

		return new ResponseEntity<>(topic, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/savetopic")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveTopic(@RequestBody Topic topic){
		service.saveTopic(topic);

		return ResponseEntity.ok(new MessageResponse("Topic saved successfully!"));
	}

	@PutMapping("/addquestion/{topicname}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTopicQuestion(@PathVariable("topicname") String topicname, @RequestBody Topic topic)
			throws TopicNotFoundException {
		service.addMoreTopicQuestion(topicname, topic);

		return ResponseEntity.ok(new MessageResponse("Topic updated successfully!"));
	}
	
	@PutMapping("/deletequestion/{topicname}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTopicQuestion(@PathVariable("topicname") String topicname, @RequestBody Topic topic)
			throws TopicNotFoundException {
		service.deleteTopicQuestion(topicname, topic);

		return ResponseEntity.ok(new MessageResponse("Topic updated successfully!"));
	}

	@DeleteMapping("/{topicname}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTopic(@PathVariable("topicname") String topicname) throws TopicNotFoundException {
		service.deleteTopicByName(topicname);
		
		return ResponseEntity.ok(new MessageResponse("Topic deleted successfully!"));
	}

}
