package com.massmutual.demo.controller;

import java.util.List;

import com.massmutual.demo.entity.Product;
import com.massmutual.demo.exceptions.AppException;
import com.massmutual.demo.service.ProductService;

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

@RequestMapping("/product")
@RestController
public class ProductController {

	@Autowired
	private ProductService service;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) throws AccessDeniedException {
		Product saved = service.addProduct(product);
		return new ResponseEntity<Product>(saved, HttpStatus.OK);
	}

	@GetMapping("/get/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Integer productId) {
		Product product = service.viewProduct(productId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/get/all")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> productList = service.viewAllProducts();
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Product> deleteProductById(@PathVariable("productId")Integer productId) throws AccessDeniedException, AppException {
		Product product= service.removeProductById(productId);
		if(product==null) {
			return new ResponseEntity("Sorry! Products are not available!",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws AccessDeniedException {
		Product products= service.updateProduct(product);
		
		return new ResponseEntity<Product>(products, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/get/category/{cname}")
	ResponseEntity<List<Product>> getProffessorCourse(@PathVariable("cname") String cname) throws AccessDeniedException{
		List<Product> rtnObj = service.viewProductsByCategory(cname);
		
		return new ResponseEntity<List<Product>>(rtnObj, HttpStatus.OK);
	}
}
