package com.sneha.socialmediademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sneha.socialmediademo.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

}
