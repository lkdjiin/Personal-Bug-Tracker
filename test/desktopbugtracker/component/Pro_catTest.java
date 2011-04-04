package desktopbugtracker.component;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Pro_catTest {

    public Pro_catTest() {
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
    public void testConstructorWithoutArchive() {
        Pro_cat instance = new Pro_cat("project", 1);
        assertEquals("project, 1, 0", instance.toString());
    }

    @Test
    public void testEqualsNull() {
        Pro_cat instance = new Pro_cat("project", 1, 0);
        assertEquals(false, instance.equals(null));
    }

    @Test
    public void testEqualsBadType() {
        Pro_cat instance = new Pro_cat("project", 1, 0);
        assertEquals(false, instance.equals(new Integer(1)));
    }

    @Test
    public void testEqualsNotOk() {
        Pro_cat instance = new Pro_cat("project", 1, 0);
        Pro_cat instance2 = new Pro_cat("project", 2, 0);
        assertEquals(false, instance.equals(instance2));
    }

    @Test
    public void testEqualsOk() {
        Pro_cat instance = new Pro_cat("project", 1, 0);
        Pro_cat instance2 = new Pro_cat("project", 1, 0);
        assertEquals(true, instance.equals(instance2));
    }
}