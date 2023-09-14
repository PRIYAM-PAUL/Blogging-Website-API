package com.website.blogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogging.payload.ApiResponse;
import com.website.blogging.payload.CommentDto;
import com.website.blogging.service.CommentService;


@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/comment/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable int postId){
		CommentDto createComment = this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int id){
		this.commentService.deleteComment(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!!",true),HttpStatus.OK);
	}
	
}
