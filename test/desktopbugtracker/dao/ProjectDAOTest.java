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

import desktopbugtracker.component.Project;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProjectDAOTest {

    public ProjectDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        ProjectDAO.delete();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        ProjectDAO.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws SQLException {
        ProjectDAO.delete();
    }

    @Test
    public void testExists() throws Exception {
        ProjectDAO.create(new Project("project", 0));
        boolean result = ProjectDAO.exists("project");
        assertEquals(true, result);
        result = ProjectDAO.exists("proj");
        assertEquals(false, result);
    }

    @Test
    public void testIsArchivedTrue() throws SQLException {
        ProjectDAO.create(new Project("project", 1));
        boolean res = ProjectDAO.isArchived("project");
        assertEquals(true, res);
    }

    @Test
    public void testIsArchivedFalse() throws SQLException {
        ProjectDAO.create(new Project("project", 0));
        boolean res = ProjectDAO.isArchived("project");
        assertEquals(false, res);
    }
}