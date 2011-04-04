/*
 *   This file is part of Personal Bug Tracker.
 *   Copyright 2010, Xavier Nayrac <xavier.nayrac@gmail.com>.
 * 
 *   Personal Bug Tracker is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package desktopbugtracker.component;

import desktopbugtracker.component.dto.AttachmentDTO;
import desktopbugtracker.dao.*;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;
import org.junit.*;
import static org.junit.Assert.*;

public class AttachmentTest {

    static Connection con = null;

    public AttachmentTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        AttachmentDAOLL.delete();
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
    public void testEquals() {
        Attachment a = null;
        Attachment b = new Attachment(1, "text", 5);
        Attachment c = new Attachment(1, "text", 5);
        Attachment d = new Attachment(2, "text", 5);
        assertEquals(false, b.equals(a));
        assertEquals(false, b.equals(d));
        assertEquals(false, b.equals(new Object()));
        assertEquals(true, b.equals(c));
    }

    @Test
    public void testHashCode() {
        Attachment b = new Attachment(1, "text", 5);
        Attachment c = new Attachment(1, "text", 5);
        Attachment d = new Attachment(2, "text", 5);
        assertEquals(false, b.hashCode() == d.hashCode());
        assertEquals(true, b.hashCode() == c.hashCode());
    }

    @Test
    public void testArchive() throws SQLException {
        AttachmentDAOLL.delete();
        Attachment a = new Attachment(new AttachmentDTO(1, "text", 0, 17));
        AttachmentDAO.create(a);
        a = new Attachment(new AttachmentDTO(2, "text2", 0, 18));
        AttachmentDAO.create(a);
        a.archive();

        Vector<Attachment> v = AttachmentDAOLL.read();

        assertEquals(0, v.get(0).archive);
        assertEquals(1, v.get(1).archive);
    }

    @Test
    public void testUnarchive() throws SQLException {
        AttachmentDAOLL.delete();
        Attachment a = new Attachment(new AttachmentDTO(1, "text", 1, 17));
        AttachmentDAO.create(a);
        a = new Attachment(new AttachmentDTO(2, "text2", 1, 18));
        AttachmentDAO.create(a);
        a.unarchive();

        Vector<Attachment> v = AttachmentDAOLL.read();

        assertEquals(1, v.get(0).archive);
        assertEquals(0, v.get(1).archive);
    }

    @Test
    public void testToString() {
        Attachment a = new Attachment(1, "text to view", 5);
        assertEquals("text to view", a.toString());
    }
}