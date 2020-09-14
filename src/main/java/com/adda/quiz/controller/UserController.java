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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adda.quiz.exceptions.UserNotFoundException;
import com.adda.quiz.model.User;
import com.adda.quiz.response.MessageResponse;
import com.adda.quiz.service.UserService;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/api/test")
public class UserController {

	@Autowired
	UserService service;

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = service.getAllUsers();

		return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> getUserByName(@PathVariable("username") String username) throws UserNotFoundException {
		User user = service.getUserByName(username);

		return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/user/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody User user)
			throws UserNotFoundException {
		service.updateUser(username, user);

		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
	}

	@DeleteMapping("/user/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) throws UserNotFoundException {
		service.deleteUserByName(username);

		return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
	}

}
