package com.speakit.controller;

import com.speakit.entity.Post;
import com.speakit.entity.User;
import com.speakit.exception.InvalidRequestException;
import com.speakit.exception.ResourceAlreadyExistsException;
import com.speakit.exception.ResourceMissingException;
import com.speakit.repository.PostRepository;
import com.speakit.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/post/")
public class PostController {

	@Resource
	private PostRepository postRepository;
	@Resource
	private UserRepository userRepository;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid Post post, User user) {

		if(postRepository.findOneByTitle(post.getTitle()) == null) {
			post.setUser(user);

			postRepository.save(post);
		} else {
			throw new ResourceAlreadyExistsException();
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> readAll() {
		List<Post> posts = postRepository.findAllByOrderByIdDesc();

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping(path = "page/{pageNumber}")
	public ResponseEntity<?> readByPage(@PathVariable Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("id").descending());
		Page<Post> posts = postRepository.findAll(pageable);

		return new ResponseEntity<>(posts.getContent(), HttpStatus.OK);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Post post = postRepository.findById(id).orElseThrow(ResourceMissingException::new);

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PatchMapping(path = "{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Post updatePost, User user) {
		Post post = postRepository.findById(id).orElseThrow(ResourceMissingException::new);

		if(post.getUser() != user){
			throw new InvalidRequestException();
		}

		if(updatePost.getBody() != null && !updatePost.getBody().isBlank()) {
			post.setBody(updatePost.getBody());

			postRepository.save(post);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable Long id, User user) {
		Post post = postRepository.findById(id).orElseThrow(ResourceMissingException::new);

		if(post.getUser() != user){
			throw new InvalidRequestException();
		}

		postRepository.delete(post);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
