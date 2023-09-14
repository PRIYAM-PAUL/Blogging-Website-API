package com.website.blogging.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

		private int categoryId;
		@NotEmpty
		@Size(min=5,max=20,message="Title Name should be more than 5 and less than 20")
		private String categoryTitle;
		@NotEmpty
		private String categoryDescription;
	}


