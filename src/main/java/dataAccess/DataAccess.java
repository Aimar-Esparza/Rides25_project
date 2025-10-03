 package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Driver;
import domain.MoneyTransaction;
import domain.Notification;
import domain.Admin;
import domain.Booking;
import domain.Car;
import domain.Passenger;
import domain.Reclamation;
import domain.Request;
import domain.Ride;
import domain.User;
import domain.Valoration;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import gui.MainDriverGUI;
import gui.MainPassengerGUI;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();

				  System.out.println("File deleted");
				} else {
				  System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized())initializeDB();
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		    //Create admin
		   Admin admin = new Admin("admin@gmail.com", "user1", "111", 9999);
		   
		    //Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez","444",999.99);
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga","555",0);
			Driver driver3=new Driver("driver3@gmail.com","Test driver","666",20.55);
			
			//Create passengers
			Passenger passenger1=new Passenger("passenger1@gmail.com","Gorka Garcia","777",100);

			
			//create cars
			Car car1 = new Car("ABCD 123", 5, driver1);
			Car car2 = new Car("EFGH 456", 7, driver1);
			Car car3 = new Car("IJKL 789", 5, driver2);
			Car car4 = new Car("MNOP 321", 8, driver3);
			Car car5 = new Car("QRST 654", 10, driver1);
			
			driver1.addCar(car1);
			driver1.addCar(car2);
			driver2.addCar(car3);
			driver3.addCar(car4);
			driver3.addCar(car5);
			
			//Create rides
			String don = "Donostia";
			String bil = "Bilbo";
			driver1.addRide(don, bil, UtilDate.newDate(year,month,15), 4, 7, car1);
			driver1.addRide(don, "Gazteiz", UtilDate.newDate(year,month,6), 4, 8, car2);
			driver1.addRide(bil, don, UtilDate.newDate(year,month,25), 4, 4, car2);

			driver1.addRide(don, "Iruña", UtilDate.newDate(year,month,7), 4, 8, car1);
			
			driver1.addRide(don, bil, UtilDate.newDate(year,month,15), 3, 3, car2);
			driver1.addRide(bil, don, UtilDate.newDate(year,month,25), 2, 5, car1);
			driver1.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5, car1);
			
			driver1.addRide(bil, don, UtilDate.newDate(year,month,14), 1, 3, car5);
					
			db.persist(admin);
			db.persist(driver1);
			db.persist(driver2);
			db.persist(driver3);
			db.persist(passenger1);
	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public User login(String email, String password) {
		User us = null;
		Driver dr = db.find(Driver.class, email);
		Passenger pa = db.find(Passenger.class, email);
		Admin ad = db.find(Admin.class, email);
		if (pa != null) {
			if (pa.getPassword().equals(password)) {
				us = pa;
			}
		}else if (dr != null){
			if (dr.getPassword().equals(password)) {
				us = dr;
			}
		}else if(ad != null) {
			if(ad.getPassword().equals(password)) {
				us = ad;
			}
		}
		return us;
	}
	
	/** 
	 * This method enters a new user if it is not there
	 */
	public User createUser(String email, String username, String password, double money, boolean type) {
		User us;
		if(type) {
			us = new Driver(email, username, password, money);
		}else {
			us = new Passenger(email, username, password, money);
		}
		if (! isDriver(us.getEmail()) && ! isPassenger(us.getEmail())) {
			db.getTransaction().begin();
			db.persist(us);
			db.getTransaction().commit();
			return us;
		}
		return null;
	}
	
	public void deleteUser(String email) {
		if(isDriver(email)) {
			deleteDriver(email);
		}else if ( isPassenger(email)) {
			deletePassenger(email);
		}
		//begiratu driver edo passenger
		//driver ezabatu bidaiak eta kotxea
		//passenger ezabatu booking-ak 
		//ezabatu 
	}
	
	private void deleteDriver(String email) {
		Driver driver = db.find(Driver.class, email);
		while (driver.getRides().size() > 0 ) {
			removeRide(driver.getRides().get(0).getRideNumber());
		}
		while (driver.getCars().size() > 0) {
			removeCar(driver.getCars().get(0).getNumberPlate());
		}
		while (driver.getTransactions().size() > 0) {
			removeTransaction(driver.getTransactions().get(0).getTransactionId());
		}
		db.getTransaction().begin();
		db.remove(driver);
		db.getTransaction().commit();
	}
	
	private void deletePassenger(String email) {
		Passenger passenger = db.find(Passenger.class, email);
		while (passenger.getBookings().size()  > 0) {
			rejectBooking(passenger.getBookings().get(0).getBookingId());
		}
		while (passenger.getTransactions().size() > 0) {
			removeTransaction(passenger.getTransactions().get(0).getTransactionId());
		}
		db.getTransaction().begin();
		db.remove(passenger);
		db.getTransaction().commit();
	}
	
	public boolean isDriver(String email) {
		Driver d = db.find(Driver.class, email);
		if(d != null) {
			return true;
		}
		return false;
	}
	
	public boolean isPassenger(String email) {
		Passenger p = db.find(Passenger.class, email);
		if(p != null) {
			return true;
		}
		return false;
	}
	
	public boolean book(int ridenumber, String email) {
		db.getTransaction().begin();
		Ride r = db.find(Ride.class, ridenumber);
		Passenger p =  db.find(Passenger.class, email);
		if (p.getMoney() >= r.getPrice() && r.getnPlaces() > 0) {
			Booking book = new Booking(r,p);
			r.addBooking(book);
			r.setBetMinimum(r.getnPlaces()-1);
			db.getTransaction().commit();
			removeMoney(p.getEmail(),r.getPrice(), "erreserba bat: " + r.toString(), book);
			return true;
		} 
		db.getTransaction().commit();
		return false;
	}
	
	public void addMoney(String email, double money, String reason, Booking b) {
		db.getTransaction().begin();
		User us = db.find(User.class, email);
		us.setMoney(us.getMoney() + money);
		addTransaction(us, money, true, new Date(),reason, b);
		db.getTransaction().commit();
		if(us instanceof Driver) {
			MainDriverGUI.setDriver((Driver)us);
		}else {
			MainPassengerGUI.setPassenger((Passenger)us);
		} 
	}

	public void removeMoney(String email, double money, String reason, Booking b) {
		db.getTransaction().begin();
		
		User us = db.find(User.class, email);
		if(us.getMoney() - money >= 0) {
			us.setMoney(us.getMoney() - money);
			addTransaction(us, money, false, new Date(),reason, b);
		}
		db.getTransaction().commit();
		if(us instanceof Driver) {
			MainDriverGUI.setDriver((Driver)us);
		}else {
			MainPassengerGUI.setPassenger((Passenger)us);
		} 
	}
	
	public MoneyTransaction addTransaction(User us, double amount, boolean action, Date date, String reason, Booking b) {
		MoneyTransaction mt = new MoneyTransaction(amount, action, date, us,reason, b);
		us.addTransaction(mt);
		return mt;
	}
	
	public List<MoneyTransaction> getTransactions(User user){
		TypedQuery<MoneyTransaction> query = db.createQuery("SELECT p FROM MoneyTransaction p Where p.user = :user", MoneyTransaction.class);
		query.setParameter("user", user);
		return query.getResultList();
	}
	
	public void removeTransaction(int transactionId) {
		db.getTransaction().begin();
		MoneyTransaction mt = db.find(MoneyTransaction.class, transactionId);
		User us = db.find(User.class, mt.getUser().getEmail());
		us.removeTransaction(mt);
		db.remove(mt);
		db.getTransaction().commit();
	}
	
	public List<Ride> getAllRides(Driver d) {
		System.out.println(">> DataAccess: getAllRides=> of "+d+"");
		String email = d.getEmail();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver.email = ?1", Ride.class);
		query.setParameter(1, email);
		
		return query.getResultList();
	}
	
	public List<Booking> getBookings(String email){
		if(isDriver(email)) {
			return getDriverBookings(db.find(Driver.class, email));
		}else if ( isPassenger(email)) {
			return getPassengerBookings(db.find(Passenger.class, email));
		}
		return null;
	}
	
	public List<Booking> getDriverBookings(Driver d){
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver = ?1", Ride.class);
		query.setParameter(1, d);
		List<Booking> bookings = new ArrayList<Booking>();
		for(Ride r : query.getResultList()) {
			TypedQuery<Booking> query1 = db.createQuery("SELECT b FROM Booking b WHERE b.ride = ?1", Booking.class);
			query1.setParameter(1, r);
			bookings.addAll(query1.getResultList());
		}
		return bookings;
	}
	
	public List<Booking> getPassengerBookings(Passenger p){
		System.out.println(">> DataAccess: getPassengerBookings=> of "+p+"");
		TypedQuery<Booking> query = db.createQuery("SELECT b FROM Booking b WHERE b.passenger = ?1", Booking.class);
		query.setParameter(1, p);
		return query.getResultList();
	}
	
	public void acceptBooking(int bookingId) {
		db.getTransaction().begin();
		Booking b = db.find(Booking.class, bookingId);
		b.accept();
		db.getTransaction().commit();
	}
	
	public void addValoration(int bookingId, boolean done, int val, String com, String email) {
		if(isDriver(email)) {
			addDriverValoration(bookingId, done, val, com);
		}else if ( isPassenger(email)) {
			addPassengerValoration(bookingId, done, val, com);
		}
	}
	
	public void addDriverValoration(int bookingId, boolean done, int val, String com) {
		db.getTransaction().begin();
		Booking booking = db.find(Booking.class, bookingId);
		Passenger p = db.find(Passenger.class, booking.getPassenger().getEmail());
		Valoration valoration = new Valoration(booking, true, val, com);
		booking.setPassengerValoration(valoration);
		db.persist(valoration);
		p.modifyVal(val);
		db.getTransaction().commit();
	}
	
	public void addPassengerValoration(int bookingId, boolean done, int val, String com) {
		db.getTransaction().begin();
		Booking booking = db.find(Booking.class, bookingId);
		Ride r = booking.getRide();
		Driver d = db.find(Driver.class, r.getDriver().getEmail());
		Valoration valoration = new Valoration(booking, done, val, com);
		booking.setDriverValoration(valoration);
		db.persist(valoration);
		d.modifyVal(val);
		db.getTransaction().commit();
		if(done) {
			addMoney(r.getDriver().getEmail(), r.getPrice(), "Ride completed", booking);
		}
	}
	
	public void deleteRequest(int requestId) {
		db.getTransaction().begin();
		Request r = db.find(Request.class, requestId);
		Passenger p = db.find(Passenger.class, r.getPassenger().getEmail());
		p.removeRequest(r);
		db.remove(r);
		db.getTransaction().commit();
	}
	
	public List<Request> getRequest(String email){
		Passenger p = db.find(Passenger.class, email);
		TypedQuery<Request> query = db.createQuery("SELECT r FROM Request r WHERE r.passenger = :passenger ORDER BY r.requestDate", Request.class);
		query.setParameter("passenger", p);
		return query.getResultList();
	}
	
	 
	
	public void priorityReservation(int ridenumber) {
		Ride ride = db.find(Ride.class, ridenumber);
		TypedQuery<Request> query = db.createQuery("SELECT r FROM Request r WHERE r.origin = :origin AND r.destination = :destination AND r.rideDate = :date ORDER BY r.requestDate", Request.class);
		query.setParameter("origin", ride.getFrom());
		query.setParameter("destination", ride.getTo());
		query.setParameter("date", ride.getDate());
		int kant = ride.getnPlaces();
		for (Request request : query.getResultList()) {
			if (request.getKant() <= kant) {
				if(request.getValoration() <= ride.getDriver().getVal()) {
					if(request.isAutoBuy()) {
						if((ride.getPrice()*request.getKant()) <= request.getPassenger().getMoney()) {
							reservate(request.getPassenger().getEmail(), ridenumber, request.getKant());
							createNotification(ride, request.getPassenger().getEmail(), 'C');
							deleteRequest(request.getRequestId());
							kant -= request.getKant();
						}else {
							createNotification(ride, request.getPassenger().getEmail(), 'N');
						}
					}
				}
			}
		}
	}
	public void reservate(String email, int ridenumber, int kant) {
		for(int i = 0; i < kant; i++){
			book(ridenumber,email);
		}
	}
	
	public void lookForAutoBuy() {
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r", Ride.class);
		for(Ride r : query.getResultList()) {
			priorityReservation(r.getRideNumber());
		}
	}
	
	public int generateNotification(String email) {
		Passenger p = db.find(Passenger.class, email);
		TypedQuery<Request> query = db.createQuery("SELECT r FROM Request r WHERE r.passenger = :passenger ORDER BY r.requestDate", Request.class);
		query.setParameter("passenger", p);
		for(Request  r : query.getResultList()) {
			if(!r.isAutoBuy()) {
				lookForNotification(r, 'N');
			}
		}
		lookForAutoBuy();
		return howManyNotifications(email);
	}
	
	private int howManyNotifications(String email) {
		Passenger p = db.find(Passenger.class, email);
		return p.getNotification().size();
	}
	
	public List<Notification> getNotifications(String email){
		Passenger p = db.find(Passenger.class, email);
		TypedQuery<Notification> query = db.createQuery("SELECT n FROM Notification n WHERE n.passenger = :p ORDER BY n.date", Notification.class);
		query.setParameter("p", p);
		return query.getResultList();
	}
	
	private void lookForNotification(Request r, char reason) {
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from = :origin AND r.to = :destination AND r.date = :date", Ride.class);
		query.setParameter("origin", r.getOrigin());
		query.setParameter("destination", r.getDestination());
		query.setParameter("date", r.getRideDate());
		query.setParameter("kant", r.getKant());
		List<Ride> rides = query.getResultList();
		boolean done = false;
		for(int i = 0; i < rides.size() && !done; i++) {
			if(rides.get(i).getDriver().getVal() >= r.getValoration() && rides.get(i).getnPlaces() >= r.getKant()) {
				createNotification(rides.get(i), r.getPassenger().getEmail(), reason);
				done = true;
			}
		}
	}
	
	private void createNotification(Ride r, String email, char reason) {
		db.getTransaction().begin();
		Passenger p = db.find(Passenger.class, email);
		Notification n = new Notification(r, p, reason);
		if(!p.hasNotification(n)) {
			p.addNotification(n);
		}
		db.getTransaction().commit();
	}
	
	public void deleteNotification(int notificationId) {
		db.getTransaction().begin();
		Notification n = db.find(Notification.class, notificationId);
		n.getPassenger().removeNotification(n);
		db.remove(n);
		db.getTransaction().commit();
	}
	
	public List<Valoration> getValorations(String email){
		List<Valoration> valList = new ArrayList<Valoration>();
		if (isDriver(email)) {
			Driver d = db.find(Driver.class, email);
			TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r where r.dirver = :driver", Ride.class);
			query.setParameter("driver", d);
			for(Ride r : query.getResultList()) {
				for (Booking b : r.getBookings()) {
					if(b.getDriverValoration() != null) {
						valList.add(b.getDriverValoration());
					}
				}
			}
			return valList;
		}else {
			Passenger p = db.find(Passenger.class, email);
			TypedQuery<Booking> query = db.createQuery("SELECT r FROM Booking r where r.passenger = :passenger", Booking.class);
			query.setParameter("passenger", p);
			for(Booking b : query.getResultList()) {
				if(b.getDriverValoration() != null) {
					valList.add(b.getDriverValoration());
				}
			}
			return valList;
		}
	}
	
	public void rejectBooking(int bookingId) {
		db.getTransaction().begin();
		Booking b = db.find(Booking.class, bookingId);
		Ride r =  b.getRide();
		r.removeBooking(b); 
		r.setBetMinimum(r.getnPlaces()+1);
		db.remove(b);
		db.getTransaction().commit();
		addMoney(b.getPassenger().getEmail(), r.getPrice(), "Booking rejected", b);
		priorityReservation(r.getRideNumber());
	} 
	
	public void removeRide(int rideNumber) {
		Ride ride = db.find(Ride.class, rideNumber);
		while (ride.getBookings().size() > 0) {
			rejectBooking(ride.getBookings().get(0).getBookingId());
		}
		db.getTransaction().begin();
		Car car = db.find(Car.class, ride.getCar().getNumberPlate());
		car.removeRide(ride);
		Driver driver = db.find(Driver.class, ride.getDriver().getEmail());
		driver.removeRide(ride);
		db.remove(ride);
		db.getTransaction().commit();
	} 
	
	 
	public void createRequest(String email, String or, String de, int val, int kant, Date ridedate, boolean autoBuy) {
		db.getTransaction().begin();
		Passenger p = db.find(Passenger.class, email);
		Request request = new Request( p,  or,  de, val, kant, ridedate, new Date(), autoBuy);
		db.persist(request);
		p.addRequest(request);
		db.getTransaction().commit();
	}
	
	public void createReclamation(int bookingId, String comment) {
		db.getTransaction().begin();
		Booking b = db.find(Booking.class, bookingId);
		Reclamation r = new Reclamation(b, comment);
		b.setReclamation(r);
		db.persist(r);
		db.getTransaction().commit();
	}
	
	public Reclamation getNextReclamation() {
		TypedQuery<Reclamation> query = db.createQuery("SELECT r FROM Reclamation r ORDER BY r.date", Reclamation.class);
		if(!query.getResultList().isEmpty()) {
			return query.getResultList().getFirst();
		}else {
			return  null;
		}
	}
	
	public void completeReclamation(int reclamationId, String pemail,String demail, boolean ok) {
		Reclamation r = db.find(Reclamation.class, reclamationId);
		if(ok) {
			addMoney(pemail, r.getRide().getPrice(), "Booking rejected", null);
			createNotification(r.getRide(), pemail, 'A');
		}else{
			addMoney(demail, r.getRide().getPrice(), "Booking done", null);
			createNotification(r.getRide(), pemail, 'R');
		}
		db.getTransaction().begin();
		db.remove(r);
		db.getTransaction().commit();
	}
	 
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
			TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			List<String> cities = query.getResultList();
			return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, Car car, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			
			TypedQuery<Driver> query = db.createQuery("SELECT p FROM Driver p WHERE p.email = :email",Driver.class);
			query.setParameter("email", driverEmail);
			Driver driver = query.getSingleResult();
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price, car);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();
			priorityReservation(ride.getRideNumber());
			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
	}
	
	public Car addCar(String matricula, int nplaces, String email) {
		db.getTransaction().begin();
		Driver driver = db.find(Driver.class, email);
		Car car = driver.addCar(matricula, nplaces);
		db.getTransaction().commit();
		return car;
	}
	
	public void removeCar(String matricula) {
		Car car = db.find(Car.class, matricula);
		while( car.getRides().size()>0) {
			removeRide(car.getRides().get(0).getRideNumber());
		}
		db.getTransaction().begin();
		Driver d = db.find(Driver.class, car.getDriver().getEmail());
		d.removeCar(car);
		db.remove(car);
		db.getTransaction().commit();
	}
	
	
	public List<Car> getCars(String email){
		TypedQuery<Car> query = db.createQuery("SELECT c FROM Driver p JOIN p.cars c WHERE p.email = :email",Car.class );
		query.setParameter("email", email);
		return query.getResultList();
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	}
	

public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
}
