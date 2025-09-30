package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Car implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id 
	@XmlID
	private String numberPlate
	;//numberPlate
	private int nplaces;
	@ManyToOne
	private Driver driver;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();
	
	public Car(String matricula, int nplaces, Driver d) {
		this.numberPlate = matricula;
		this.nplaces = nplaces;
		this.driver = d;
	}
	
	public Car() {
		super();
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String matricula) {
		this.numberPlate = matricula;
	}

	public int getNplaces() {
		return nplaces;
	}

	public void setNplaces(int nplaces) {
		this.nplaces = nplaces;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}	
	
	public void removeRide(Ride r) {
		rides.remove(r);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(driver, numberPlate, nplaces);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		return Objects.equals(numberPlate, other.numberPlate) && nplaces == other.nplaces;
	}

	public String toString() {
		return numberPlate;
	}
}
