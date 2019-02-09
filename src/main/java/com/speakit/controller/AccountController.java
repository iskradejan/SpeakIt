package com.speakit.controller;

import com.speakit.dataobject.Account;
import com.speakit.entity.User;
import com.speakit.entity.Verification;
import com.speakit.exception.DisplaynameAlreadyExistsException;
import com.speakit.exception.InvalidRequestException;
import com.speakit.exception.ResourceAlreadyExistsException;
import com.speakit.exception.ResourceMissingException;
import com.speakit.repository.UserRepository;
import com.speakit.repository.VerificationRepository;
import com.speakit.service.email.EmailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Random;

@RestController
@RequestMapping(path = "/api/account/")
public class AccountController {
	@PersistenceContext
	private EntityManager entityManager;
	@Resource
	private UserRepository userRepository;
	@Resource
	private VerificationRepository verificationRepository;
	@Resource
	private PasswordEncoder passwordEncoder;
	@Resource
	private EmailSender emailSender;

	@Transactional
	@CrossOrigin(origins = "http://localhost:4200")
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

		entityManager.persist(user);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Transactional
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "verify/request")
	public ResponseEntity<?> request(@RequestBody Account account) {
		if(account.getUsername() == null || account.getUsername().isBlank()) {
			throw new InvalidRequestException();
		}

		User user = userRepository.findByUsername(account.getUsername());
		if(user == null) {
			throw new ResourceMissingException();
		} else {
			if(!user.isVerified()) {
				String code = null;
				Verification existingVerification = verificationRepository.findByUser(user);
				if(existingVerification == null) {
					Verification verification = new Verification()
							.setUser(user)
							.setCode(generateString());
					entityManager.persist(verification);
					code = verification.getCode();
				} else {
					code = existingVerification.getCode();
				}

				emailSender.send(account.getUsername(), "Verify Your Account", "The security code is: " + code);
			}
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Transactional
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "verify/claim")
	public ResponseEntity<?> claim(@RequestBody Verification verification) {
		Verification existingVerification = verificationRepository.findOneByCode(verification.getCode());
		if(existingVerification == null) {
			throw new ResourceMissingException();
		}

		existingVerification.getUser().setVerified(true);
		verificationRepository.delete(existingVerification);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private String generateString() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		return String.format("%06d", number);
	}
}
