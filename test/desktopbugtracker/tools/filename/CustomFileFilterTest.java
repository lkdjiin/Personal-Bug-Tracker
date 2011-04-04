package desktopbugtracker.tools.filename;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomFileFilterTest {

    public CustomFileFilterTest() {
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
    public void testAcceptDirectory() {
        CustomFileFilter instance = new CustomFileFilter(".txt", "txt");
        boolean result = instance.accept(new File("."));
        assertEquals(true, result);
    }

    @Test
    public void testAccept() {
        CustomFileFilter instance = new CustomFileFilter(".txt", "txt");
        boolean result = instance.accept(new File("truc.txt"));
        assertEquals(true, result);
    }

    @Test
    public void testAcceptTooShort() {
        CustomFileFilter instance = new CustomFileFilter(".txt", "txt");
        boolean result = instance.accept(new File(".txt"));
        assertEquals(false, result);
    }

    @Test
    public void testAcceptBadExt() {
        CustomFileFilter instance = new CustomFileFilter(".txt", "txt");
        boolean result = instance.accept(new File("truc.pdf"));
        assertEquals(false, result);
    }

    @Test
    public void testGetDescription() {
        CustomFileFilter instance = new CustomFileFilter(".txt", "text files");
        assertEquals("text files", instance.getDescription());
    }

}