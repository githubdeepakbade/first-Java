package com.codewithdurgesh.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.codewithdurgesh.blog.entities.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.Payloads.CategoryDto;
import com.codewithdurgesh.blog.exception.ResourceNotFoundException;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
	private CategoryRepo categoryRepo;

@Autowired
private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
	Category cat =this.modelMapper.map(categoryDto, Category.class);
	Category addedCat =this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
	Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updateCategory = this.categoryRepo.save(cat);
				
		return this.modelMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getcategoies() {
		List<Category>categories=this.categoryRepo.findAll();
	List<CategoryDto>categoryDtos	=categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}

}
