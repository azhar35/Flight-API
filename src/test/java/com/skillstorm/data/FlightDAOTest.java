package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import com.skillstorm.beans.Flight;

public class FlightDAOTest {
	
	private final static String url = "jdbc:mysql://localhost:3306/test_database";
	private final static String username = "root";	
	private final static String password = "root";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Something bad happened while loading the driver");
			e.printStackTrace();
		}
	}

	// (upto us - how to execute SQL code on JDBC)
	// before
	// execute the SQL script
	
	// after
	// drop the table
	
	public Flight create(Flight flight) throws SQLException{
		// try-with resources Java 7+, any "resources" that implement Auto-closable interface
			 try(Connection conn = DriverManager.getConnection(url, username, password)){
					String sql = "insert into flight (airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate) "
							+ "values (?,?,?,?,?,?,?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	// generated keys
					// stmt.setInt(1,  flight.getSequence_no());
					stmt.setString(1, flight.getAirline());
					stmt.setInt(2, flight.getFlight_no());
					stmt.setString(3, flight.getOrigin());
					stmt.setString(4, flight.getDestination());
					stmt.setString(5, flight.getDeparture_time());
					stmt.setString(6, flight.getArrival_time());
					stmt.setString(7, flight.getStatus_flight());
					stmt.setString(8, flight.getTerm_gate());
					// precompiles the SQL within Java, sends compiled code to MySQL
					stmt.executeUpdate();	//	key is generated as the row is inserted
					
					ResultSet keys = stmt.getGeneratedKeys();
					keys.next();	// returns 1 row
					int sequence_no = keys.getInt(1);	// generated primary key
					flight.setSequence_no(sequence_no);	// sequence no is updated in Java
					
					
				}catch(SQLException e)	{
					e.printStackTrace();
					// re-throw

				} // implied finally {conn.close();}
			 return flight;	
	}
	
	// retrieve test 
	public Flight retrieve(int sequence_no) {
		// auto-closable
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "select sequence_no, airline, flight_no, origin, destination, "
					+ "departure_time, arrival_time, status_flight, term_gate from flight_api where sequence_no = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, sequence_no);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return new Flight(rs.getInt("sequence_no"), rs.getString("airline"), rs.getInt("flight_no"), rs.getString("origin"), rs.getString("destination"), 
						rs.getString("departure_time"), rs.getString("arrival_time"), rs.getString("status_flight"), rs.getString("term_gate"));
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
	}
	
	public void update(Flight flight) throws ClassNotFoundException{
		// try-with resources Java 7+, any "resources" that implement Auto-closable interface
		 try(Connection conn = DriverManager.getConnection(url, username, password)){
				String sql = "update flight set airline = ?, flight_no = ?, origin = ?, destination = ?, "
						+ "departure_time = ?, arrival_time = ?, status_flight = ?, term_gate = ? where sequence_no = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);	// generated keys
				// stmt.setInt(1,  flight.getSequence_no());
				stmt.setInt(9,  flight.getSequence_no());
				stmt.setString(1, flight.getAirline());
				stmt.setInt(2, flight.getFlight_no());
				stmt.setString(3, flight.getOrigin());
				stmt.setString(4, flight.getDestination());
				stmt.setString(5, flight.getDeparture_time());
				stmt.setString(6, flight.getArrival_time());
				stmt.setString(7, flight.getStatus_flight());
				stmt.setString(8, flight.getTerm_gate());
				// Execute
				stmt.executeUpdate();
		 }catch(SQLException e)	{
				e.printStackTrace();
		 }
	}
	
	public void delete(int sequence_no) throws ClassNotFoundException, SQLException{
		// try-with resources Java 7+, any "resources" that implement Auto-closable interface
				 try(Connection conn = DriverManager.getConnection(url, username, password)){
						String sql = "delete from flight where sequence_no = ?;";
						PreparedStatement stmt = conn.prepareStatement(sql);	// generated keys
						stmt.setInt(1,  sequence_no);
						stmt.executeUpdate();
				 }catch(SQLException e)	{
						e.printStackTrace();
				 }
	}

	public Flight update(int i, String string, int j, String string2, String string3, String string4, String string5,
			String string6, String string7) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
