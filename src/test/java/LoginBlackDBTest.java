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
/*
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
*/
    
    // ============================================================
    // test2: Driver login correcto
    // ============================================================
    @Test
    public void test2() {
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
            assertTrue(u instanceof Driver);
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
    // test3: Admin login correcto
    // ============================================================
    @Test
    public void test3() {
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
            e.printStackTrace();
            fail();
        } finally {
            testDA.open();
            testDA.removeAdmin(email);
            testDA.close();
        }
    }

    // ============================================================
    // test4: Usuario inexistente
    // ============================================================
    @Test
    public void test4() {
        String email = "noexiste@gmail.com";
        String password = "123";

        try {
            // asegurar que no existe
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
        	System.out.print("No debería lanzar excepción: ");
        	e.printStackTrace();
        	System.out.print("No debería lanzar excepción: ");
            fail("No debería lanzar excepción: ");
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
