package com.website.blogging.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.blogging.entities.Comment;
import com.website.blogging.entities.Post;
import com.website.blogging.exception.ResourceNotFoundException;
import com.website.blogging.payload.CommentDto;
import com.website.blogging.repository.CommentRepo;
import com.website.blogging.repository.PostRepo;
import com.website.blogging.service.CommentService;

import java.util.HashSet;
import java.util.Set;

@Service
public class CommentServiceimpl implements CommentService {

	@Autowired
	CommentRepo commentRepo;
	@Autowired
	PostRepo postRepo;
	@Autowired
	ModelMapper mapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Post post =this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id ", postId));
		Comment comment = this.mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment save = this.commentRepo.save(comment);
		CommentDto dto = this.mapper.map(save, CommentDto.class);
		return dto;
	}

	@Override
	public void deleteComment(int id) {
		Comment comment = this.commentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id",id));
		this.commentRepo.delete(comment);
		
	}


}
