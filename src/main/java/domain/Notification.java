 package domain;

import java.io.*;
import java.util.Date;
import java.util.ResourceBundle;

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
public class Notification implements Serializable {
	@Id 
	@GeneratedValue
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	private Integer notificationId;
	private char reason;
	@ManyToOne
	private Passenger passenger;
	@XmlIDREF
	private Ride ride;
	private Date date;
	
	public Notification() {
		super();
	}
	
	public Notification(Ride ride, Passenger passenger, char reason) {
		this.passenger = passenger;
		this.reason = reason;
		this.ride = ride;
		this.date = new Date();
	}
	
	

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public char getComment() {
		return reason;
	}

	public void setComment(char reason) {
		this.reason = reason;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public String toString(){
		String r = "Notification.Reason" + reason;
		return ResourceBundle.getBundle("Etiquetas").getString(r) + " |" + ride.toString();  
	}
	
	//hay que hacerlo
	public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Notification other = (Notification) obj;
			return this.ride.equals(other.getRide()) && this.passenger.equals(other.getPassenger());
	}
	
}
