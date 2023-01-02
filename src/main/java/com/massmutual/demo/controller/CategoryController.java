package com.massmutual.demo.controller;

import java.util.List;

import com.massmutual.demo.entity.Category;
import com.massmutual.demo.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService service;
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") Integer categoryId) {
		Category category = service.viewCategory(categoryId);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) throws AccessDeniedException {
		Category saved = null;
		saved = service.addCategory(category);
		return new ResponseEntity<Category>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/all")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categoryList = service.viewAllCategories();
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<Category> deleteCategoryById(@PathVariable("categoryId")Integer categoryId) throws AccessDeniedException {
		Category category = service.removeCategoryById(categoryId);
		if(category==null) {
			return new ResponseEntity("Sorry! Products are not available!",HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<Category> updateCategory (
			@RequestBody Category category) throws AccessDeniedException{
		Category categories= service.updateCategory(category);
		
		return new ResponseEntity<Category>(categories, HttpStatus.OK);
	}
}