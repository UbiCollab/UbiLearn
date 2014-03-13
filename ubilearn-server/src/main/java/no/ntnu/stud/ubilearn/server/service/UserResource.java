package no.ntnu.stud.ubilearn.server.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import no.ntnu.stud.ubilearn.server.models.User;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	

	   	@GET
	    @Timed
		public User getArticle(@QueryParam("user") Optional<String> name){
			return new User("knut@gmail.com", "abcdefg");	
		}
	   	@POST
	   	
	
}
