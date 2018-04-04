package com.restful_app.controller;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restful_app.model.Address;
import com.restful_app.persistence.AddressPersistenceJDBI;

@Service
public class AddressController {
	public static final Scanner scanner = new Scanner(System.in);
	@Autowired
	ValidationController validationController;
	@Autowired
	AddressPersistenceJDBI addressPersistenceJDBI;
	
	public void insertAddress(int userId) {
		Address address = new Address();
		System.out.println("Enter your pin");
		address.setPin(validationController.validInt());
		System.out.println("Enter your city Name");
		address.setCityName(scanner.nextLine());
		System.out.println("Enter State Name");
		address.setStateName(scanner.nextLine());
		address.setUserId(userId);
		if (addressPersistenceJDBI.getCount(userId)!=0) {
			System.out.println("Already Record Exist for the given user!");
			return;
		}
		addressPersistenceJDBI.insertAddress(address);
		System.out.println("Successfully Added");
	}
	public void getYourAddress(int userId) {

		if(addressPersistenceJDBI.getCount(userId)==0) {
			System.out.println("No match found!!!");
		}
		else {
			Address address =addressPersistenceJDBI.getAddress(userId);
			System.out.println("Pin: "+address.getPin());
			System.out.println("City : "+address.getCityName());
			System.out.println("State: "+address.getStateName().toUpperCase());
			
		}
		}
	public void printAllAddress() {
		List<Address> li =addressPersistenceJDBI.getAllAddress();
		for (Address address : li) {
			System.out.println("Pin: "+address.getPin());
			System.out.println("City : "+address.getCityName());
			System.out.println("State: "+address.getStateName().toUpperCase());
			System.out.println();
		}
	}
	public  void getAddressCount() {
		int s = addressPersistenceJDBI.getCount();
		System.out.println(s);
	}

}
