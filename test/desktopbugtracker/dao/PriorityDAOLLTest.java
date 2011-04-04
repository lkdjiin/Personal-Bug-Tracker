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

import desktopbugtracker.component.Priority;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PriorityDAOLLTest {

    public PriorityDAOLLTest() {
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
    }

    @After
    public void tearDown() throws SQLException {
        PriorityDAOLL.delete();
    }

    @Test
    public void testCreate() throws Exception {
        PriorityDAOLL.create(new Priority(123));
    }

    @Test(expected=AssertionError.class)
    public void testCreateNull() throws Exception {
        PriorityDAOLL.create(null);
    }

    @Test(expected=AssertionError.class)
    public void testCreateBadArgument() throws Exception {
        PriorityDAOLL.create(new Priority(-1));
    }

    @Test
    public void testRead_0args() throws Exception {
        PriorityDAOLL.create(new Priority(123));
        Vector result = PriorityDAOLL.read();
        assertEquals(new Priority(123), result.get(0));
    }

    @Test
    public void testReadPk() throws Exception {
        PriorityDAOLL.create(new Priority(123));
        Priority p = PriorityDAOLL.readPk(123);
        assertEquals(new Priority(123), p);
    }

    @Test
    public void testDelete_0args() throws Exception {
        PriorityDAOLL.create(new Priority(123));
        PriorityDAOLL.delete();
        assertEquals(0, PriorityDAOLL.read().size());
    }

    @Test
    public void testDelete_String_Object() throws Exception {
        PriorityDAOLL.create(new Priority(123));
        PriorityDAOLL.delete("pri_number", 123);
        assertEquals(0, PriorityDAOLL.read().size());
    }

    @Test
    public void testDeletePk_Integer() throws Exception {
        PriorityDAOLL.create(new Priority(123));
        PriorityDAOLL.deletePk(123);
        assertEquals(0, PriorityDAOLL.read().size());
    }

    @Test
    public void testDeletePk_Priority() throws Exception {
        PriorityDAOLL.create(new Priority(123));
        PriorityDAOLL.deletePk(new Priority(123));
        assertEquals(0, PriorityDAOLL.read().size());
    }

}