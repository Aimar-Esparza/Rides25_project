 package domain;

import java.io.*;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


//@SuppressWarnings("serial")
//@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class Request implements Serializable {
	//@Id 
	//@GeneratedValue
	//@XmlJavaTypeAdapter(IntegerAdapter.class)
	//@XmlID
	private Integer RequestId;
	private String origin;
	private String destination;
	private int kant;
	//@XmlIDREF
	private Passenger passenger;
	private int valoration;
	private Date rideDate;
	private Date requestDate;
	private boolean autoBuy;
	
	public Request() {
		super();
	}
	
	public Request(Passenger p, String Origin, String Destination, int valoration, int kant, Date rideDate, Date requestDate, boolean autoBuy) {
		this.passenger = p;
		this.origin = Origin;
		this.destination = Destination;
		this.valoration = valoration;
		this.kant = kant;
		this.rideDate = rideDate;
		this.requestDate = requestDate;
		this.autoBuy = autoBuy;
	}
	
	public boolean isAutoBuy() {
		return autoBuy;
	}

	public void setAutoBuy(boolean autoBuy) {
		this.autoBuy = autoBuy;
	}
 
	public Integer getRequestId() {
		return RequestId;
	}

	public void setRequestId(Integer requestId) {
		RequestId = requestId;
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

	public int getKant() {
		return kant;
	}

	public void setKant(int kant) {
		this.kant = kant;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public int getValoration() {
		return valoration;
	}

	public void setValoration(int valoration) {
		this.valoration = valoration;
	}

	public Date getRideDate() {
		return rideDate;
	}

	public void setRideDate(Date ride_date) {
		this.rideDate = ride_date;
	}

	public Date getRequest_date() {
		return requestDate;
	}

	public void setRequest_date(Date request_date) {
		this.requestDate = request_date;
	}

	public String toString(){
		return origin+";"+destination+";" + "kant" +";" + rideDate+";" + requestDate + ";";  
	}
	
	
	
}

