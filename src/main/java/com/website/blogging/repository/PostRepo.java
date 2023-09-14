package com.website.blogging.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.blogging.entities.Category;
import com.website.blogging.entities.Post;
import com.website.blogging.entities.User;
import com.website.blogging.payload.PostDto;


public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByCategory(Category category);
	List<Post> findByUser(User user );
	List<Post> findByTitleContaining(String keyword);

}
