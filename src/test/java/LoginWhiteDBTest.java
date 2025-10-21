import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.*;
import testOperations.TestDataAccess;

public class LoginWhiteDBTest {

    // sut: system under test
    static DataAccess sut = new DataAccess();

    static TestDataAccess testDA = new TestDataAccess();

    private Passenger passenger;
    private Driver driver;
    private Admin admin;

    // ============================================================
    // test1: Passenger login correcto
    // ============================================================
   /*
    @Test
    public void test1() {
        String email = "passenger1@gmail.com";
        String password = "777";

        try {
            testDA.open();
            boolean exist = testDA.existPassenger(email);
            if (!exist) passenger = (Passenger) testDA.createUser(email, "PassengerName", password, 0, false);
            testDA.close();

            sut.open();
            User u = sut.login(email, password);
            sut.close();

            assertNotNull("Debe devolver un usuario válido", u);
            assertTrue("Debe ser Passenger", u instanceof Passenger);
            assertEquals(email, u.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería lanzar excepción");
        } finally {
            testDA.open();
            testDA.removePassenger(email);
            testDA.close();
        }
    }
*/
    
    // ============================================================
    // test2: Passenger contraseña incorrecta
    // ============================================================
    @Test
    public void test2() {
        String email = "passenger1@gmail.com";
        String password = "777";
        String wrongPassword = "771";

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
            e.printStackTrace();
            fail("No debería lanzar excepción");
        } finally {
            testDA.open();
            testDA.removePassenger(email);
            testDA.close();
        }
    }

    // ============================================================
    // test3: Driver login correcto
    // ============================================================
    @Test
    public void test3() {
        String email = "driver1@gmail.com";
        String password = "444";

        try {
            testDA.open();
            boolean exist = testDA.existDriver(email);
            if (!exist) driver = (Driver) testDA.createUser(email, "DriverName", password, 0, true);
            testDA.close();

            sut.open();
            User u = sut.login(email, password);
            sut.close();

            assertNotNull(u);
            assertTrue("Debe ser Driver", u instanceof Driver);
            assertEquals(email, u.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            testDA.open();
            testDA.removeDriver(email);
            testDA.close();
        }
    }

    // ============================================================
    // test4: Driver contraseña incorrecta
    // ============================================================
    @Test
    public void test4() {
        String email = "driver1@gmail.com";
        String password = "444";
        String wrongPassword = "441";

        try {
            testDA.open();
            if (!testDA.existDriver(email))
            	driver = (Driver) testDA.createUser(email, "DriverName", password, 0, true);
            testDA.close();

            sut.open();
            User u = sut.login(email, wrongPassword);
            sut.close();

            assertNull(u);
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removeDriver(email);
            testDA.close();
        }
    }

    // ============================================================
    // test5: Admin login correcto
    // ============================================================
    @Test
    public void test5() {
        String email = "admin1@gmail.com";
        String password = "111";

        try {
            testDA.open();
            boolean exist = testDA.existAdmin(email);
            if (!exist) admin = testDA.addAdmin(email, "AdminName", password, 0);
            testDA.close();

            sut.open();
            User u = sut.login(email, password);
            sut.close();

            assertNotNull(u);
            assertTrue(u instanceof Admin);
            assertEquals(email, u.getEmail());
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removeAdmin(email);
            testDA.close();
        }
    }

    // ============================================================
    // test6: Admin contraseña incorrecta
    // ============================================================
    @Test
    public void test6() {
        String email = "admin1@gmail.com";
        String password = "111";
        String wrongPassword = "1111";

        try {
            testDA.open();
            if (!testDA.existAdmin(email))
                admin = testDA.addAdmin(email, "AdminName", password, 0);
            testDA.close();

            sut.open();
            User u = sut.login(email, wrongPassword);
            sut.close();

            assertNull(u);
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removeAdmin(email);
            testDA.close();
        }
    }

    // ============================================================
    // test7: Usuario no existente
    // ============================================================
    @Test
    public void test7() {
        String email = "noexist@gmail.com";
        String password = "123";

        try {
            testDA.open();
            testDA.removePassenger(email);
            testDA.removeDriver(email);
            testDA.removeAdmin(email);
            testDA.close();

            sut.open();
            User u = sut.login(email, password);
            sut.close();

            assertNull(u);
        } catch (Exception e) {
            fail("No debe lanzar excepción");
        }
    }
}
