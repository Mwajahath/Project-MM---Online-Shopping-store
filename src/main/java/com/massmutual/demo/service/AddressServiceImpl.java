package com.massmutual.demo.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.massmutual.demo.entity.Address;
import com.massmutual.demo.exceptions.AddressNotFoundException;
import com.massmutual.demo.exceptions.AppException;
import com.massmutual.demo.exceptions.NoRecordFoundException;
import com.massmutual.demo.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private AddressRepository repository;
	
	@Override
	public Address saveAddress(Address address) {
		// TODO Auto-generated method stub
		return repository.save(address);
	}

	@Override
	public List<Address> fetchAddressList() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Optional<Address> fetchAddressById(Long addressID)  {
		// TODO Auto-generated method stub
		Optional<Address> address  = repository.findById(addressID);
		address.orElseThrow(()->new NoRecordFoundException("No record found with id : "+addressID));
		return address;
	}

	@Override
	public String deleteAddressById(Long addressID){
		// TODO Auto-generated method stub
		try {
			int update = repository.deleteAddressByAddressID(addressID);
			switch (update){
				case 1 :
					return "Address Successfully deleted";
				case 0 :
					throw new AppException("Unable to delete record with id: "+addressID);
				default:
					throw new AppException("Some error occurred");
			}
		} catch (NullPointerException e){
			throw new AddressNotFoundException("Address not present with id: "+addressID);
		}

	}

	@Override
	public Address updateAddress(Long addressID, Address address) {
	
		Address add = repository.findById(addressID).get();
		
		if(Objects.nonNull(address.getStreetNo()) && !"".equalsIgnoreCase(address.getStreetNo())) {
			add.setStreetNo(address.getStreetNo());
			
		}
		if(Objects.nonNull(address.getBuildingName()) && !"".equalsIgnoreCase(address.getBuildingName())) {
			add.setBuildingName(address.getBuildingName());
			
		}
		if(Objects.nonNull(address.getCity()) && !"".equalsIgnoreCase(address.getCity())) {
			add.setCity(address.getCity());
			
		}
		if(Objects.nonNull(address.getState()) && !"".equalsIgnoreCase(address.getState())) {
			add.setState(address.getState());
			
		}
		if(Objects.nonNull(address.getCountry()) && !"".equalsIgnoreCase(address.getCountry())) {
			add.setCountry(address.getCountry());
		}
		if(Objects.nonNull(address.getPincode()) && !"".equalsIgnoreCase(address.getPincode())) {
			add.setPincode(address.getPincode());
		}

		return repository.save(add);

	}

}
