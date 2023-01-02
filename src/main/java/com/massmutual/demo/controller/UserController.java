package com.massmutual.demo.controller;


import com.massmutual.demo.entity.User;
import com.massmutual.demo.exceptions.NoRecordFoundException;
import com.massmutual.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{customerID}")
    public String deleteCustomerById(@PathVariable("customerID")Long customerID) throws AccessDeniedException {
        userService.deleteCustomerById(customerID);
        return "Customer Deleted successfully";
    }

    @PutMapping("/update/{customerID}")
    public ResponseEntity<User> updateCustomer(@PathVariable("customerID") Long customerID, @RequestBody User customer) throws AccessDeniedException, NoRecordFoundException{
        User updatedUser = userService.updateCustomer(customerID, customer);
        return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get")
    public List<User> fetchCustomerList() throws AccessDeniedException {
        return userService.fetchCustomerList();
    }

    @GetMapping("/get/{customerID}")
    public ResponseEntity<User> fetchCustomerById(@PathVariable("customerID") Long customerID) throws NoRecordFoundException {
        User customer = userService.fetchCustomerById(customerID);
        return new ResponseEntity<User>(customer, HttpStatus.OK);
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity<User> fetchCustomerByName(@PathVariable("name") String name) throws NoRecordFoundException{
        return new ResponseEntity<>(userService.fetchCustomerByName(name),HttpStatus.OK);
    }

}
