import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import dataAccess.DataAccess;
import domain.Passenger;
import domain.Driver;
import domain.Admin;
import domain.User;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class LoginBlackMockTest {
	
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
	/*
	@Test
	public void test1() {
		String passengeremail="passenger1@gmail.com";
		String passengerPassword="777";
		try {
			User a;
			Passenger passenger=new Passenger(passengeremail,"",passengerPassword, 0);
			Mockito.when(db.find(Passenger.class, passengeremail)).thenReturn(passenger);
			sut.open();
			a = sut.login(passengeremail, passengerPassword);
			if(a == null) {
				sut.close();
				fail();
			}else {
				sut.close();
				assertTrue(true);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			sut.close();
			fail();
		}
	}
	
	*/
	
	@Test
	public void test2() {
		String drivereremail="driver1@gmail.com";
		String driverPassword="444";
		try {
			User a;
			Driver driver =new Driver(drivereremail,"",driverPassword, 0);
			Mockito.when(db.find(Driver.class, drivereremail)).thenReturn(driver);
			sut.open();
			a = sut.login(drivereremail, driverPassword);
			if(a == null) {
				sut.close();
				fail();
			}else {
				sut.close();
				assertTrue(true);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			sut.close();
			fail();
		}
	} 
	@Test
	public void test3() {
		String adminemail="admin1@gmail.com";
		String adminPassword="111";
		try {
			User a;
			Admin admin =new Admin(adminemail,"",adminPassword, 0);
			Mockito.when(db.find(Admin.class, adminemail)).thenReturn(admin);
			sut.open();
			a = sut.login(adminemail, adminPassword);
			if(a == null) {
				sut.close();
				fail();
			}else {
				sut.close();
				assertTrue(true);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			sut.close();
			fail();
		}
	} 
	@Test
	public void test4() {
		String passengeremail="passenger1@gmail.com";
		String passengerPassword="777";
		String falseEmail = "passengerquenoexistendb@gmail.com";
		String falsePassword = "666";
		try {
			User a;
			Passenger passenger=new Passenger(passengeremail,"",passengerPassword, 0);
			Mockito.when(db.find(Passenger.class, passengeremail)).thenReturn(passenger);
			sut.open();
			a = sut.login(falseEmail, falsePassword);
			if(a != null) {
				sut.close();
				fail();
			}else {
				sut.close();
				assertTrue(true);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			sut.close();
			fail();
		}
	} 
	
	@Test
	public void test5() {
		String passengeremail="passenger1@gmail.com";
		String passengerPassword="777";
		String falseEmail = null;
		try {
			User a;
			Passenger passenger=new Passenger(passengeremail,"",passengerPassword, 0);
			Mockito.when(db.find(Passenger.class, passengeremail)).thenReturn(passenger);
			sut.open();
			a = sut.login(falseEmail, passengerPassword);
			if(a != null) {
				sut.close();
				fail();
			}else {
				sut.close();
				assertTrue(true);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			sut.close();
			fail();
		}
	} 
	
	@Test
	public void test6() {
		String passengeremail="passenger1@gmail.com";
		String passengerPassword="777";
		String falsePassword = null;
		try {
			User a;
			Passenger passenger=new Passenger(passengeremail,"",passengerPassword, 0);
			Mockito.when(db.find(Passenger.class, passengeremail)).thenReturn(passenger);
			sut.open();
			a = sut.login(passengeremail, falsePassword);
			if(a != null) {
				sut.close();
				fail();
			}else {
				sut.close();
				assertTrue(true);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			sut.close();
			fail();
		}
	} 

}
