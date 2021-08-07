package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.skillstorm.beans.Flight;

// Data Access Object: D.A.O. Dows
// separate algorithmic/brains from low-level data access
// cohesion: each class has one job, does it we;;
public class FlightDAO {
	// public interface FlightDAO{}	public class FlightDAOImpl{}
	private final static String url = "jdbc:mysql://localhost:3306/flight_api01";
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
	
	// CRUD: 
	// Create, Retrieve, Update, Delete
	
// ******************************************************************************CREATE*************************************************************************************************	
	public Flight create(Flight flight) throws SQLException{
		// try-with resources Java 7+, any "resources" that implement Auto-closable interface
			 try(Connection conn = DriverManager.getConnection(url, username, password)){
					String sql = "insert into flight_api (airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate) "
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
	
	public void withoutTryWith(Flight flight) {
		// try-with resources Java 7+, any "resources" that implement Auto-closable interface
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url, username, password);
			String sql = "insert into flight_api (airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate) "
					+ "values (?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			// stmt.setInt(1,  flight.getSequence_no());
			stmt.setString(2, flight.getAirline());
			stmt.setInt(3, flight.getFlight_no());
			stmt.setString(4, flight.getOrigin());
			stmt.setString(5, flight.getDestination());
			stmt.setString(6, flight.getDeparture_time());
			stmt.setString(7, flight.getArrival_time());
			stmt.setString(8, flight.getStatus_flight());
			stmt.setString(9, flight.getTerm_gate());
			// precompiles the SQL within Java, sends compiled code to MySQL
			stmt.executeUpdate();
		}catch(SQLException e)	{
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)
					conn.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
	}

// ******************************************************************************RETRIEVE*************************************************************************************************	

	public Set<Flight> findAll(){
		Set<Flight> results = new TreeSet<>();
		// create connection
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			// write the sql
			String sql = "select sequence_no, airline, flight_no, origin, destination, "
					+ "departure_time, arrival_time, status_flight, term_gate from flight_api order by sequence_no desc;";
			// create a prepared statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			// bind values to the parameters in the query (optional)
			// execute query and loop thru the ResultSet
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int sequence_no = rs.getInt("sequence_no");
				String airline = rs.getString("airline");
				int flight_no = rs.getInt("flight_no");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				String departure_time = rs.getString("departure_time");
				String arrival_time = rs.getString("arrival_time");
				String status_flight = rs.getString("status_flight");
				String term_gate = rs.getString("term_gate");
				// object-relational mapping (Hibernate, Spring Data JPA, JPA, iBatis)
				Flight flight = new Flight(sequence_no, airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate);
				results.add(flight);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}
	
	public Flight findBySequence_no(int sequence_no) {
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
	
	public Set<Flight> findByAirline(String givenAirline){
		Set<Flight> results = new HashSet<>();
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "select sequence_no, airline, flight_no, origin, destination, "
					+ "departure_time, arrival_time, status_flight, term_gate from flight_api where airline = ? order by sequence_no;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, givenAirline);
			stmt.executeQuery();
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int sequence_no = rs.getInt("sequence_no");
				String airline = rs.getString("airline");
				int flight_no = rs.getInt("flight_no");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				String departure_time = rs.getString("departure_time");
				String arrival_time = rs.getString("arrival_time");
				String status_flight = rs.getString("status_flight");
				String term_gate = rs.getString("term_gate");
				// object-relational mapping (Hibernate, Spring Data JPA, JPA, iBatis)
				Flight flight = new Flight(sequence_no, airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate);
				results.add(flight);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public Set<Flight> findByOrigin(String givenOrigin) {
		Set<Flight> results = new HashSet<>();
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "select sequence_no, airline, flight_no, origin, destination, "
					+ "departure_time, arrival_time, status_flight, term_gate from flight_api where origin = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, givenOrigin);
			stmt.executeQuery();
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int sequence_no = rs.getInt("sequence_no");
				String airline = rs.getString("airline");
				int flight_no = rs.getInt("flight_no");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				String departure_time = rs.getString("departure_time");
				String arrival_time = rs.getString("arrival_time");
				String status_flight = rs.getString("status_flight");
				String term_gate = rs.getString("term_gate");
				// object-relational mapping (Hibernate, Spring Data JPA, JPA, iBatis)
				Flight flight = new Flight(sequence_no, airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate);
				results.add(flight);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
		
	}
	
	public Set<Flight> findByDestination(String givenDestination) {
		Set<Flight> results = new HashSet<>();
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "select sequence_no, airline, flight_no, origin, destination, "
					+ "departure_time, arrival_time, status_flight, term_gate from flight_api where destination = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, givenDestination);
			stmt.executeQuery();
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int sequence_no = rs.getInt("sequence_no");
				String airline = rs.getString("airline");
				int flight_no = rs.getInt("flight_no");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				String departure_time = rs.getString("departure_time");
				String arrival_time = rs.getString("arrival_time");
				String status_flight = rs.getString("status_flight");
				String term_gate = rs.getString("term_gate");
				// object-relational mapping (Hibernate, Spring Data JPA, JPA, iBatis)
				Flight flight = new Flight(sequence_no, airline, flight_no, origin, destination, departure_time, arrival_time, status_flight, term_gate);
				results.add(flight);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	
// ******************************************************************************UPDATE*************************************************************************************************	
	//											|V was not here / to resolve try/catch error
	public void update(Flight flight) throws ClassNotFoundException{
		// try-with resources Java 7+, any "resources" that implement Auto-closable interface
		 try(Connection conn = DriverManager.getConnection(url, username, password)){
				String sql = "update flight_api set airline = ?, flight_no = ?, origin = ?, destination = ?, "
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
// ******************************************************************************DELETE by sequence_no*************************************************************************************************	
	
	public void delete(int sequence_no) throws ClassNotFoundException, SQLException{
		// try-with resources Java 7+, any "resources" that implement Auto-closable interface
				 try(Connection conn = DriverManager.getConnection(url, username, password)){
						String sql = "delete from flight_api where sequence_no = ?;";
						PreparedStatement stmt = conn.prepareStatement(sql);	// generated keys
						stmt.setInt(1,  sequence_no);
						stmt.executeUpdate();
				 }catch(SQLException e)	{
						e.printStackTrace();
				 }
	}
	
	
	

	
	
	


}
