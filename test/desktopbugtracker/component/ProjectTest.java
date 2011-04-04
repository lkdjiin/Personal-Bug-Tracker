package desktopbugtracker.component;

import java.sql.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProjectTest {

    public ProjectTest() {
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
        Project instance = new Project("project");
        assertEquals("project, 0", instance.toString());
    }

    @Test
    public void testEqualsNull() {
        Project instance = new Project("project", 0);
        assertEquals(false, instance.equals(null));
    }

    @Test
    public void testEqualsBadType() {
        Project instance = new Project("project", 0);
        assertEquals(false, instance.equals(new Integer(1)));
    }

    @Test
    public void testEqualsNotOk() {
        Project instance = new Project("project", 0);
        Project instance2 = new Project("other project", 0);
        assertEquals(false, instance.equals(instance2));
    }

    @Test
    public void testEqualsOk() {
        Project instance = new Project("project", 0);
        Project instance2 = new Project("project", 0);
        assertEquals(true, instance.equals(instance2));
    }

    @Test
    public void testToString() {
        Project instance = new Project("project", 0);
        assertEquals("project, 0", instance.toString());
    }
}