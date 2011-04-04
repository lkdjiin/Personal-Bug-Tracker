/*
 *  This file is part of Personal Bug Tracker.
 *  Copyright (C) 2009, Xavier Nayrac
 *
 *  Personal Bug Tracker is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package desktopbugtracker.component;

import desktopbugtracker.component.dto.BugDTO;
import desktopbugtracker.dao.*;
import desktopbugtracker.tools.*;
import java.sql.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BugTest {

    public BugTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        BugDAO.delete();
        CategoryDAO.delete();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws SQLException {

    }

    @Test
    public void testConstructorWithoutArchive() {
        Bug bug = new Bug(1, "t", 123, 321, "1", "2", 9, "n", 7);
        assertEquals("1, t, 123, 321, 1, 2, 9, n, 7, 0", bug.toString());
    }

    @Test
    public void testConstructorDTO() {
        BugDTO bugDTO = new BugDTO(1, "t", 123, 321, "1", "2", 9, "n", 7, 0);
        Bug b = new Bug(bugDTO);
        assertEquals("1, t, 123, 321, 1, 2, 9, n, 7, 0", b.toString());
    }

    @Test
    public void testEqualsBadType() {
        Integer badType = new Integer(123);
        Bug instance = new Bug(1, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        assertEquals(false, instance.equals(badType));
    }

    @Test
    public void testEqualsNull() {
        Bug bad = null;
        Bug instance = new Bug(1, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        assertEquals(false, instance.equals(bad));
    }

    @Test
    public void testEqualsNotOk() {
        Bug test = new Bug(2, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        Bug instance = new Bug(1, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        assertEquals(false, instance.equals(test));
    }

    @Test
    public void testEqualsOk() {
        Bug test = new Bug(1, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        Bug instance = new Bug(1, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        assertEquals(true, instance.equals(test));
    }

    @Test
    public void testIsOpenedOk() {
        Bug instance = new Bug(1, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        assertEquals(true, instance.isOpened());
    }

    @Test
    public void testIsOpenedNotOk() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 1, "project", 0, 0);
        assertEquals(false, instance.isOpened());
    }

    @Test
    public void testIsClosedNotOk() {
        Bug instance = new Bug(1, "title", 1234, 0, "0.1", "", 1, "project", 0, 0);
        assertEquals(false, instance.isClosed());
    }

    @Test
    public void testIsClosedOk() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 1, "project", 0, 0);
        assertEquals(true, instance.isClosed());
    }

    @Test
    public void testIsGoodPriorityForPrintingOkItsTheSame() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 2, "project", 0, 0);
        Priority p = new Priority(2);
        assertEquals(true, instance.isGoodPriorityForPrinting(p));
    }

    @Test
    public void testIsGoodPriorityForPrintingOkItsZero() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 2, "project", 0, 0);
        Priority p = new Priority(0);
        assertEquals(true, instance.isGoodPriorityForPrinting(p));
    }

    @Test
    public void testIsGoodPriorityForPrintingNotOk() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 2, "project", 0, 0);
        Priority p = new Priority(3);
        assertEquals(false, instance.isGoodPriorityForPrinting(p));
    }

    @Test
    public void testIsGoodCategoryForPrintingOkItsTheSame() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 2, "project", 2, 0);
        Category c = new Category(2, "foo", 0);
        assertEquals(true, instance.isGoodCategoryForPrinting(c));
    }

    @Test
    public void testIsGoodCategoryForPrintingOkItsZero() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 2, "project", 2, 0);
        Category c = new Category(0, "foo", 0);
        assertEquals(true, instance.isGoodCategoryForPrinting(c));
    }

    @Test
    public void testIsGoodCategoryForPrintingOkItsNull() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 2, "project", 2, 0);
        Category c = null;
        assertEquals(true, instance.isGoodCategoryForPrinting(c));
    }

    @Test
    public void testIsGoodCategoryForPrintingNotOk() {
        Bug instance = new Bug(1, "title", 1234, 123456, "0.1", "0.2", 2, "project", 2, 0);
        Category c = new Category(3, "foo", 0);
        assertEquals(false, instance.isGoodCategoryForPrinting(c));
    }
}