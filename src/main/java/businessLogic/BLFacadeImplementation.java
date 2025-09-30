package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.*;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    @WebMethod public User login(String email, String password) {
    	dbManager.open();
    	User us = dbManager.login(email,password);
    	dbManager.close();
    	return us;
    }
    
    @WebMethod public User createUser(String email, String username, String password, double money, boolean type) {
    	dbManager.open();
    	User ema = dbManager.createUser(email, username, password, money,type);
    	dbManager.close();
    	return ema;
    }
    
    @WebMethod public void deleteUser(String email) {
    	dbManager.open();
    	dbManager.deleteUser(email);
    	dbManager.close();
    }
    
    @WebMethod public boolean book(int ridenumber, String email) {
    	dbManager.open();
    	boolean ema = dbManager.book(ridenumber, email);
    	dbManager.close();
    	return ema;
    }
    
    
    @WebMethod public void removeMoney(String email, double money, String s, Booking b) {
    	dbManager.open();
    	dbManager.removeMoney(email,money,s, b);
    	dbManager.close();
    }
    
    @WebMethod public void addMoney(String email, double money, String s, Booking b) {
    	dbManager.open();
    	dbManager.addMoney(email,money, s, b);
    	dbManager.close();
    }
    
    @WebMethod public List<MoneyTransaction> getTransactions(User user){
    	dbManager.open();
    	List<MoneyTransaction> mt = dbManager.getTransactions(user);
    	dbManager.close();
    	return mt;
    }
    
    @WebMethod public List<Ride> getAllRides(Driver d){
    	dbManager.open();
    	List<Ride> ema = dbManager.getAllRides(d);
    	dbManager.close();
    	return ema;
    }
    
    @WebMethod public void acceptBooking(int bookingId) {
    	dbManager.open();
    	dbManager.acceptBooking(bookingId);
    	dbManager.close();
    }
    
    @WebMethod public void rejectBooking(int bookingId) {
    	dbManager.open();
    	dbManager.rejectBooking(bookingId);
    	dbManager.close();
    }
    
    @WebMethod public void removeRide(int rideNumber) {
    	dbManager.open();
    	dbManager.removeRide(rideNumber);
    	dbManager.close();
    }
    
    @WebMethod public void addValoration(int bookingId, boolean done, int val, String com, String email) {
    	dbManager.open();
    	dbManager.addValoration(bookingId, done, val, com, email);
    	dbManager.close();
    }
    
    @WebMethod public List<Valoration> getValorations(String email){
    	dbManager.open();
    	List<Valoration> valList = dbManager.getValorations(email);
    	dbManager.close();
    	return valList;
    }
    
    @WebMethod public List<Booking> getBookings(String email){
    	dbManager.open();
    	List<Booking> ema = dbManager.getBookings(email);
    	dbManager.close();
    	return ema;
    }
    
    @WebMethod public void priorityReservation(int rideId) {
    	dbManager.open();
    	dbManager.priorityReservation(rideId);
    	dbManager.close();
    }
    
    @WebMethod public int generateNotification(String email) {
    	dbManager.open();
    	int kant = dbManager.generateNotification(email);
    	dbManager.close();
    	return kant;
    }
    
    @WebMethod public List<Notification> getNotifications(String email){
    	dbManager.open();
    	List<Notification> n = dbManager.getNotifications(email);
    	dbManager.close();
    	return n;
    }
    
    @WebMethod public Reclamation getNextReclamation() {
    	dbManager.open();
    	Reclamation r = dbManager.getNextReclamation();
    	dbManager.close();
    	return r;
    }
    
    @WebMethod public void completeReclamation(int reclamationId, String pemail,String demail, boolean ok) {
    	dbManager.open();
    	dbManager.completeReclamation(reclamationId, pemail, demail, ok);
    	dbManager.open();
    }
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, Car car, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, car, driverEmail);		
		dbManager.close();
		return ride;
   };
   
   @WebMethod public void createRequest(String email, String or, String de, int val, int kant, Date date, boolean autoBuy) {
	   dbManager.open();
	   dbManager.createRequest(email, or, de, val, kant, date, autoBuy);
	   dbManager.close();
   }
   
   @WebMethod public void deleteRequest(int requestId) {
	   dbManager.open();
	   dbManager.deleteRequest(requestId);
	   dbManager.close();
   }
   
   @WebMethod public void deleteNotification(int notificationId) {
	   dbManager.open();
	   dbManager.deleteNotification(notificationId);
	   dbManager.close();
   }
   
   @WebMethod public List<Request> getRequest(String email){
	   dbManager.open();
	   List<Request> r = dbManager.getRequest(email);
	   dbManager.close();
	   return r;
   }
   
   @WebMethod public void createReclamation(int bookingId, String comment) {
	   dbManager.open();
	   dbManager.createReclamation(bookingId, comment);
	   dbManager.close();
   }
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}
	
	@WebMethod
	public Car addCar(String matricula, int nplaces, String email) {
		dbManager.open();
		Car c = dbManager.addCar(matricula, nplaces, email);
		dbManager.close();
		return c;
	}
	
	
	@WebMethod
	public List<Car> getCars(String email){
		dbManager.open();
		List<Car> cars=dbManager.getCars(email);
		dbManager.close();
		return cars;
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

}

