/*
 *   This file is part of Personal Bug Tracker.
 *   Copyright 2009, Xavier Nayrac <xavier.nayrac@gmail.com>.
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

import desktopbugtracker.component.Bug;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.*;
import org.junit.*;
import static org.junit.Assert.*;

public class BugDAOLLTest {

    static Connection con = null;

    public BugDAOLLTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        try {
            BugDAOLL.delete();
        } catch (SQLException ex) {
            Logger.getLogger(BugDAOLLTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        try {
            BugDAOLL.delete();
        } catch (SQLException ex) {
            Logger.getLogger(BugDAOLLTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testCreateBugWithQuote() throws Exception {
        Bug bug = new Bug(1, "title with '", 0, 0, "ver with '", "ver with '", 1, "name with '", 1, 0);
        BugDAOLL.create(bug);
    }

    @Test
    public void testRead_Connection() throws Exception {
        BugDAOLL.create(new Bug(1, "title", 0, 0, "ver", "ver", 1, "name", 1, 0));
        Vector result = BugDAOLL.read();
        assertEquals(true, result.size() == 1);
    }

    @Test
    public void testRead_3args() throws Exception {
        BugDAOLL.create(new Bug(1, "'title'", 0, 0, "ver", "ver", 1, "name", 1, 0));
        Vector result = BugDAOLL.read("bug_title", "'title'");
        assertEquals(true, ((Bug)result.get(0)).bug_title.equals("'title'"));
    }

    @Test
    public void testUpdate() throws Exception {
        Bug b = new Bug(1, "'title'", 0, 0, "ver", "ver", 1, "name", 1, 0);
        BugDAOLL.create(b);
        b.pro_name = "'name'";
        BugDAOLL.update(b, "bug_title", "'title'");

    }

    @Test
    public void testDelete_Connection() throws Exception {
        BugDAOLL.create(new Bug(1, "'title'", 0, 0, "ver", "ver", 1, "name", 1, 0));
        BugDAOLL.delete();
        assertEquals(true, BugDAOLL.read().isEmpty());
    }

    @Test
    public void testDelete_3args() throws Exception {
        BugDAOLL.create(new Bug(1, "'title'", 0, 0, "ver", "ver", 1, "name", 1, 0));
        BugDAOLL.delete("bug_title", "'title'");
        assertEquals(true, BugDAOLL.read().isEmpty());
    }

    @Test
    public void testCreatePk() throws Exception {
        BugDAOLL.create(new Bug(1, "'title'", 0, 0, "ver", "ver", 1, "name", 1, 0));
        Bug b = new Bug(0, "bug 2", 0, 0, "ver", "ver", 1, "name", 1, 0);
        BugDAOLL.createPk(b);
    }

    @Test
    public void testGetNextId_Connection() throws Exception {
        BugDAOLL.create(new Bug(111, "'title'", 0, 0, "ver", "ver", 1, "name", 1, 0));
        int result = BugDAOLL.getNextId();
        assertEquals(112, result);
    }

    @Test
    public void testReadPk() throws Exception {
        BugDAOLL.create(new Bug(111, "t111", 0, 0, "ver", "ver", 1, "name", 1, 0));
        Bug result = BugDAOLL.readPk(111);
        assertEquals("t111", result.bug_title);
    }

    @Test
    public void testUpdatePk_Connection_Bug() throws Exception {
        Bug b = new Bug(111, "t111", 0, 0, "ver", "ver", 1, "name", 1, 0);
        BugDAOLL.create(b);
        b.pro_name = "n111";
        BugDAOLL.updatePk(b);
    }

    @Test
    public void testDeletePk_Connection_Integer() throws Exception {
        Bug b = new Bug(111, "t111", 0, 0, "ver", "ver", 1, "name", 1, 0);
        BugDAOLL.create(b);
        BugDAOLL.deletePk(111);
        assertEquals(true, BugDAOLL.read().isEmpty());
    }

    @Test
    public void testDeletePk_Connection_Bug() throws Exception {
        Bug b = new Bug(111, "t111", 0, 0, "ver", "ver", 1, "name", 1, 0);
        BugDAOLL.create(b);
        BugDAOLL.deletePk(b);
        assertEquals(true, BugDAOLL.read().isEmpty());
    }


}