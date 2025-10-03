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
public class Driver extends User implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Car> cars=new Vector<Car>();
	private double val;
	private int kantVal;

	public Driver() {
		super();
	}

	public Driver(String email, String username, String password, double money) {
		super(email, username, password, money);
		this.val = 0;
		this.kantVal = 0;
	}
	
	public List<Ride> getRides(){
		return rides;
	}
	
	public List<Car> getCars(){
		return cars;
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
	
	public Car addCar(Car car) {
		if (!cars.contains(car)){
			cars.add(car);
			return car;
		}else {
			return null;
		}
		
	}

	public Car addCar(String matricula, int nplaces) {
		Car car = new Car(matricula, nplaces, this);
		Car c = addCar(car);
		return c;
	}
	
	public String toString(){
		return super.getEmail() + " ; " + super.getUsername() + " ; " + val;
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price, Car car)  {
		if (cars.contains(car)){
			Ride ride=new Ride(from,to,date,nPlaces,price, this, car);
			rides.add(ride);
			return ride;
		}
		return null;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public void removeRide(Ride r) {
		rides.remove(r);
	}
	
	public Ride removeRide(String from, String to, Date date) {
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return r;
		
		return null;
	}
	
	public void removeCar(Car c) {
		cars.remove(c);
	}
	
}
