package com.massmutual.demo.controller;

import java.util.List;

import com.massmutual.demo.entity.Address;
import com.massmutual.demo.exceptions.AddressNotFoundException;
import com.massmutual.demo.exceptions.AppException;
import com.massmutual.demo.exceptions.NoRecordFoundException;
import com.massmutual.demo.service.AddressService;
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
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService service;

	@PostMapping("/save")
	public ResponseEntity<Address> saveAddress(@RequestBody Address address) throws AppException {
		return new ResponseEntity<>(service.saveAddress(address),HttpStatus.CREATED);
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Address>> fetchAddressList() throws AppException, AccessDeniedException {
		return new ResponseEntity<>( service.fetchAddressList(),HttpStatus.OK);
	}
	
	@GetMapping("/get/{addressID}")
	public ResponseEntity<Address> fetchAddressById(@PathVariable("addressID") Long addressID) throws NoRecordFoundException {
		return new ResponseEntity(service.fetchAddressById(addressID),HttpStatus.OK);
	}

	@DeleteMapping("/delete/{addressID}")
	public String deleteAddressById(@PathVariable("addressID")Long addressID) throws AddressNotFoundException, AppException {
			return service.deleteAddressById(addressID);
	}

	@PutMapping("/update/{addressID}")
	public ResponseEntity<Address> updateAddress(@PathVariable("addressID") Long addressID, @RequestBody Address address) throws AppException, AccessDeniedException{
		return new ResponseEntity<>(service.updateAddress(addressID, address),HttpStatus.OK);
	}

}
