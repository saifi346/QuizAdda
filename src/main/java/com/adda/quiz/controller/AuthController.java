package com.adda.quiz.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adda.quiz.model.ERole;
import com.adda.quiz.model.Role;
import com.adda.quiz.model.User;
import com.adda.quiz.repo.RoleRepository;
import com.adda.quiz.repo.UserRepository;
import com.adda.quiz.request.LoginRequest;
import com.adda.quiz.request.SignUpRequest;
import com.adda.quiz.response.JwtResponse;
import com.adda.quiz.response.MessageResponse;
import com.adda.quiz.security.jwt.JwtUtil;
import com.adda.quiz.service.UserDetailImpl;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository repo;

	@Autowired
	RoleRepository rolesRepo;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	PasswordEncoder encoder;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest login) throws Exception {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);

		UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest register) throws Exception {

		if (repo.existsByUsername(register.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (repo.existsByEmail(register.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(register.getUsername(), register.getEmail(), encoder.encode(register.getPassword()),null,0);
		Set<String> setRoles = new HashSet<>();
		setRoles.add("admin");
		Set<Role> roles = new HashSet<>();

		/*
		 * if(setRoles == null) { Role userRole = rolesRepo.findByName(ERole.ROLE_USER)
		 * .orElseThrow(()-> new RuntimeException("Error : Role is not found."));
		 * roles.add(userRole); }
		 */
		setRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = rolesRepo.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);
				break;

			default:
				Role userRole = rolesRepo.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		repo.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}

}
