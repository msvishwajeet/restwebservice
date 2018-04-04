package com.restful_app.controller;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restful_app.model.User;
import com.restful_app.persistence.UserPersistenceJDBI;
@Service
public class UserController {
	public static final Scanner scanner = new Scanner(System.in);
	@Autowired
	ValidationController validationController;
	@Autowired
	UserPersistenceJDBI userPersistenceJDBI;
	
	public void insertUser() {
		User user = new User();
		System.out.println("Enter your First Name");
		user.setFirstName(scanner.nextLine());
		System.out.println("Enter your Last Name");
		user.setLastName(scanner.nextLine());
		System.out.println("Enter Your Gmail");
		String email =validationController.validEmail();
		user.setEmail(email);
		int available=userPersistenceJDBI.emailExistence(email);
		if (available>0) {
			System.out.println("Email Already Exist");
			System.out.println("Please Register Again with you Unique Email Id");
			System.out.println();
			return;
		}
		
		System.out.println("Enter your password");
		user.setPassword(scanner.nextLine());
		System.out.println("Enter Your Mobile number");
		user.setMobileNumber(validationController.validMobileNumber());
		Date date =new Date();
		Timestamp timeStamp=new Timestamp(date.getTime());  
		user.setTimeStamp(timeStamp);
		if (userPersistenceJDBI.emailExistence(email)!=0) {
			System.out.println("Already Record Exist for the given user!");
			return;
		}
		userPersistenceJDBI.insertUser(user);
		System.out.println("Successfully Added");
	}
	public User getUserDetails(int uid) {
		return userPersistenceJDBI.getuser(uid);
	}
}
