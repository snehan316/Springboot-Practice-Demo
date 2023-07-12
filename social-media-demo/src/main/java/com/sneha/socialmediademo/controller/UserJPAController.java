package com.sneha.socialmediademo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
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

import com.sneha.socialmediademo.dao.UserDAOService;
import com.sneha.socialmediademo.exceptions.UserNotFoundException;
import com.sneha.socialmediademo.model.User;
import com.sneha.socialmediademo.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJPAController {

	@Autowired
	UserRepository repository;
	
	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
	    User savedUser = repository.save(user);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users")
	public List<User> findAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> findUserByID(@PathVariable int id) {
		Optional<User> user = repository.findById(id);
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
		 repository.deleteById(id);

	}
}
