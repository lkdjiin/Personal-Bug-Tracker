package desktopbugtracker.component;

import desktopbugtracker.dao.CategoryDAO;
import desktopbugtracker.tools.*;
import java.sql.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoryTest {

    public CategoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        CategoryDAO.delete();
    }

    @Before
    public void setUp() throws SQLException {
        CategoryDAO.delete();
        CategoryDAO.create(new Category(0, "", 0));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructorWithoutArchive() {
        Category c = new Category(1, "titre");
        assertEquals("1, titre, 0", c.toString());
    }

    @Test
    public void testEqualsNull() {
        Category instance = new Category(1, "titre", 0);
        boolean result = instance.equals(null);
        assertEquals(false, result);
    }

    @Test
    public void testEqualsBadType() {
        Category instance = new Category(1, "titre", 0);
        boolean result = instance.equals(new Integer(123));
        assertEquals(false, result);
    }

    @Test
    public void testEqualsNotOk() {
        Category instance = new Category(1, "titre", 0);
        boolean result = instance.equals(new Category(2, "titre", 0));
        assertEquals(false, result);
    }

    @Test
    public void testEqualsOk() {
        Category instance = new Category(1, "titre", 0);
        boolean result = instance.equals(new Category(1, "other title but we don't care", 0));
        assertEquals(true, result);
    }

    @Test
    public void testDelete() throws SQLException {
        CategoryDAO.create(new Category(1, "1", 0));
        new Category(1, "1", 0).delete();
        assertEquals(1, CategoryDAO.read().size());
    }

    @Test
    public void testTryToDeleteDefaultCategory() throws SQLException {
        new Category(0, "", 0).delete();
        assertEquals(1, CategoryDAO.read().size());
    }
}