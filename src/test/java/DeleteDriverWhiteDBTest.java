import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import testOperations.TestDataAccess;

public class DeleteDriverWhiteDBTest {

    static DataAccess sut = new DataAccess();  // System Under Test
    static TestDataAccess testDA = new TestDataAccess(); // Para manejar la BD en los tests

    @Test
    public void test1() {
        // Caso: Driver con rides, cars y transacciones
        String driverEmail = "driver100@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH) + 1;
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "Driver1", password, 0, true);
            Passenger passenger = (Passenger) testDA.createUser("p100@gmail.com", "P1", "p", 0, false);
            Car car = testDA.addCar("AAA 111", 4, driver.getEmail());
            Ride ride = testDA.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7, driver.getEmail(), car);
            Booking booking = testDA.addBooking(ride.getRideNumber(), passenger.getEmail());
            testDA.addMoneyTransaction(11, true, UtilDate.newDate(year, month, 15), driver.getEmail(), "a", booking);
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
            Driver driver = (Driver) testDA.createUser(driverEmail, "Driver2", password, 0, true);
            Passenger passenger = (Passenger) testDA.createUser("p2@gmail.com", "P2", "p", 0, false);
            Ride ride = testDA.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 20), 4, 6, driver.getEmail(), null);
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
        // Caso: Driver con coche y ride pero sin transacciones
        String driverEmail = "driver30@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "Driver3", password, 0, true);
            Car car = testDA.addCar("BBB 222", 5, driver.getEmail());
            testDA.addRide("Bilbo", "Gasteiz", UtilDate.newDate(year, month, 10), 4, 6, driver.getEmail(), car);
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
        // Caso: Driver con transacción pero sin rides
        String driverEmail = "driver4@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "Driver4", password, 0, true);
            Passenger passenger = (Passenger) testDA.createUser("p4@gmail.com", "P4", "p", 0, false);
            Booking booking = testDA.addBookingWithoutRide(passenger.getEmail());
            testDA.addMoneyTransaction(10, true, UtilDate.newDate(year, month, 15), driver.getEmail(), "b", booking);
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
        // Caso: Driver sin rides, cars ni transacciones
        String driverEmail = "driver50@gmail.com";
        String password = "777";

        try {
            testDA.open();
            testDA.createUser(driverEmail, "Driver5", password, 0, true);
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
    public void test6() {
        // Caso: Driver con coches, sin rides ni transacciones
        String driverEmail = "driver6@gmail.com";
        String password = "777";

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "Driver6", password, 0, true);
            Car car = testDA.addCar("CCC 333", 4, driver.getEmail());
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
    public void test7() {
        // Caso: Driver con coches y transacciones, sin rides
        String driverEmail = "driver7@gmail.com";
        String password = "777";
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        try {
            testDA.open();
            Driver driver = (Driver) testDA.createUser(driverEmail, "Driver7", password, 0, true);
            Car car = testDA.addCar("DDD 444", 4, driver.getEmail());
            Passenger passenger = (Passenger) testDA.createUser("p7@gmail.com", "P7", "p", 0, false);
            Booking booking = testDA.addBookingWithoutRide(passenger.getEmail());
            testDA.addMoneyTransaction(12, true, UtilDate.newDate(year, month, 15), driver.getEmail(), "c", booking);
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
public void test8() {
    // Caso: Driver con rides y transacciones, sin coches
    String driverEmail = "driver8@gmail.com";
    String password = "777";
    Calendar today = Calendar.getInstance();
    int month = today.get(Calendar.MONTH);
    int year = today.get(Calendar.YEAR);
    if (month == 12) { month = 1; year += 1; }

    try {
        testDA.open();
        Driver driver = (Driver) testDA.createUser(driverEmail, "Driver8", password, 0, true);
        Passenger passenger = (Passenger) testDA.createUser("p8@gmail.com", "P8", "p", 0, false);
        Ride ride = testDA.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 18), 4, 5, driver.getEmail(), null);
        Booking booking = testDA.addBooking(ride.getRideNumber(), passenger.getEmail());
        testDA.addMoneyTransaction(13, true, UtilDate.newDate(year, month, 18), driver.getEmail(), "d", booking);
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

   
}
