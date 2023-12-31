package com.sneha.socialmediademo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;

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

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	UserDAOService userDAOService;
	
	@GetMapping("/users")
	public List<User> findAllUsers(){
		return userDAOService.findAll();
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
	    User savedUser = userDAOService.saveUser(user);
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("users/{id}")
	public EntityModel<User> findUserByID(@PathVariable int id) {
		User user = userDAOService.findUserByID(id);
		if(user==null) {
			throw new UserNotFoundException("User with id:" + id + " not found");
		}
		
		EntityModel<User> entityModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDAOService.deleteUser(id);
		
		if(user == null)
			throw new UserNotFoundException("User with id:" + id + " not found");
	
	}
}
