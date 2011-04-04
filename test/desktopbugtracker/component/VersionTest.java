package desktopbugtracker.component;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VersionTest {

    public VersionTest() {
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
        Version instance = new Version("version", "project");
        assertEquals("version, project, 0", instance.toString());
    }

    @Test
    public void testEqualsNull() {
        Version instance = new Version("version", "project", 0);
        assertEquals(false, instance.equals(null));
    }

    @Test
    public void testEqualsBadType() {
        Version instance = new Version("version", "project", 0);
        assertEquals(false, instance.equals(new Integer(1)));
    }

    @Test
    public void testEqualsNotOk() {
        Version instance = new Version("version", "project", 0);
        Version instance2 = new Version("versione", "projecte", 0);
        assertEquals(false, instance.equals(instance2));
    }

    @Test
    public void testEqualsOk() {
        Version instance = new Version("version", "project", 0);
        Version instance2 = new Version("version", "project", 0);
        assertEquals(true, instance.equals(instance2));
    }
}