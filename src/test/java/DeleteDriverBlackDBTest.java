import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import testOperations.TestDataAccess;

public class DeleteDriverBlackDBTest {

    static DataAccess sut = new DataAccess();  // System Under Test
    static TestDataAccess testDA = new TestDataAccess(); // BD laguntzeko

    @Test
    public void test1() {
        // Caso: Driver con rides, cars y transacciones
        String driverEmail = "driver10@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "DriverName", password, 0, true);
            Passenger passenger = (Passenger) testDA.createUser("p1@gmail.com", "P", "p", 0, false);
            Car car = testDA.addCar("ABC 123", 5, driver.getEmail());
            Ride ride = testDA.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7, driver.getEmail(), car);
            Booking booking = testDA.addBooking(ride.getRideNumber(), passenger.getEmail());
            testDA.addMoneyTransaction(11, true, UtilDate.newDate(year, month, 15), driver.getEmail(), "a", booking);
            testDA.close();

            sut.open();
            sut.deleteDriver(driverEmail);
            sut.close();

            testDA.open();
            
            testDA.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería lanzar excepción");
        } finally {
            testDA.open();
            testDA.removeDriver(driverEmail);
            testDA.close();
        }
    }

    @Test
    public void test2() {
        // Caso: Driver con ride pero sin coche asociado
        String driverEmail = "driver20@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "DriverName", password, 0, true);
            Passenger passenger = (Passenger) testDA.createUser("p2@gmail.com", "P", "p", 0, false);
            Ride ride = testDA.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7, driver.getEmail(), null);
            Booking booking = testDA.addBooking(ride.getRideNumber(), passenger.getEmail());
            testDA.close();

            sut.open();
            sut.deleteDriver(driverEmail);
            sut.close();

            testDA.open();
            assertFalse(testDA.existDriver(driverEmail));
            testDA.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería lanzar excepción");
        } finally {
            testDA.open();
            testDA.removeDriver(driverEmail);
            testDA.close();
        }
    }

    @Test
    public void test3() {
        // Caso: Driver con coche pero sin transacciones
        String driverEmail = "driver30@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "DriverName", password, 0, true);
            Car car = testDA.addCar("ZZZ 999", 4, driver.getEmail());
            Ride ride = testDA.addRide("Bilbo", "Gasteiz", UtilDate.newDate(year, month, 10), 4, 6, driver.getEmail(), car);
            testDA.close();

            sut.open();
            sut.deleteDriver(driverEmail);
            sut.close();

            testDA.open();
            assertFalse(testDA.existDriver(driverEmail));
            testDA.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería lanzar excepción");
        } finally {
            testDA.open();
            testDA.removeDriver(driverEmail);
            testDA.close();
        }
    }

    @Test
    public void test4() {
        // Caso: Driver sin rides ni cars ni transacciones
        String driverEmail = "driver4@gmail.com";
        String password = "777";

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "DriverName", password, 0, true);
            testDA.close();

            sut.open();
            sut.deleteDriver(driverEmail);
            sut.close();

            testDA.open();
            assertFalse(testDA.existDriver(driverEmail));
            testDA.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería lanzar excepción");
        } finally {
            testDA.open();
            testDA.removeDriver(driverEmail);
            testDA.close();
        }
    }
    @Test
    public void test5() {
        // Caso: El driver no existe en la BD
        String driverEmail = "driver10@gmail.com";
        String driverPassword = "777";
        String falseEmail = "driverquenoexiste@gmail.com";
        Calendar today = Calendar.getInstance();

        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
       
            testDA.open();
            testDA.createDriver(driverEmail, "DriverName");
            testDA.close();

            sut.open();
            sut.deleteDriver(falseEmail);  
            sut.close();

            testDA.open();
            assertTrue(testDA.existDriver(driverEmail)); 
            testDA.close();
        } catch (Exception e) {
            e.printStackTrace();
            sut.close();
            assertTrue(true); 
        } finally {
            testDA.open();
            testDA.removeDriver(driverEmail);
            testDA.close();
        }
    }

    @Test
    public void test6() {

        String driverEmail = "driver100@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();

        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            testDA.createUser(driverEmail, "DriverName", password, 0, true);
            testDA.close();

            sut.open();
            sut.deleteDriver(null);  
            sut.close();

            fail("Debería lanzar excepción o manejar null correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            sut.close();
            assertTrue(true); 
        } finally {
            testDA.open();
            testDA.removeDriver(driverEmail);
            testDA.close();
        }
    }

}
