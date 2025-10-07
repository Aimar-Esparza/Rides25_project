package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Passenger extends User implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Booking> booking=new Vector<Booking>();
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Request> request = new Vector<Request>();
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Notification> notifications = new Vector<Notification>();
	private double val;
	private int kantVal;
	
	public Passenger() {
		super();
	}

	public Passenger(String email, String username, String password, double money) {
		super(email, username, password, money);
		this.val = 0;
		this.kantVal = 0;
	}
	
	public List<Notification> getNotification(){
		return notifications;
	}
	
	public boolean hasNotification(Notification n) {
		return this.notifications.contains(n);
	}
	
	public void removeNotification(Notification n) {
		this.notifications.remove(n);
	}
	
	public void addNotification(Notification n) {
		notifications.add(n);
	}
	
	public List<Booking> getBookings() {
		return booking;
	}
	
	public void addRequest(Request r) {
		request.add(r);
		
	}
	public void removeRequest(Request r) {
		request.remove(r);
	}
	
	public double getVal() {
		return val;
	}
	
	public void setVal(double val) {
		this.val = val;
	}
	
	public void modifyVal(int v){
		val = (val * kantVal) + v;
		kantVal ++;
		val = val / kantVal;
	}
	
	public String toString(){
		return super.toString();
	}
		
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public void addBooking(Booking b) {
	    if (b != null && !booking.contains(b)) {
	        booking.add(b);
	    }
	}

	public void removeBooking(Booking b) {
	    booking.remove(b);
	}
	
}
