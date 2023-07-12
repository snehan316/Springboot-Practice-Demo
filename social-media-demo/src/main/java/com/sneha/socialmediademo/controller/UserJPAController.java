package com.sneha.socialmediademo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.sneha.socialmediademo.exceptions.UserNotFoundException;
import com.sneha.socialmediademo.model.Post;
import com.sneha.socialmediademo.model.User;
import com.sneha.socialmediademo.repository.PostRepository;
import com.sneha.socialmediademo.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJPAController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
	    User savedUser = userRepository.save(user);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users")
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> findUserByID(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("User with id:" + id + " not found");
		}
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		 userRepository.deleteById(id);

	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		 Optional<User> user = userRepository.findById(id);
		 
		 if(user.isEmpty()) {
			 throw new UserNotFoundException("User with id: " + id + " not found");
		 }
		 
		return user.get().getPosts();

	}
	
	@PostMapping("jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("User with id: " + id + " not found");
		}
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();
				
		return ResponseEntity.created(location).build();
	}
	
	
}
