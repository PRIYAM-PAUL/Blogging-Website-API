package com.website.blogging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogging.implementation.CategeoryServiceImpl;
import com.website.blogging.payload.ApiResponse;
import com.website.blogging.payload.CategoryDto;
import com.website.blogging.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategeoryServiceImpl categeoryServiceImpl;
	@PostMapping("/")
	public ResponseEntity<CategoryDto> getCategoryByid(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = this.categeoryServiceImpl.createCategory(categoryDto);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategoryByid(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("id") int id) {
		CategoryDto updateCategory = this.categeoryServiceImpl.updateCategory(categoryDto, id);
		return new ResponseEntity<>(updateCategory,HttpStatus.CREATED);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleletCategoryById(@PathVariable int id){
		this.categeoryServiceImpl.deleteCategory(id);
		return new ResponseEntity<>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> allCategory = this.categeoryServiceImpl.getAllCategory();
		return new ResponseEntity<>(allCategory,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id){
		CategoryDto categoryById = this.categeoryServiceImpl.getCategoryById(id);
		return new ResponseEntity<>(categoryById,HttpStatus.OK);
	}
}
