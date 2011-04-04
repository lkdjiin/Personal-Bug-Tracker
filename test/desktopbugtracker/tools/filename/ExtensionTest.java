/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopbugtracker.tools.filename;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xavier.nayrac@gmail.com
 */
public class ExtensionTest {

    public ExtensionTest() {
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
    public void testIsExt() {
        boolean result = Extension.isExt("truc.txt", ".txt");
        assertEquals(true, result);
    }

    @Test
    public void testNotIsExt() {
        boolean result = Extension.isExt("truc.txt", ".pdf");
        assertEquals(false, result);
    }

    @Test
    public void testTooShortIsExt() {
        boolean result = Extension.isExt(".txt", ".txt");
        assertEquals(false, result);
    }
}