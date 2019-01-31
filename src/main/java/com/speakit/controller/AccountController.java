package com.speakit.controller;

import com.speakit.dataobject.Account;
import com.speakit.entity.User;
import com.speakit.exception.DisplaynameAlreadyExistsException;
import com.speakit.exception.ResourceAlreadyExistsException;
import com.speakit.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/account/")
public class AccountController {
	@Resource
	private UserRepository userRepository;
	@Resource
	private PasswordEncoder passwordEncoder;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid Account account) {
		if(userRepository.findByUsername(account.getUsername()) != null) {
			throw new ResourceAlreadyExistsException();
		}

		if(userRepository.findByDisplayName(account.getDisplayName()) != null) {
			throw new DisplaynameAlreadyExistsException();
		}

		User user = new User()
				.setPassword(passwordEncoder.encode(account.getPassword()))
				.setUsername(account.getUsername())
				.setDisplayName(account.getDisplayName());

		userRepository.save(user);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
