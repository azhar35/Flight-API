package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.skillstorm.beans.Flight;

import java.sql.Statement;

import static org.junit.Assert.*;

public class DatatableTest {
	// *** test user will have DBA privileges
	// in production: follow least privilege
	private final static String url = "jdbc:mysql://localhost:3306/test_database";
	private final static String username = "root";	
	private final static String password = "root";
	
	FlightDAOTest dao = new FlightDAOTest();
	
	@Before
	public void beforeTest() {
		// create table DDL
		try {
		String ddl = "create table `test_database`.`flight` (`sequence_no` INT AUTO_INCREMENT, `airline` VARCHAR(50) NOT NULL, "
				+ "`flight_no` VARCHAR(50) NOT NULL, `origin` VARCHAR(50) NOT NULL, `destination` VARCHAR(50) NOT NULL, "
				+ "`departure_time` VARCHAR(50) NOT NULL, `arrival_time` VARCHAR(50) NOT NULL, `status_flight` VARCHAR(50) NOT NULL, "
				+ "`term_gate` VARCHAR(50), PRIMARY KEY (`sequence_no`));";
		Connection conn = DriverManager.getConnection(url, username, password);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(ddl);
		System.out.println("Test table created");
		System.out.println();
		conn.close();
		}catch(Exception e){
			fail();
			
		}
	}
	
	@Test
	public void createTest() {
		try {
			String sql = "select count(*) from flight";
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery(sql);
			rs1.next();
			int row1 = rs1.getInt(1);
			
			dao.create(new Flight("AA", 2190, "ORY", "EWR", "12:30", "09:15", "On Time", "TBD"));
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql);
			rs2.next();
			int row2 = rs2.getInt(1);
			
			System.out.println("TEST 1: Create a new row to table");
			System.out.println();
			Thread.sleep(2_000);	// _ for comma t in ms
			
			assertEquals(row2, ++row1);
		} catch (Exception e) {
			fail();
		}
	}
	/*
	 * Template
	 * 
	@Test
	public void test() {
		try {
			String sql = "select count(*) from flight";
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int rows = rs.getInt(1);
			
		} catch (Exception e) {
			fail();
		}
	}
	*/
	
	/*
	 * Transient tests: fresh data set for each test
	 * 
	 * 
	 * */
	
	@Test
	public void deleteTest() {
		try {
			
			dao.create(new Flight("AA", 2190, "ORY", "EWR", "12:30", "09:15", "On Time", "TBD"));
			dao.create(new Flight("AB", 2190, "ORY", "EWR", "12:30", "09:15", "On Time", "TBD"));
			
			String sql = "select count(*) from flight";
			
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet rs1 = stmt.executeQuery(sql);
			rs1.next();
			int row1 = rs1.getInt(1);
			
			dao.delete(2);
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql);
			rs2.next();
			
			int row2 = rs2.getInt(1);
			System.out.println("TEST 2: Delete a row from table");
			System.out.println();
			Thread.sleep(2_000);	// _ for comma t in ms
			
			assertEquals(row2, (row1 - 1));
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@After
	public void afterTest() {
		
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("drop table flight");
			System.out.println("Test table dropped");
			System.out.println();
			conn.close();
		} catch (Exception e) {
			fail();
		}
		
	}

}
