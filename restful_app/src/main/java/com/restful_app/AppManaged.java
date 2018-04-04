package com.restful_app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.dropwizard.lifecycle.Managed;

public class AppManaged implements Managed{
	private static ApplicationContext context;
	public void start() throws Exception {
		context = new AnnotationConfigApplicationContext("com.restful_app.*");
		
	}

	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
