package com.website.blogging.service;

import java.util.List;

import com.website.blogging.payload.CategoryDto;

public interface CategoryService {
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,int categoryId);
	//delete
	void deleteCategory(int categoryId);
	//read
	CategoryDto getCategoryById(int categoryId);
	List<CategoryDto> getAllCategory();
	
}
