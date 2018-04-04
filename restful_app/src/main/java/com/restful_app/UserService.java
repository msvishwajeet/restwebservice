package com.restful_app;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.restful_app.controller.UserController;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	private UserController userController ;
public UserService() {
	userController = new UserController();
}
@GET
@Path("/{uid}")
public Response getResult(@PathParam("uid") Integer uid) {
	//User = userController.getUserDetails(uid);
    Map<String, Object> res = new HashMap<String, Object>();
    res.put("name", userController.getUserDetails(uid).getFirstName());
    res.put("status", "success");
    res.put("uid", uid);

    return Response.ok(res).build();
}
}
