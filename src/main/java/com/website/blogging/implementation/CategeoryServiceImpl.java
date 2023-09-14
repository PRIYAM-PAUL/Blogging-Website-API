package com.website.blogging.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.blogging.entities.Category;
import com.website.blogging.exception.ResourceNotFoundException;
import com.website.blogging.payload.CategoryDto;
import com.website.blogging.repository.CategoryRepo;
import com.website.blogging.service.CategoryService;

@Service
public class CategeoryServiceImpl implements CategoryService{

	@Autowired
	ModelMapper mapper;
	@Autowired
	CategoryRepo categoryRepo;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category save = this.categoryRepo.save(this.mapper.map(categoryDto, Category.class));
		return this.mapper.map(save, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		Category save = this.categoryRepo.save(cat);
		return this.mapper.map(save, CategoryDto.class);		
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category orElseThrow = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(orElseThrow);
	}

	@Override
	public CategoryDto getCategoryById(int categoryId) {
		Category orElseThrow = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.mapper.map(orElseThrow, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAll = this.categoryRepo.findAll();
		List<CategoryDto> collect = findAll.stream().map(cat->this.mapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return collect;
	}

}
