package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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


		
}


