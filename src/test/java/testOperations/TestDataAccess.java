package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.*;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String driverEmail) {
		System.out.println(">> TestDataAccess: removeRide");
		Driver d = db.find(Driver.class, driverEmail);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public boolean removePassenger(String passengerEmail) {
		System.out.println(">> TestDataAccess: removeRide");
		Passenger p = db.find(Passenger.class, passengerEmail);
		if (p!=null) {
			db.getTransaction().begin();
			db.remove(p);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public boolean removeAdmin(String adminEmail) {
		System.out.println(">> TestDataAccess: removeRide");
		Admin a = db.find(Admin.class, adminEmail);
		if (a!=null) {
			db.getTransaction().begin();
			db.remove(a);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Driver createDriver(String email, String name) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(email, name, "1234", 0.0);
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return driver;
    }
	public boolean existPassenger(String email) {
		 return  db.find(Passenger.class, email)!=null;
		 
	}
	public boolean existAdmin(String email) {
		 return  db.find(Driver.class, email)!=null;
		 
	}
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 
	}
		
		public Driver addDriverWithRide(String email, String name, String from, String to,  Date date, int nPlaces, float price) {
			System.out.println(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, email);
					if (driver==null)
						driver=new Driver(email, name, "1234", 0.0);
				    driver.addRide(from, to, date, nPlaces, price, null);
				    db.getTransaction().commit();
					return driver;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return null;
	    }
		
		//PRUEBA
		public User createUser(String email, String username, String password, double money, boolean type) {
			User us;
			if(type) {
				us = new Driver(email, username, password, money);
			}else {
				us = new Passenger(email, username, password, money);
			}
			if (! existDriver(us.getEmail()) && ! existPassenger(us.getEmail())) {
				db.getTransaction().begin();
				db.persist(us);
				db.getTransaction().commit();
				return us;
			}
			return null;
		}
		
		public Admin addAdmin(String email, String adminName, String password, double money) {
			Admin ad = new Admin(email, adminName, password, money);
			if (! existAdmin(ad.getEmail())) {
				db.getTransaction().begin();
				db.persist(ad);
				db.getTransaction().commit();
				return ad;
			}
			return null;
		}	
		
		
		public boolean existRide(String email, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				return d.doesRideExists(from, to, date);
			} else 
			return false;
		}
		public Ride removeRide(String email, String from, String to, Date date ) {
			System.out.println(">> TestDataAccess: removeRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				db.getTransaction().begin();
				Ride r= d.removeRide(from, to, date);
				db.getTransaction().commit();
				return r;

			} else 
			return null;

		}

		public Car addCar(String matricula, int nplaces, String email) {
			db.getTransaction().begin();
			Driver driver = db.find(Driver.class, email);
			if (driver == null) {
				System.out.println("⚠️ No se encontró el driver con email: " + email);
				db.getTransaction().rollback();
				return null;
			}
			Car car = driver.addCar(matricula, nplaces);
			db.persist(car);
			db.getTransaction().commit();
			return car;
		}
		
		public Ride addRide(String from, String to, Date date, int nPlaces, float price, String driverEmail, Car car) {
			db.getTransaction().begin();
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver == null) {
				System.out.println("⚠️ No se encontró el driver con email: " + driverEmail);
				db.getTransaction().rollback();
				return null;
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price, car);
			db.persist(ride);
			db.getTransaction().commit();
			return ride;
		}
		
		public Booking addBooking(int rideNumber, String passengerEmail) {
			db.getTransaction().begin();
			Ride ride = db.find(Ride.class, rideNumber);
			Passenger passenger = db.find(Passenger.class, passengerEmail);

			if (ride == null || passenger == null) {
				System.out.println("⚠️ Ride o Passenger no encontrados.");
				db.getTransaction().rollback();
				return null;
			}

			Booking booking = new Booking(ride, passenger);
			ride.addBooking(booking);
			passenger.addBooking(booking);
			db.persist(booking);
			db.getTransaction().commit();
			return booking;
		}
		public MoneyTransaction addMoneyTransaction(double amount, boolean action, Date date, String userEmail, String reason, Booking booking) {
		    EntityTransaction tx = db.getTransaction();
		    try {
		        tx.begin();
		        User user = db.find(User.class, userEmail);
		        if (user == null) {
		            tx.rollback();
		            throw new IllegalArgumentException("Usuario no encontrado: " + userEmail);
		        }

		        Booking managedBooking = null;
		        if (booking != null) {
		            managedBooking = db.find(Booking.class, booking.getBookingId());
		            if (managedBooking == null) {
		                managedBooking = null;
		            }
		        }
		        MoneyTransaction mt;
		        if (managedBooking != null) {
		            mt = new MoneyTransaction(amount, action, date, user, reason, managedBooking);
		        } else {
		            mt = new MoneyTransaction(amount, action, date, user, reason);
		        }
		        db.persist(mt);
		        tx.commit();
		        return mt;
		    } catch (RuntimeException e) {
		        if (tx != null && tx.isActive()) tx.rollback();
		        e.printStackTrace();
		        return null;
		    }
		}
		
		public Booking addBookingWithoutRide(String passengerEmail) {
		    EntityTransaction tx = db.getTransaction();
		    try {
		        tx.begin();

		        Passenger passenger = db.find(Passenger.class, passengerEmail);
		        if (passenger == null) {
		            tx.rollback();
		            throw new IllegalArgumentException("Pasajero no encontrado: " + passengerEmail);
		        }
		        Booking booking = new Booking(null, passenger);

		        db.persist(booking);
		        tx.commit();

		        return booking;
		    } catch (RuntimeException e) {
		        if (tx != null && tx.isActive()) tx.rollback();
		        e.printStackTrace();
		        return null;
		    }
		}
		
}


