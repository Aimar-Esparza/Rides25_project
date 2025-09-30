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


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Booking implements Serializable {
	@Id 
	@GeneratedValue
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	private Integer bookingId;
	@ManyToOne
	private Ride ride;
	@ManyToOne
	private Passenger passenger;
	private Boolean status; // false pendiente //True aceptado
	@XmlIDREF
	private Valoration driverValoration;
	@XmlIDREF
	private Valoration passengerValoration;
	@XmlIDREF
	private Reclamation reclamation;
	
	public Booking() {
		super();
	}
	
	public Booking(Ride r, Passenger p) {
		this.ride = r;
		this.passenger = p;
		this.status = false;
		this.driverValoration = null;
		this.passengerValoration = null;
	}
	
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}
	
	public Driver getDriver() {
		return ride.getDriver();
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	public boolean isAccept() {
		return status;
	}
	
	public void accept() {
		this.status = true;
	}
	
	public void reject() {
		this.status = false;
	}

	public Valoration getDriverValoration() {
		return driverValoration;
	}

	public void setDriverValoration(Valoration valoration) {
		if(this.driverValoration == null) {
			this.driverValoration = valoration;
		}
	}
	
	public Valoration getPassengerValoration() {
		return passengerValoration;
	}

	public void setPassengerValoration(Valoration valoration) {
		if(this.passengerValoration == null) {
			this.passengerValoration = valoration;
		}
	}

	public void setReclamation(Reclamation reclamation) {
		if(this.reclamation == null) {
			this.reclamation =  reclamation;
		}
	}
	
	public Reclamation getReclamation() {
		return this.reclamation;
	}
	
	public boolean isPassengerDone() {
		return (this.driverValoration != null);
	}
	
	public boolean isDriverDone() {
		return (this.passengerValoration != null);
	}
	
	public String toString(){
		return bookingId+";"+";"+passenger+";"+status+";";  
	}
	
	
	
}
