package com.adda.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adda.quiz.model.LeaderBoard;
import com.adda.quiz.service.LeaderBoardService;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/leaderboard")
public class LeaderBoardController {

	@Autowired
	LeaderBoardService service;
	
	@GetMapping("/overall")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<LeaderBoard>> getLeaderBoard() {
		List<LeaderBoard> leaderBoard = service.getLeaderBoard();

		return new ResponseEntity<>(leaderBoard, new HttpHeaders(), HttpStatus.OK);
	}


	@GetMapping("/{topicname}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<LeaderBoard>> getLeaderBoardByTopic(@PathVariable("topicname") String topicname) {
		List<LeaderBoard> leaderBoard = service.getLeaderBoardByTopic(topicname);

		return new ResponseEntity<>(leaderBoard, new HttpHeaders(), HttpStatus.OK);
	}
}
