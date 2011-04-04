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

import desktopbugtracker.component.*;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Pro_catDAOTest {


    public Pro_catDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        Pro_catDAO.delete();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Pro_catDAO.delete();
    }

    @Before
    public void setUp() throws SQLException {
        Pro_catDAO.create(new Pro_cat("alpha", 1, 0));
        Pro_catDAO.create(new Pro_cat("alpha", 2, 0));
        Pro_catDAO.create(new Pro_cat("alpha", 3, 0));
        Pro_catDAO.create(new Pro_cat("beta", 11, 0));
        Pro_catDAO.create(new Pro_cat("beta", 12, 0));
    }

    @After
    public void tearDown() throws SQLException {
        Pro_catDAO.delete();
    }

    @Test
    public void testGetCategoryID() throws SQLException {
        Vector result = Pro_catDAO.getCategoryID("alpha");
        assertEquals(3, result.size());
    }

    @Test
    public void testUpdateProjectName() throws SQLException {
        Pro_catDAO.updateProjectName("alpha", "delta");
        Vector result = Pro_catDAO.getCategoryID("delta");
        assertEquals(3, result.size());
    }

    @Test
    public void testDeleteWhereProject() throws SQLException {
        Pro_catDAO.deleteWhereProject(new Project("alpha", 0));
        Vector result = Pro_catDAO.getCategoryID("alpha");
        assertEquals(0, result.size());
    }

    @Test
    public void testArchive() throws SQLException {
        Pro_catDAO.archive("beta");
        Vector<Pro_cat> proCats = Pro_catDAO.read();
        assertEquals("beta, 11, 1", proCats.get(3).toString());
        assertEquals("beta, 12, 1", proCats.get(4).toString());
    }

    @Test
    public void testUnarchive() throws SQLException {
        Pro_catDAO.archive("beta");
        Pro_catDAO.unarchive("beta");
        Vector<Pro_cat> proCats = Pro_catDAO.read();
        assertEquals("beta, 11, 0", proCats.get(3).toString());
        assertEquals("beta, 12, 0", proCats.get(4).toString());
    }
}