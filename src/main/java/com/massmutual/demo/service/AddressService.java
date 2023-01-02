package com.massmutual.demo.service;

import java.util.List;
import java.util.Optional;

import com.massmutual.demo.entity.Address;
import com.massmutual.demo.exceptions.AddressNotFoundException;


public interface AddressService {

	Address saveAddress(Address address);

	List<Address> fetchAddressList();

	Optional<Address> fetchAddressById(Long addressID);

	String deleteAddressById(Long addressID) ;

	Address updateAddress(Long addressID, Address address);

}
