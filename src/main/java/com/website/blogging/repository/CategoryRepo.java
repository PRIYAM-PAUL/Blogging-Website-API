package com.website.blogging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.blogging.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
