package com.website.blogging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.blogging.entities.Comment;

public interface CommentRepo  extends JpaRepository<Comment, Integer>{

}
