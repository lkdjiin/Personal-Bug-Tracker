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

package desktopbugtracker.dao;

import desktopbugtracker.component.Attachment;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;
import org.junit.*;
import static org.junit.Assert.*;

public class AttachmentDAOTest {

    static Connection con = null;

    public AttachmentDAOTest() {
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
    public void tearDown() throws SQLException {
        AttachmentDAOLL.delete();
    }

    @Test
    public void testArchive() throws Exception {
        Attachment a = new Attachment(1, "text", 0, 17);
        AttachmentDAO.create(a);
        a = new Attachment(2, "text2", 0, 18);
        AttachmentDAO.create(a);
        AttachmentDAO.archive(17);

        Vector<Attachment> v = AttachmentDAOLL.read();

        assertEquals(1, v.get(0).archive);
        assertEquals(0, v.get(1).archive);
    }

    @Test
    public void testUnarchive() throws Exception {
        Attachment a = new Attachment(1, "text", 1, 17);
        AttachmentDAO.create(a);
        a = new Attachment(2, "text2", 1, 18);
        AttachmentDAO.create(a);
        AttachmentDAO.unarchive(17);

        Vector<Attachment> v = AttachmentDAOLL.read();

        assertEquals(0, v.get(0).archive);
        assertEquals(1, v.get(1).archive);
    }

}