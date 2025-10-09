import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.*;
import testOperations.TestDataAccess;

public class LoginBlackDBTest {

    // sut: system under test
    static DataAccess sut = new DataAccess();

    static TestDataAccess testDA = new TestDataAccess();

    private Passenger passenger;
    private Driver driver;
    private Admin admin;

    @Test
    public void test1() {
        String email = "passenger1@gmail.com";
        String password = "777";

        try {
            // configure database state
            testDA.open();
     
            boolean exist = testDA.existPassenger(email);
            if (!exist) passenger = (Passenger) testDA.createUser(email, "PassengerName", password, 0, false);
            testDA.close();

            // invoke SUT
            sut.open();
            
            User u = sut.login(email, password);
            sut.close();

            // verify
            assertNotNull(u);
            assertTrue(u instanceof Passenger);
            assertEquals(email, u.getEmail());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Ez luke exception hau atera beharrik: " + e.getMessage());
        } finally {
            // clean up
            testDA.open();
            testDA.removePassenger(email);
            testDA.close();
        }
    }

    
    // ============================================================
    // test5: Contraseña incorrecta
    // ============================================================
    @Test
    public void test5() {
        String email = "passenger1@gmail.com";
        String password = "777";
        String wrongPassword = "wrong";

        try {
            testDA.open();
            if (!testDA.existPassenger(email))
                passenger = (Passenger) testDA.createUser(email, "PassengerName", password, 0, false);
            testDA.close();

            sut.open();
            User u = sut.login(email, wrongPassword);
            sut.close();

            assertNull("El login debe fallar con contraseña incorrecta", u);
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removePassenger(email);
            testDA.close();
        }
    }

    // ============================================================
    // test6: Email null
    // ============================================================
    @Test
    public void test6() {
        try {
            sut.open();
            User u = sut.login(null, "777");
            sut.close();

            assertNull(u);
        } catch (Exception e) {
            fail("No debe lanzar excepción con email nulo");
            System.out.print(e.getMessage());
        }
    }

    // ============================================================
    // test7: Contraseña null
    // ============================================================
    @Test
    public void test7() {
        String email = "passenger1@gmail.com";
        String password = "777";

        try {
            testDA.open();
            if (!testDA.existPassenger(email))
                passenger = (Passenger) testDA.createUser(email, "PassengerName", password, 0, false);
            testDA.close();

            sut.open();
            User u = sut.login(email, null);
            sut.close();

            assertNull(u);
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removePassenger(email);
            testDA.close();
        }
    }
}
