package com.website.blogging.implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.website.blogging.entities.Category;
import com.website.blogging.entities.Post;
import com.website.blogging.entities.User;
import com.website.blogging.exception.ResourceNotFoundException;
import com.website.blogging.payload.CategoryDto;
import com.website.blogging.payload.PostDto;
import com.website.blogging.payload.PostPageableResponse;
import com.website.blogging.payload.UserDto;
import com.website.blogging.repository.CategoryRepo;
import com.website.blogging.repository.PostRepo;
import com.website.blogging.repository.UserRepo;
import com.website.blogging.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepo postRepo;
	@Autowired
	ModelMapper mapper;
	@Autowired
	UserRepo userRepo;
	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto dto, int uid, int cid) {
		User user = this.userRepo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("User", "Id", uid));
		Category category = this.categoryRepo.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", cid));
		Post post = this.mapper.map(dto, Post.class);
		post.setImage("default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post save = this.postRepo.save(post);
		return this.mapper.map(save,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto dto, int id) {
		Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());
		post.setImage(dto.getImage());
		Post save = this.postRepo.save(post);
		return this.mapper.map(save,PostDto.class);
	}

	@Override
	public PostDto getPostById(int id) {
		Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
		return this.mapper.map(post,PostDto.class);
	}

	@Override
	public void deletePost(int id) {
		Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
		this.postRepo.delete(post);
	}

	@Override
	public PostPageableResponse getAllPosts(int pageNumber ,int pageSize,String sortBy,String arr) {
		Sort sort=null;
		if(arr.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort =Sort.by(sortBy).descending();
		}
		Pageable ofSize = PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> findAll = this.postRepo.findAll(ofSize);
		List<PostDto> collect = findAll.stream().map(post->this.mapper.map(post,PostDto.class)).collect(Collectors.toList());
		PostPageableResponse pageableResponse = new PostPageableResponse();
		pageableResponse.setPosts(collect);
		pageableResponse.setPageSize(pageSize);
		pageableResponse.setPageNumber(pageNumber);
		pageableResponse.setPageElement(findAll.getTotalElements());
		pageableResponse.setLastPage(findAll.isLast());
		return pageableResponse;
	}

	@Override
	public List<PostDto> searchPost(String keywords) {
		List<Post> findAllPostContaining = this.postRepo.findByTitleContaining(keywords);
		List<PostDto> collect = findAllPostContaining.stream().map(post->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostDto> getAllPostByCategory(int categoryId) {
		Category orElseThrow = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		List<Post> findByCategory = this.postRepo.findByCategory(orElseThrow);
		List<PostDto> map = findByCategory.stream().map(e->this.mapper.map(e,PostDto.class)).collect(Collectors.toList());
		return map;
	}

	@Override
	public List<PostDto> getAllPostByUser(int userId) {
		User orElseThrow = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		List<Post> findByUser = this.postRepo.findByUser(orElseThrow);
		List<PostDto> map = findByUser.stream().map(e->this.mapper.map(e,PostDto.class)).collect(Collectors.toList());
		return map;
	}

}
