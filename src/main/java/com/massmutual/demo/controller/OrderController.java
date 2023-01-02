package com.massmutual.demo.controller;

import java.util.List;

import com.massmutual.demo.entity.Order;
import com.massmutual.demo.service.OrderService;

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

@RequestMapping("/order")
@RestController
public class OrderController {

	@Autowired
	private OrderService service;
	
	@GetMapping("/get/{orderID}")
	public ResponseEntity<Order> getOrderByID(@PathVariable("orderID") Long orderID) {
		Order order = service.viewOrder (orderID);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/get/all")
	public ResponseEntity<List<Order>> getAllOrders() throws AccessDeniedException {
		List<Order> orderList = service.viewAllOrders();
		return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
	}
		
	
	@GetMapping("/get/location/{pincode}")
	public ResponseEntity<Order> getOrderByLocation(@PathVariable("pincode") String pincode) {
		return new ResponseEntity<>(service.getOrderByLocation(pincode),HttpStatus.OK);
	}

	@PutMapping("/update/{orderID}")
	public ResponseEntity<Order> updateOrder(@PathVariable("orderID")Long orderID, @RequestBody Order order) {
		return new ResponseEntity<>(service.updateOrder(orderID, order),HttpStatus.OK);
	}
	  
	
	@GetMapping("/get/user/{customerID}")
	ResponseEntity<List<Order>> getUserID(@PathVariable("customerID") Long customerID){
		List<Order> rtnObj = service.viewOrderByUserId(customerID);
		return new ResponseEntity<List<Order>>(rtnObj, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
		Order saved = service.addOrder(order);
		return new ResponseEntity<Order>(saved, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{orderId}")
	public ResponseEntity<Order> deleteOrderById(@PathVariable("orderId") Long orderID) {
		Order order= service.removeOrderById(orderID);
		if(order==null) {
			return new ResponseEntity("Sorry! Orders are not available!",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

}
