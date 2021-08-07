package com.skillstorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.skillstorm.beans.Flight;

public class FlightJdbc {

	// jdbc:mysql://host_name:port_number/schema_name
	static String url = "jdbc:mysql://localhost:3306/flight_api01";
	static String username = "root";	
	static String password = "root";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Flight flight = new Flight(25, "DY", 6607, "FCO", "LGA","05:29", "14:20", "On Air", "TBD");
		createFlight(flight);
	}
	// int sequence_no, String airline, int flight_no, String origin, String destination, String departure_time, String arrival_time, String status_flight, String term_gate
	public static void createFlight(Flight flight) 
				throws ClassNotFoundException, SQLException {
		// 1. Load the driver (force the class to be loaded into the memory)
		// JDBC 3.0+ drivers self-bootstrapping...
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// 2. Make a Connection object
		Connection conn = DriverManager.getConnection(url, username, password);
		
		// 3. Statement object
		String sql = "insert into flight_api (sequence_no, airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate) "
				+ "values ("+flight.getSequence_no()+",'"+flight.getAirline()+"',"+flight.getFlight_no()+",'"+flight.getOrigin()+"','"+flight.getDestination()+"',"
						+ "'"+flight.getDeparture_time()+"','"+flight.getArrival_time()+"','"+flight.getStatus_flight()+"','"+flight.getTerm_gate()+"')";
		Statement stmt = conn.createStatement();
		// sending a raw string to MySQL, database compile it
		
		// 4. Execute the SQL commands
		stmt.executeUpdate(sql);
		
		// 5. Close the connection
		conn.close();
	}
}
