package com.tsjr.rest;

import java.util.ArrayList;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import com.tsjr.dao.CommentDAO;
import com.tsjr.entities.Comment;
import com.tsjr.entities.ListWrapper;
import com.tsjr.entities.ServiceResponse;

@Component
@Path("/comments")
public class CommentServices {

	@Autowired 
	@Qualifier("commentDAO")
	protected CommentDAO commentDAO;

	@GET
	@Produces("application/json")
	@Path("/{version}/{messageId}/")
	public ServiceResponse<?> getComment(
					@PathParam("version") String version, 
					@PathParam("messageId") String messageId, 
					@QueryParam("offset") @DefaultValue("10") int offset, 
					@QueryParam("limit") @DefaultValue("10") int limit ) {		
		ArrayList<Comment> list = new ArrayList<Comment>();

		// Notice that this only returns 1 comment		
		Comment comment = commentDAO.get(1);
		list.add(comment);

		ServiceResponse<ListWrapper<Comment>> r = new ServiceResponse();
		r.setMessage(new ListWrapper<Comment>(list));

		// Could have alternatively used ObjectMapper.writeValueAsString(), some custom "toJSON()", 
		// or JSONObject to generate the JSON. I think I prefer explicitly generating the JSON.
		return r;
	}

	@POST
	@Path("/{version}/createComment")
	@Consumes("application/json")
	public Response createComment( Comment comment )  {
		commentDAO.create(comment);

		String result = "Product created : " + comment;
		return Response.status(201).entity(result).build();
	}	

	@GET
	@Produces("application/json")
	@Path("/{version}/{messageId}/noDB")
	public ServiceResponse<?> getCommentNoDB(
					@PathParam("version") String version, 
					@PathParam("messageId") String messageId, 
					@QueryParam("offset") @DefaultValue("10") int offset, 
					@QueryParam("limit") @DefaultValue("10") int limit ) {
		// This is kind of a ridiculous service to have in real life, but I left it in
		// because it is good for the purposes of this experiment.  It's a different
		// route and has no database interaction, simply returning some fixed queries.
		// Notice it returns multiple comments.
		
		ArrayList<Comment> list = new ArrayList<Comment>();		

		Comment c1 = new Comment();
		c1.setVersion(version);		
		c1.setMessage("first");
		list.add(c1);

		Comment c2 = new Comment();
		c2.setVersion(version);		
		c2.setMessage("second");
		list.add(c2);

		ServiceResponse<ListWrapper<Comment>> r = new ServiceResponse();
		r.setMessage(new ListWrapper<Comment>(list));

		return r;
	}	
}