package desktopbugtracker.component;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Priority_i18nTest {

    public Priority_i18nTest() {
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
        Priority_i18n instance = new Priority_i18n(1, "code", "desc");
        assertEquals(false, instance.equals(null));
    }

    @Test
    public void testEqualsBadType() {
        Priority_i18n instance = new Priority_i18n(1, "code", "desc");
        assertEquals(false, instance.equals(new Integer(1)));
    }

    @Test
    public void testEqualsNotOk() {
        Priority_i18n instance = new Priority_i18n(1, "code", "desc");
        Priority_i18n instance2 = new Priority_i18n(2, "code", "desc");
        assertEquals(false, instance.equals(instance2));
    }

    @Test
    public void testEqualsOk() {
        Priority_i18n instance = new Priority_i18n(1, "code", "desc");
        Priority_i18n instance2 = new Priority_i18n(1, "code", "desc");
        assertEquals(true, instance.equals(instance2));
    }

    @Test
    public void testToString() {
        Priority_i18n instance = new Priority_i18n(1, "code", "desc");
        assertEquals("1, code, desc", instance.toString());
    }
}