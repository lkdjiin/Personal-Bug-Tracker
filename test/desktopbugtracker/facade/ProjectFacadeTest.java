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

package desktopbugtracker.facade;

import desktopbugtracker.component.*;
import desktopbugtracker.dao.*;
import desktopbugtracker.tools.*;
import java.sql.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProjectFacadeTest {

    public ProjectFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        clearTable();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        clearTable();
    }

    @Before
    public void setUp() throws SQLException {
        ProjectDAO.create(new Project("alpha", 0));
        VersionDAO.create(new Version("0.1", "alpha", 0));
        VersionDAO.create(new Version("0.2", "alpha", 0));
        BugDAO.create(new Bug(1, "titre", 0, 0, "0.1", "", 1, "alpha", 1, 0));
        BugDAO.create(new Bug(2, "titre", 0, 0, "0.2", "", 1, "alpha", 2, 0));
        Pro_catDAO.create(new Pro_cat("alpha", 1, 0));
        Pro_catDAO.create(new Pro_cat("alpha", 2, 0));
        CategoryDAO.create(new Category(0, "", 0)); // Default category
        CategoryDAO.create(new Category(1, "1", 0));
        CategoryDAO.create(new Category(2, "2", 0));
    }

    @After
    public void tearDown() throws SQLException {
        clearTable();
    }

    public static void clearTable() throws SQLException {
        ProjectDAO.delete();
        VersionDAO.delete();
        BugDAO.delete();
        Pro_catDAO.delete();
        CategoryDAO.delete();
    }

    @Test
    public void testRename() throws Exception {
        boolean result = ProjectFacade.rename(new Project("alpha", 0), "beta");
        assertEquals(true, result);
        assertEquals("beta", ProjectDAO.readPk("beta").pro_name);
        assertEquals(2, VersionDAO.read("pro_name", "beta").size());
        ProjectFacade.rename(new Project("beta", 0), "alpha"); // Remise en ordre de la db
    }

    @Test
    public void testDelete() throws Exception {
        ProjectFacade.delete(new Project("alpha", 0));
        assertEquals(0, ProjectDAO.read().size());
        assertEquals(0, VersionDAO.read().size());
        assertEquals(0, BugDAO.read().size());
        assertEquals(0, Pro_catDAO.getCategoryID("alpha").size());
        assertEquals(1, CategoryDAO.read().size()); // Still the default category
    }
}