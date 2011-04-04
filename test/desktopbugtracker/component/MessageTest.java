package desktopbugtracker.component;

import desktopbugtracker.dao.MessageDAO;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    public MessageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        MessageDAO.delete();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws SQLException {
        MessageDAO.delete();
    }

    @Test
    public void testConstructorWithoutArchive() {
        Message instance = new Message(1, "text", 1234, 1234, 456);
        assertEquals("1, text, 1234, 1234, 456, 0", instance.toString());
    }

    @Test
    public void testEqualsNull() {
        Message instance = new Message(1, "text", 1234, 1234, 456, 0);
        assertEquals(false, instance.equals(null));
    }

    @Test
    public void testEqualsBadType() {
        Message instance = new Message(1, "text", 1234, 1234, 456, 0);
        assertEquals(false, instance.equals(new Integer(123)));
    }

    @Test
    public void testEqualsNotOk() {
        Message instance = new Message(1, "text", 1234, 1234, 456, 0);
        Message instance2 = new Message(1, "an other text", 1234, 1234, 456, 0);
        assertEquals(false, instance.equals(instance2));
    }

    @Test
    public void testEqualsOk() {
        Message instance = new Message(1, "text", 1234, 1234, 456, 0);
        Message instance2 = new Message(1, "text", 1234, 1234, 456, 0);
        assertEquals(true, instance.equals(instance2));
    }

    @Test
    public void testGetTitleForScreenPrinting() {
        Message instance = new Message(1, "text", 555555555555L, 555555555555L, 456, 0);
        assertEquals("1987-08-10 --- 02:59:15 --- [Message ID : 1]", instance.getTitleForScreenPrinting());
    }

    @Test
    public void testGetDateAndTime() {
        Message instance = new Message(1, "text", 555555555555L, 555555555555L, 456, 0);
        assertEquals("1987-08-10 02:59:15", instance.getDateAndTime());
    }

    @Test
    public void testRead() throws SQLException {
        MessageDAO.create(new Message(1, "m1", 555555555555L, 555555555555L, 1, 0));
        MessageDAO.create(new Message(2, "m2", 555555555555L, 555555555555L, 1, 0));
        MessageDAO.create(new Message(3, "m3", 555555555555L, 555555555555L, 2, 0));
        Vector<Message> messages = Message.read("bug_id", 1);
        assertEquals(2, messages.size());
        assertEquals("1, m1, 555555555555, 555555555555, 1, 0", messages.get(0).toString());
        assertEquals("2, m2, 555555555555, 555555555555, 1, 0", messages.get(1).toString());
    }

    @Test
    public void testDelete() throws SQLException {
        MessageDAO.create(new Message(1, "m1", 555555555555L, 555555555555L, 1, 0));
        MessageDAO.create(new Message(2, "m2", 555555555555L, 555555555555L, 1, 0));
        MessageDAO.create(new Message(3, "m3", 555555555555L, 555555555555L, 2, 0));
        Message.delete("bug_id", 1);
        assertEquals(1, MessageDAO.read().size());
    }
}