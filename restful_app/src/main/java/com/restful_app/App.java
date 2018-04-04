package com.restful_app;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
public class App extends Application<AppConfig> {
    
	@Override
	public void run(AppConfig appConfig, Environment environment) throws Exception {
		 System.out.println(appConfig.getMysqlUrl());
		 
	     environment.lifecycle().manage(new AppManaged());
	      environment.jersey().register(UserService.class);
	       // environment.jersey().register(Test2Resource.class);
	}
	
	public static void main( String[] args ) throws Exception {
        new App().run(args);
    }

}
