package com.skillstorm.beans;

import java.util.Objects;

// MODEL
public class Flight implements Comparable <Flight>{
	
	private int sequence_no;
	private String airline;
	private int flight_no;
	private String origin;
	private String destination;
	private String departure_time;
	private String arrival_time;
	private String status_flight;
	private String term_gate;
	
	// always
	public Flight() {
		super();
	}

	public Flight(int sequence_no, String airline, int flight_no, String origin, String destination,
			String departure_time, String arrival_time, String status_flight, String term_gate) {
		super();
		this.sequence_no = sequence_no;
		this.airline = airline;
		this.flight_no = flight_no;
		this.origin = origin;
		this.destination = destination;
		this.departure_time = departure_time;
		this.arrival_time = arrival_time;
		this.status_flight = status_flight;
		this.term_gate = term_gate;
	}

	public Flight(String airline, int flight_no, String origin, String destination, String departure_time,
			String arrival_time, String status_flight, String term_gate) {
		super();
		this.airline = airline;
		this.flight_no = flight_no;
		this.origin = origin;
		this.destination = destination;
		this.departure_time = departure_time;
		this.arrival_time = arrival_time;
		this.status_flight = status_flight;
		this.term_gate = term_gate;
	}

	public int getSequence_no() {
		return sequence_no;
	}

	public void setSequence_no(int sequence_no) {
		this.sequence_no = sequence_no;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public int getFlight_no() {
		return flight_no;
	}

	public void setFlight_no(int flight_no) {
		this.flight_no = flight_no;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	public String getStatus_flight() {
		return status_flight;
	}

	public void setStatus_flight(String status_flight) {
		this.status_flight = status_flight;
	}

	public String getTerm_gate() {
		return term_gate;
	}

	public void setTerm_gate(String term_gate) {
		this.term_gate = term_gate;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(airline, arrival_time, departure_time, destination, flight_no, origin, sequence_no,
				status_flight, term_gate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(airline, other.airline) && Objects.equals(arrival_time, other.arrival_time)
				&& Objects.equals(departure_time, other.departure_time)
				&& Objects.equals(destination, other.destination) && flight_no == other.flight_no
				&& Objects.equals(origin, other.origin) && sequence_no == other.sequence_no
				&& Objects.equals(status_flight, other.status_flight) && Objects.equals(term_gate, other.term_gate);
	}

	@Override
	public String toString() {
		return "Flight [sequence_no=" + sequence_no + ", airline=" + airline + ", flight_no=" + flight_no + ", origin="
				+ origin + ", destination=" + destination + ", departure_time=" + departure_time + ", arrival_time="
				+ arrival_time + ", status_flight=" + status_flight + ", term_gate=" + term_gate + "]";
	}

	@Override
	public int compareTo(Flight other) {
		return this.sequence_no - other.sequence_no;
	}

	
	
}
