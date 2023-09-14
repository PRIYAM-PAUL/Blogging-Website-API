package com.website.blogging.service;

import java.util.List;

import com.website.blogging.entities.Post;
import com.website.blogging.payload.PostDto;
import com.website.blogging.payload.PostPageableResponse;

public interface PostService {

	PostDto createPost(PostDto dto,int uid,int cid);
	PostDto updatePost(PostDto dto,int id);
	PostDto getPostById(int id);
	void deletePost(int id);
	PostPageableResponse getAllPosts(int pageNumber,int pageSize,String sortBy,String arr);
	List<PostDto> getAllPostByCategory(int categoryId);
	List<PostDto> getAllPostByUser(int userId);
	List<PostDto> searchPost(String keywords);
}
