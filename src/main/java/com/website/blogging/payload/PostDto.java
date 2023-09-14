package com.website.blogging.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.website.blogging.entities.Comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	int postId;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	private String image;
	private Date date;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> comments= new HashSet<>();

}
