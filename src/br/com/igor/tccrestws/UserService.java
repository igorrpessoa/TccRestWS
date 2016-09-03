package br.com.igor.tccrestws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("/UserService")
public class UserService {

   UserDao userDao = new UserDao();

    @GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
   public String getUsers(){
	   Gson gson = new Gson();
	   String userJSONString = gson.toJson( userDao.getAllUsers());
	  
	   return userJSONString;
   }	
}