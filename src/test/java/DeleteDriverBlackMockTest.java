import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Passenger;
import domain.Request;
import domain.Driver;
import domain.MoneyTransaction;
import domain.Admin;
import domain.Booking;
import domain.Car;
import domain.User;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class DeleteDriverBlackMockTest {
	
	static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	

	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
	    
	    
    }
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	
	Driver driver;
	
	@Test
	public void test1() {
		String driveremail="driver1@gmail.com";
		String driverPassword="777";
		Calendar today = Calendar.getInstance();
		   
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=1; year+=1;}  
		
		try {
			User a;
			Driver driver=new Driver(driveremail,"",driverPassword, 0);
			Passenger passenger = new Passenger("", "", "", 0);
			Car car1 = new Car("ABCD 123", 5, driver);
			driver.addCar(car1);
			Ride r = new Ride("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7,driver, car1);
			r.setRideNumber(3);
			driver.getRides().add(r);
			Booking b = new Booking(driver.getRides().get(0), passenger);
			b.setBookingId(1);
			MoneyTransaction mt = new MoneyTransaction(11, true, UtilDate.newDate(year,month,15), driver,"a", b);
			mt.setTransactionId(2);
			driver.addTransaction(mt);
			Mockito.when(db.find(Driver.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Car.class, car1.getNumberPlate())).thenReturn(car1);
			Mockito.when(db.find(Booking.class, b.getBookingId())).thenReturn(b);
			Mockito.when(db.find(MoneyTransaction.class, mt.getTransactionId())).thenReturn(mt);
			Mockito.when(db.find(User.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
			Mockito.when(db.createQuery("SELECT r FROM Request r WHERE r.origin = :origin AND r.destination = :destination AND r.rideDate = :date ORDER BY r.requestDate", Request.class)).thenReturn(null);
			//System.out.println("a");
			sut.open();
			sut.deleteDriver(driveremail);
			sut.close();
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			sut.close();
			fail();
		}
	}
	
	
	
	@Test
	public void test2() {
		String driveremail="driver1@gmail.com";
		String driverPassword="777";
		Calendar today = Calendar.getInstance();
		   
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=1; year+=1;}  
		
		try {
			User a;
			Driver driver=new Driver(driveremail,"",driverPassword, 0);
			Passenger passenger = new Passenger("", "", "", 0);
			Car car1 = new Car("EFGH 123", 5, null);
			//driver.addCar(car1);
			Ride r = new Ride("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7,driver, car1);
			r.setRideNumber(3);
			driver.getRides().add(r);
			Booking b = new Booking(driver.getRides().get(0), passenger);
			b.setBookingId(1);
			MoneyTransaction mt = new MoneyTransaction(11, true, UtilDate.newDate(year,month,15), driver,"a", b);
			mt.setTransactionId(2);
			driver.addTransaction(mt);
			Mockito.when(db.find(Driver.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Car.class, car1.getNumberPlate())).thenReturn(car1);
			Mockito.when(db.find(Booking.class, b.getBookingId())).thenReturn(b);
			Mockito.when(db.find(MoneyTransaction.class, mt.getTransactionId())).thenReturn(mt);
			Mockito.when(db.find(User.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
			Mockito.when(db.createQuery("SELECT r FROM Request r WHERE r.origin = :origin AND r.destination = :destination AND r.rideDate = :date ORDER BY r.requestDate", Request.class)).thenReturn(null);
			//System.out.println("a");
			sut.open();
			sut.deleteDriver(driveremail);
			sut.close();
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			sut.close();
			fail();
		}
	}
	@Test
	public void test3() {
		String driveremail="driver1@gmail.com";
		String driverPassword="777";
		Calendar today = Calendar.getInstance();
		   
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=1; year+=1;}  
		
		try {
			User a;
			Driver driver=new Driver(driveremail,"",driverPassword, 0);
			Passenger passenger = new Passenger("", "", "", 0);
			Car car1 = new Car("ABCD 123", 5, driver);
			driver.addCar(car1);
			Ride r = new Ride("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7,driver, car1);
			r.setRideNumber(3);
			driver.getRides().add(r);
			Booking b = new Booking(driver.getRides().get(0), passenger);
			b.setBookingId(1);
			Mockito.when(db.find(Driver.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Car.class, car1.getNumberPlate())).thenReturn(car1);
			Mockito.when(db.find(Booking.class, b.getBookingId())).thenReturn(b);
			//Mockito.when(db.find(MoneyTransaction.class, mt.getTransactionId())).thenReturn(mt);
			Mockito.when(db.find(User.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
			Mockito.when(db.createQuery("SELECT r FROM Request r WHERE r.origin = :origin AND r.destination = :destination AND r.rideDate = :date ORDER BY r.requestDate", Request.class)).thenReturn(null);
			//System.out.println("a");
			sut.open();
			sut.deleteDriver(driveremail);
			sut.close();
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			sut.close();
			fail();
		}
	}
	
	
	@Test
	public void test4() {
		String driveremail="driver1@gmail.com";
		String driverPassword="777";
		Calendar today = Calendar.getInstance();
		   
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=1; year+=1;}  
		
		try {
			User a;
			Driver driver=new Driver(driveremail,"",driverPassword, 0);
			Passenger passenger = new Passenger("", "", "", 0);
			Car car1 = new Car("ABCD 123", 5, driver);
			driver.addCar(car1);
			Booking b = new Booking(null, passenger);
			b.setBookingId(1);
			MoneyTransaction mt = new MoneyTransaction(11, true, UtilDate.newDate(year,month,15), driver,"a", b);
			mt.setTransactionId(2);
			driver.addTransaction(mt);
			Mockito.when(db.find(Driver.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Car.class, car1.getNumberPlate())).thenReturn(car1);
			Mockito.when(db.find(Booking.class, b.getBookingId())).thenReturn(b);
			Mockito.when(db.find(MoneyTransaction.class, mt.getTransactionId())).thenReturn(mt);
			Mockito.when(db.find(User.class, driveremail)).thenReturn(driver);
			//Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
			Mockito.when(db.createQuery("SELECT r FROM Request r WHERE r.origin = :origin AND r.destination = :destination AND r.rideDate = :date ORDER BY r.requestDate", Request.class)).thenReturn(null);
			//System.out.println("a");
			sut.open();
			sut.deleteDriver(driveremail);
			sut.close();
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			sut.close();
			fail();
		}
	}

	@Test
	public void test5() {
		String driveremail="driver1@gmail.com";
		String driverPassword="777";
		String falseEmail = "driverquenoexiste@gmail.com";
		Calendar today = Calendar.getInstance();
		   
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=1; year+=1;}  
		
		try {
			User a;
			Driver driver=new Driver(driveremail,"",driverPassword, 0);
			Passenger passenger = new Passenger("", "", "", 0);
			Car car1 = new Car("ABCD 123", 5, driver);
			driver.addCar(car1);
			Ride r = new Ride("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7,driver, car1);
			r.setRideNumber(3);
			driver.getRides().add(r);
			Booking b = new Booking(driver.getRides().get(0), passenger);
			b.setBookingId(1);
			MoneyTransaction mt = new MoneyTransaction(11, true, UtilDate.newDate(year,month,15), driver,"a", b);
			mt.setTransactionId(2);
			driver.addTransaction(mt);
			Mockito.when(db.find(Driver.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Car.class, car1.getNumberPlate())).thenReturn(car1);
			Mockito.when(db.find(Booking.class, b.getBookingId())).thenReturn(b);
			Mockito.when(db.find(MoneyTransaction.class, mt.getTransactionId())).thenReturn(mt);
			Mockito.when(db.find(User.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
			Mockito.when(db.createQuery("SELECT r FROM Request r WHERE r.origin = :origin AND r.destination = :destination AND r.rideDate = :date ORDER BY r.requestDate", Request.class)).thenReturn(null);
			//System.out.println("a");
			sut.open();
			sut.deleteDriver(falseEmail);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			sut.close();
			sut.close();
			assertTrue(true);
		}
	}
	
	@Test
	public void test6() {
		String driveremail="driver1@gmail.com";
		String driverPassword="777";
		Calendar today = Calendar.getInstance();
		   
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=1; year+=1;}  
		
		try {
			User a;
			Driver driver=new Driver(driveremail,"",driverPassword, 0);
			Passenger passenger = new Passenger("", "", "", 0);
			Car car1 = new Car("ABCD 123", 5, driver);
			driver.addCar(car1);
			Ride r = new Ride("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7,driver, car1);
			r.setRideNumber(3);
			driver.getRides().add(r);
			Booking b = new Booking(driver.getRides().get(0), passenger);
			b.setBookingId(1);
			MoneyTransaction mt = new MoneyTransaction(11, true, UtilDate.newDate(year,month,15), driver,"a", b);
			mt.setTransactionId(2);
			driver.addTransaction(mt);
			Mockito.when(db.find(Driver.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Car.class, car1.getNumberPlate())).thenReturn(car1);
			Mockito.when(db.find(Booking.class, b.getBookingId())).thenReturn(b);
			Mockito.when(db.find(MoneyTransaction.class, mt.getTransactionId())).thenReturn(mt);
			Mockito.when(db.find(User.class, driveremail)).thenReturn(driver);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
			Mockito.when(db.createQuery("SELECT r FROM Request r WHERE r.origin = :origin AND r.destination = :destination AND r.rideDate = :date ORDER BY r.requestDate", Request.class)).thenReturn(null);
			//System.out.println("a");
			sut.open();
			sut.deleteDriver(null);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			sut.close();
			sut.close();
			assertTrue(true);
		}
	}

}
