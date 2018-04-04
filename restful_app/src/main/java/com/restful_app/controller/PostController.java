package com.restful_app.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restful_app.model.Post;
import com.restful_app.persistence.PostPersistenceJDBI;

@Service
public class PostController {
	public static final Scanner scanner  = new Scanner(System.in);
	@Autowired
	PostPersistenceJDBI postPersistenceJDBI;
	
	public void insertPost(int userId) {
		Post post = new Post();
		System.out.println("Enter Your content");
		post.setContent(scanner.nextLine());
		post.setDateOfPost(new Timestamp(new Date().getTime()));
		post.setUserId(userId);
		postPersistenceJDBI.insertPost(post);
		System.out.println("Successfully uploaded");
	}

}
