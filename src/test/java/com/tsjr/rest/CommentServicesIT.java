package com.tsjr.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.tsjr.dao.CommentDAO;
import com.tsjr.entities.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
				"classpath:test-spring-datasources.xml", // Containing HyperSQL datasource and test Hibernate properties				
				"classpath:test-spring-hibernate.xml" // Containing Hibernate session bean, transaction config and DAOs
})
public class CommentServicesIT extends TestCase {

	@Qualifier("commentDAO")
	@Autowired protected CommentDAO commentDAO; // Injected by springy magic
	@Autowired protected DataSource ds; // Injected by springy magic
	protected IDatabaseTester dbTester;	

	/** 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		/** Setup the in memory database and populate it with initial data **/
		dbTester = new DataSourceDatabaseTester(ds);
		IDataSet dataSet = new FlatXmlDataSet(getClass().getResource("CommentTest.xml"));
		dbTester.setDataSet(dataSet);
		dbTester.onSetup();		
	}

	/** 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		dbTester.onTearDown();
	}

	@Test
	public void testServiceNoDB() throws Exception {
		try {
			Client c = Client.create();
			WebResource r = c.resource("http://localhost:9090/tsjr/comments/version/messageId/noDB");
			System.out.println("JSON=>> " + r.get(String.class));		
		} catch( Exception e ) {
			System.out.println( "WARNING: Web Server is probably inaccessible: " + e );
		}
	}	

	@Test
	public void testService() throws Exception {
		try {
			// Create a new message via a POST request
			URL url = new URL("http://localhost:9090/tsjr/comments/version/createComment");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"version\":\"version\",\"message\":\"Test Create Message\",\"offset\":\"10\",\"limit\":\"10\"}";
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("Output from Server ...."+output);
			}
			conn.disconnect();

			// Get the message that was just created
			Client c = Client.create();
			WebResource r = c.resource("http://localhost:9090/tsjr/comments/version/messageId");
			System.out.println("JSON=>> " + r.get(String.class));		
		} catch( Exception e ) {
			System.out.println( "WARNING: Web Server is probably inaccessible: " + e );
			throw e;
		}
	}		

	@Test
	public void testCreate() {
		// DAO Test
		
		// Create the new comment
		Comment newComment = newComment(); // Message2
		int id = commentDAO.create(newComment);

		// Check it's there
		Comment message2 = commentDAO.get(id);
		checkComment(message2, id, newComment.getMessage());

		// Check original data is still intact
		Comment message1 = commentDAO.get(1);
		checkComment(message1, 1, "Message1");
	}

	private Comment newComment() {
		Comment comment = new Comment();
		comment.setMessage("Message1");
		return comment;
	}

	private void checkComment(Comment comment, int id, String name) {
		assertNotNull(comment);
		assertEquals(id, comment.getId());
		assertEquals(name, comment.getMessage());
	}	

}
