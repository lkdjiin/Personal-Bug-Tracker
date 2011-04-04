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

import desktopbugtracker.component.Pro_cat;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Pro_catDAOLLTest {

    public Pro_catDAOLLTest() {
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
        Pro_catDAOLL.delete();
    }

    @Test
    public void testCreate() throws Exception {
        Pro_catDAOLL.create(new Pro_cat("alpha", 1, 0));
    }

    @Test
    public void testRead() throws Exception {
        Pro_catDAOLL.create(new Pro_cat("alpha", 1, 0));
        Vector result = Pro_catDAOLL.read();
        assertEquals(new Pro_cat("alpha", 1, 0), result.get(0));

    }

    @Test
    public void testDelete_Pro_cat() throws Exception {
        Pro_catDAOLL.create(new Pro_cat("alpha", 1, 0));
        Pro_catDAOLL.delete(new Pro_cat("alpha", 1, 0));
        assertEquals(0, Pro_catDAOLL.read().size());
    }

    @Test
    public void testDelete_0args() throws Exception {
        Pro_catDAOLL.create(new Pro_cat("alpha", 1, 0));
        Pro_catDAOLL.delete();
        assertEquals(0, Pro_catDAOLL.read().size());
    }

}