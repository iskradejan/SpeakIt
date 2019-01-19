package com.speakit.controller;

import com.speakit.entity.Post;
import com.speakit.entity.User;
import com.speakit.repository.PostRepository;
import com.speakit.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/api/post/")
public class PostController {

	@Resource
	private PostRepository postRepository;
	@Resource
	private UserRepository userRepository;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Post post) {
		User user = userRepository.findById(1L).orElseThrow();
		user.addPost(post);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Post post = postRepository.findById(id).orElseThrow();

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PatchMapping(path = "{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Post updatePost) {
		Post post = postRepository.findById(id).orElseThrow();

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
	public ResponseEntity<?> delete(@PathVariable Long id, @RequestBody Post updatePost) {
		Post post = postRepository.findById(id).orElseThrow();

		postRepository.delete(post);

		return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
	}
}
