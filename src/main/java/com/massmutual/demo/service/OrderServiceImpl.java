package com.massmutual.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.massmutual.demo.entity.Address;
import com.massmutual.demo.entity.Order;
import com.massmutual.demo.entity.Product;
import com.massmutual.demo.entity.User;
import com.massmutual.demo.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository repository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
//	@Override
//	public Order addOrder(Order order) {
//
//		Product p1 = productrepo.getProductById(order.getProduct().getProductId());
//		User c1 = customerrepo.getCustomerById(order.getCustomer().getCustomerID());
////		Address a1 = addressrepo.getById(order.getAddress().getAddressID());
//		if(c1==null) {
//			System.out.println("Customer is inactive");
//		}else if (p1==null) {
//			System.out.println("product is unavailable");
//		}
//		else {
//			order.setProduct(p1);
//			order.setCustomer(c1);
////			order.setAddress(a1);
//			return repository.save(order);
//		}
//		return null;
//	}


	@Override
	public Order addOrder(Order order) {

		Optional<Address> address = addressService.fetchAddressById(order.getAddress().getAddressID());

		User user = userService.fetchCustomerById(order.getCustomer().getUser_id());

		Product product = productService.viewProduct(order.getProduct().getProductId());

		Order order1 = new Order();
		order1.setOrderDate(LocalDate.now());
		order1.setAddress(address.get());
		order1.setProduct(product);
		order1.setCustomer(user);
		order1.setOrderStatus("Accepted");

		return repository.save(order1);


	}

	@Override
	public Order viewOrder(Long orderID) {
		return repository.findById(orderID).get();
	}

	@Override
	public List<Order> viewAllOrders() {
		return repository.findAll();
	}

	@Override
	public Order removeOrderById(Long orderID) {
		Order exists = repository.findById(orderID).get();
		Order resultOrder=null;
		if(exists != null) {
			repository.deleteById(orderID);
			resultOrder=exists;
		}
		return resultOrder;
	}

	@Override
	public Order getOrderByLocation(String pincode) {
		
		return repository.findByAddressPincode(pincode);
	}

	@Override
	public Order updateOrder(Long orderID, Order order) {
		
		Order o1 = repository.findById(orderID).get();
		if(Objects.nonNull(order.getOrderStatus()) && !"".equalsIgnoreCase(order.getOrderStatus())) {
			o1.setOrderStatus(order.getOrderStatus());
		}
		
		return repository.save(o1);
}

	@Override
	public List<Order> viewOrderByUserId(Long customerID) {
		return repository.findByCustomer(customerID);
	}
	
}