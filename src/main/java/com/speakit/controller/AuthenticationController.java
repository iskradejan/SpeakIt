package com.speakit.controller;

import com.speakit.dataobject.Login;
import com.speakit.entity.Session;
import com.speakit.entity.User;
import com.speakit.exception.NotAuthenticatedException;
import com.speakit.exception.ResourceMissingException;
import com.speakit.repository.SessionRepository;
import com.speakit.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/auth/")
public class AuthenticationController {
	@Resource
	private UserRepository userRepository;
	@Resource
	private SessionRepository sessionRepository;
	@Resource
	private PasswordEncoder passwordEncoder;

	@PostMapping(path = "login")
	public ResponseEntity<?> login(@RequestBody @Valid Login login) {
		User user = userRepository.findByUsername(login.getUsername());
		if(user == null) {
			throw new ResourceMissingException();
		}

		if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
			throw new NotAuthenticatedException();
		}

		Session session = sessionRepository.findByUser(user);

		String token = UUID.randomUUID().toString();

		if(session == null) {
			session = new Session().setUser(user).setToken(token);
		} else {
			session.setToken(token);
		}

		sessionRepository.save(session);

		return new ResponseEntity<>(new Session().setToken(token), HttpStatus.ACCEPTED);
	}
}
