package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.Flight;
import com.skillstorm.data.FlightDAO;

// commenting for the MVC example
@WebServlet(urlPatterns = "/api/flight")
public class FlightServlet extends HttpServlet{
	
	// servlets are Singletons...
	FlightDAO dao = new FlightDAO();
	
	// safe : no server state is changed
	// GET /api/trainee?sequence_no=__
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("sequence_no") != null) {
		String param = req.getParameter("sequence_no");
		int sequence_no = Integer.parseInt(param);
		Flight flight = dao.findBySequence_no(sequence_no);	// JDBC
		String json = new ObjectMapper().writeValueAsString(flight); // converting Java obj -> JSON
		System.out.println(json);
		resp.getWriter().print(json); // write the data to the response
		}else if(req.getParameter("givenAirline") != null){
			String param = req.getParameter("givenAirline");
			// int sequence_no = Integer.parseInt(param);
			System.out.println("givenAirline is being called");
			Set<Flight> flight = dao.findByAirline(param);	// JDBC
			String json = new ObjectMapper().writeValueAsString(flight); // converting Java obj -> JSON
			resp.getWriter().print(json);
		}
		
		else {
			Set<Flight> flight = dao.findAll();
			String json = new ObjectMapper().writeValueAsString(flight);
			resp.getWriter().print(json);
		}
	}
	
	// indempotence: subsequent/repetitive calls have an adverse result
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doPost(req, resp);	// default is to throw 405
		InputStream requestBody = req.getInputStream();
		// convert the request body into a Trainee.class object
		Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);
		System.out.println(flight);
		try {
			Flight updated = dao.create(flight);
			// return the updated flight
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updated));
			resp.setStatus(201);	// "return"
			resp.setContentType("application/json");	// Content-Type : application/json (header)
		} catch (SQLException e) {
			e.printStackTrace();
			resp.getWriter().print(new Flight());	// OR empty object
			// resp.setStatus(400);
		}
	}
	
	// not SAFE
	// not idempotent
	// similar to POST
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// update your first name
		// req.getParameter("firstname");	// == Dan
		// super.doPost(req, resp);	// default is to throw 405
				InputStream requestBody = req.getInputStream();
				// convert the request body into a Trainee.class object
				Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);
				System.out.println(flight);
				try {
					dao.update(flight);
					// return the updated flight
					// resp.getWriter().print(new ObjectMapper().writeValueAsString(updated));
					// resp.setStatus(201);	// "return"
					// resp.setContentType("application/json");	// Content-Type : application/json (header)
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					// resp.getWriter().print(new Flight());	// OR empty object
					// resp.setStatus(400);
				}
	}
	
	// not idempotent
	// similiar to get
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// delete record is 1
		// if(req.getParameter("sequence_no") != null) {
			String param = req.getParameter("sequence_no");
			int sequence_no = Integer.parseInt(param);
			System.out.println("Sequence is " + sequence_no);
			try {
			dao.delete(sequence_no);	// JDBC
			// String json = new ObjectMapper().writeValueAsString(flight); // converting Java obj -> JSON
			// System.out.println(json);
			// resp.getWriter().print(json); // write the data to the response
			//}else {
			//	Set<Flight> flight = dao.findAll();
			//	String json = new ObjectMapper().writeValueAsString(flight);
			//	resp.getWriter().print(json);
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
	}
	
	

}
