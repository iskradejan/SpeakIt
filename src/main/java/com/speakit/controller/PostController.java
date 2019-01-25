package com.speakit.controller;

import com.speakit.entity.Post;
import com.speakit.entity.User;
import com.speakit.exception.ResourceMissingException;
import com.speakit.repository.PostRepository;
import com.speakit.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(path = "/api/post/")
public class PostController {

	@Resource
	private PostRepository postRepository;
	@Resource
	private UserRepository userRepository;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Post post) {
		User user = new User();
		post.setUser(user);

		postRepository.save(post);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> readAll() {
		List<Post> posts = postRepository.findAllByOrderByCreateDateDesc();

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Post post = postRepository.findById(id).orElseThrow(ResourceMissingException::new);

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PatchMapping(path = "{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Post updatePost) {
		Post post = postRepository.findById(id).orElseThrow(ResourceMissingException::new);

		if(updatePost.getBody() != null && !updatePost.getBody().isBlank()) {
			post.setBody(updatePost.getBody());
		}

		if(updatePost.getTitle() != null && !updatePost.getTitle().isBlank()) {
			post.setTitle(updatePost.getTitle());
		}

		postRepository.save(post);

		return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Post post = postRepository.findById(id).orElseThrow(ResourceMissingException::new);

		postRepository.delete(post);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
