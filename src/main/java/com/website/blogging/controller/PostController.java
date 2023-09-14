package com.website.blogging.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.website.blogging.global.ApiContants;
import com.website.blogging.payload.ApiResponse;
import com.website.blogging.payload.PostDto;
import com.website.blogging.payload.PostPageableResponse;
import com.website.blogging.repository.CategoryRepo;
import com.website.blogging.repository.UserRepo;
import com.website.blogging.service.ImageService;
import com.website.blogging.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	PostService postService;
	@Autowired
	UserRepo userRepo;
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ModelMapper mapper;
	@Autowired
	ImageService imageService;
	@Value("${project.image}") 
	String path;
	@GetMapping("/posts")
	public ResponseEntity<PostPageableResponse>getAllPost(@RequestParam(value="pageNumber",defaultValue = ApiContants.PAGE_NUMBER,required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = ApiContants.PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = ApiContants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "arr",defaultValue = ApiContants.ARR,required = false) String arr
			){
		 PostPageableResponse allPosts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,arr);
		return new ResponseEntity<>(allPosts,HttpStatus.OK);
	}
	@PostMapping("/user/{uid}/category/{cid}/posts/")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto dto,@PathVariable("uid") int uid,@PathVariable("cid") int cid){
		PostDto createPost = this.postService.createPost(dto, uid, cid);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("users/{uid}/posts")
	public ResponseEntity<List<PostDto>> getPostbyUser(@PathVariable int uid ){
		List<PostDto> allPostByUser = this.postService.getAllPostByUser(uid);
		return new ResponseEntity<List<PostDto>>(allPostByUser,HttpStatus.OK);
	}
	@GetMapping("category/{cid}/posts")
	public ResponseEntity<List<PostDto>> getPostbyCategory(@PathVariable int cid ){
		List<PostDto> allPostByUser = this.postService.getAllPostByCategory(cid);
		return new ResponseEntity<List<PostDto>>(allPostByUser,HttpStatus.OK);
	}
	
	@GetMapping("post/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int id) {
		PostDto postById = this.postService.getPostById(id);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	@DeleteMapping("post/{id}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable int id) {
		this.postService.deletePost(id);
		return new ResponseEntity<>(new ApiResponse("Post deleted successfully !!",true),HttpStatus.OK);
	}
	@PutMapping("post/{id}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto dto,@PathVariable int id) {
		PostDto postById = this.postService.updatePost(dto,id);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	@GetMapping("/posts/{title}")
	public ResponseEntity<List<PostDto>> getPostByTitle(@PathVariable String title){
		List<PostDto> searchPost = this.postService.searchPost(title);
		return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
	}
	@PostMapping("/image/{id}")
	public ResponseEntity<PostDto> uploadImage(@PathVariable int id,@RequestParam("image") MultipartFile file) throws IOException{
		PostDto post = this.postService.getPostById(id);
		String uploadImage = this.imageService.uploadImage(path, file);
		post.setImage(uploadImage);
		PostDto updatePost = this.postService.updatePost(post, id);
		return new ResponseEntity<>(updatePost,HttpStatus.OK);
	}
	@GetMapping(value="/image/view/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void DownloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream resource = this.imageService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
