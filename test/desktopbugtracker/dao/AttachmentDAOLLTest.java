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

public class AttachmentDAOLLTest {

    static Connection con = null;

    public AttachmentDAOLLTest() {
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
    public void testCreateAndRead() throws Exception {
        Attachment a = new Attachment(1, "'text'", 0, 17);
        AttachmentDAOLL.create(a);
        Vector<Attachment> v = AttachmentDAOLL.read();
        assertEquals(1, v.size());
        assertEquals(1, v.get(0).id);
    }

    @Test
    public void testRead_String_Object() throws Exception {
        Attachment a = new Attachment(1, "text", 0, 17);
        AttachmentDAOLL.create(a);
        a = new Attachment(2, "text2", 0, 17);
        AttachmentDAOLL.create(a);

        Vector<Attachment> v = AttachmentDAOLL.read("name", "text");
        assertEquals(1, v.size());
        assertEquals(1, v.get(0).id);
        v = AttachmentDAOLL.read("mes_id", 17);
        assertEquals(2, v.size());
        assertEquals(2, v.get(1).id);
    }

    @Test
    public void testDelete_0args() throws Exception {
        Attachment a = new Attachment(1, "text", 0, 17);
        AttachmentDAOLL.create(a);
        a = new Attachment(2, "text2", 0, 17);
        AttachmentDAOLL.create(a);

        AttachmentDAOLL.delete();

        Vector<Attachment> v = AttachmentDAOLL.read();
        assertEquals(0, v.size());
    }

    @Test
    public void testDelete_String_Object() throws Exception {
        Attachment a = new Attachment(1, "text", 0, 17);
        AttachmentDAOLL.create(a);
        a = new Attachment(2, "text2", 0, 17);
        AttachmentDAOLL.create(a);

        AttachmentDAOLL.delete("name", "text");

        Vector<Attachment> v = AttachmentDAOLL.read();
        assertEquals(1, v.size());
        assertEquals("text2", v.get(0).name);
    }

    @Test
    public void testCreatePk() throws Exception {
        Attachment a = new Attachment(1, "te'xt", 0, 17);
        AttachmentDAOLL.createPk(a);
        AttachmentDAOLL.createPk(a);
        Vector<Attachment> v = AttachmentDAOLL.read();
        assertEquals(2, v.size());
    }

    @Test
    public void testGetNextId() throws Exception {
        Attachment a = new Attachment(17, "text", 0, 17);
        AttachmentDAOLL.create(a);
        assertEquals(18, AttachmentDAOLL.getNextId());
    }

    @Test
    public void testReadPk() throws Exception {
        Attachment a = new Attachment(1, "text", 0, 17);
        AttachmentDAOLL.create(a);
        a = new Attachment(2, "text2", 0, 17);
        AttachmentDAOLL.create(a);
        a = AttachmentDAOLL.readPk(1);
        assertEquals("text", a.name);
    }

    @Test
    public void testDeletePk_Integer() throws Exception {
        Attachment a = new Attachment(1, "text", 0, 17);
        AttachmentDAOLL.create(a);
        a = new Attachment(2, "text2", 0, 17);
        AttachmentDAOLL.create(a);
        AttachmentDAOLL.deletePk(1);
        a = AttachmentDAOLL.readPk(1);
        assertEquals(null, a);
    }

    @Test
    public void testDeletePk_Attachment() throws Exception {
        Attachment a = new Attachment(1, "text", 0, 17);
        AttachmentDAOLL.create(a);
        a = new Attachment(2, "text2", 0, 17);
        AttachmentDAOLL.create(a);
        AttachmentDAOLL.deletePk(a);
        a = AttachmentDAOLL.readPk(2);
        assertEquals(null, a);
    }

}