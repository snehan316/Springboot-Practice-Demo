package com.sneha.socialmediademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sneha.socialmediademo.model.Post;

public interface PostRepository extends JpaRepository<Post,Integer> {

}
