package desktopbugtracker.component;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PriorityTest {

    public PriorityTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEqualsNull() {
        Priority instance = new Priority(1);
        assertEquals(false, instance.equals(null));
    }

    @Test
    public void testEqualsBadType() {
        Priority instance = new Priority(1);
        assertEquals(false, instance.equals(new Integer(1)));
    }

    @Test
    public void testEqualsNotOk() {
        Priority instance = new Priority(1);
        assertEquals(false, instance.equals(new Priority(2)));
    }

    @Test
    public void testEqualsOk() {
        Priority instance = new Priority(1);
        assertEquals(true, instance.equals(new Priority(1)));
    }
}