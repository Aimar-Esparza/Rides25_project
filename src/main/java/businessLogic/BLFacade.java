package businessLogic;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.*;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	
	@WebMethod public User login(String email, String password);
	
	@WebMethod public User createUser(String email, String username, String password, double money, boolean type);
	
	@WebMethod public void deleteUser(String email);
	
	@WebMethod public boolean book(int ridenumber, String email);
	
	@WebMethod public void removeMoney(String email, double money, String s, Booking b);
	
	@WebMethod public void addMoney(String email, double money, String s, Booking b);
	
	@WebMethod public List<MoneyTransaction> getTransactions(User user);
	
	@WebMethod public List<Ride> getAllRides(Driver d);
	
	@WebMethod public void acceptBooking(int bookingId);
	
	@WebMethod public void rejectBooking(int bookingId);
	
	@WebMethod public void removeRide(int rideNumber);
	
	@WebMethod public void addValoration(int bookingId, boolean done, int val, String com, String email);
	
	@WebMethod public List<Valoration> getValorations(String email);
	
	@WebMethod public List<Booking> getBookings(String email);
	
	@WebMethod public void priorityReservation(int rideId);
	
	@WebMethod public int generateNotification(String email);
	
	@WebMethod public List<Notification> getNotifications(String email);
	
	@WebMethod public void createRequest(String email, String or, String de, int val, int kant, Date date, boolean autoBuy);
	
	@WebMethod public void deleteRequest(int requestId);
	
	@WebMethod public List<Request> getRequest(String email);
	
	@WebMethod public Car addCar(String matricula, int nplaces, String email);
	
	@WebMethod public List<Car> getCars(String email);
	
	@WebMethod public void deleteNotification(int notificationId);
	
	@WebMethod public void createReclamation(int bookingId, String comment);
	
	@WebMethod public Reclamation getNextReclamation();
	
	@WebMethod public void completeReclamation(int reclamationId, String pemail,String demail, boolean ok);
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, Car car, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	
}
