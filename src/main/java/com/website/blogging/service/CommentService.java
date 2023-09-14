package com.website.blogging.service;

import com.website.blogging.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, int postId);
	void deleteComment(int id);
}
