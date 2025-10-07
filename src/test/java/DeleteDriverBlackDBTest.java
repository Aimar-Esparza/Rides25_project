import static org.junit.Assert.*;
import java.util.Calendar;
import javax.persistence.*;

import org.junit.*;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;

public class DeleteDriverBlackDBTest {

    static DataAccess sut;  
    static EntityManagerFactory emf;
    static EntityManager db;

    @BeforeClass
    public static void setUpBeforeClass() {
        // Crea una base de datos ObjectDB embebida solo para pruebas
        emf = Persistence.createEntityManagerFactory("objectdb:db/tests.odb");
        db = emf.createEntityManager();
        sut = new DataAccess(db);
    }

    @Before
    public void setUp() {
        // Limpiar antes de cada test
        db.getTransaction().begin();
        db.createQuery("DELETE FROM MoneyTransaction").executeUpdate();
        db.createQuery("DELETE FROM Booking").executeUpdate();
        db.createQuery("DELETE FROM Ride").executeUpdate();
        db.createQuery("DELETE FROM Car").executeUpdate();
        db.createQuery("DELETE FROM Driver").executeUpdate();
        db.createQuery("DELETE FROM Passenger").executeUpdate();
        db.createQuery("DELETE FROM Admin").executeUpdate();
        db.getTransaction().commit();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        if (db.isOpen()) db.close();
        if (emf.isOpen()) emf.close();
    }

    @Test
    public void testDeleteDriverWithRidesAndCars() {
        String driverEmail = "driver1@gmail.com";
        String password = "777";

        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        if (month == 12) { month = 1; year += 1; }

        db.getTransaction().begin();

        Driver driver = new Driver(driverEmail, "Driver1", password, 0);
        Car car = new Car("ABCD 123", 5, driver);
        driver.addCar(car);

        Ride ride = new Ride("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7, driver, car);
        ride.setRideNumber(3);
        driver.getRides().add(ride);

        Passenger passenger = new Passenger("passenger1@gmail.com", "", "123", 0);
        Booking booking = new Booking(ride, passenger);
        booking.setBookingId(1);

        MoneyTransaction mt = new MoneyTransaction(11, true, UtilDate.newDate(year, month, 15), driver, "a", booking);
        mt.setTransactionId(2);
        driver.addTransaction(mt);

        db.persist(passenger);
        db.persist(driver);
        db.persist(car);
        db.persist(ride);
        db.persist(booking);
        db.persist(mt);

        db.getTransaction().commit();

        // Ejecutar el método que queremos probar
        sut.open();
        sut.deleteDriver(driverEmail);
        sut.close();

        // Verificar que se ha eliminado correctamente
        Driver deleted = db.find(Driver.class, driverEmail);
        assertNull("El driver debería haber sido eliminado", deleted);
    }

    @Test
    public void testDeleteDriverWithoutCarsOrRides() {
        String driverEmail = "driver2@gmail.com";
        String password = "888";

        db.getTransaction().begin();
        Driver driver = new Driver(driverEmail, "Driver2", password, 0);
        db.persist(driver);
        db.getTransaction().commit();

        sut.open();
        sut.deleteDriver(driverEmail);
        sut.close();

        Driver deleted = db.find(Driver.class, driverEmail);
        assertNull("El driver debería haber sido eliminado", deleted);
    }
}

