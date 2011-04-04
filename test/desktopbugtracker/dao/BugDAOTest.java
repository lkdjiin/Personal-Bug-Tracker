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

public class BugDAOTest {

    public BugDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws SQLException {
        BugDAO.delete();
    }

    @After
    public void tearDown() throws SQLException {
        BugDAO.delete();
    }

    @Test
    public void testUpdate_projectName() throws Exception {
        BugDAO.create(new Bug(1, "", 0, 0, "", "", 0, "'The project'", 0, 0));
        BugDAO.create(new Bug(2, "", 0, 0, "", "", 0, "'The project'", 0, 0));
        BugDAO.updateProjectName("'The project'", "'Le projet'");
        Vector<Bug> bugs = BugDAO.read("pro_name", "'Le projet'");
        assertEquals(2, bugs.size());
    }

    @Test
    public void testTurnOffCategory() throws Exception {
        BugDAO.create(new Bug(1, "", 0, 0, "", "", 0, "Project", 123, 0));
        BugDAO.create(new Bug(2, "", 0, 0, "", "", 0, "Project", 123, 0));
        Project p = new Project("Project", 0);
        Category c = new Category(123, "Title", 0);
        BugDAO.turnOffCategory(p, c);
        Vector<Bug> bugs = BugDAO.read("cat_id", 0);
        assertEquals(2, bugs.size());
    }

    @Test
    public void testReadByProjectOrderByPriority() throws Exception {
        BugDAO.create(new Bug(1, "p0", 0, 0, "", "", 0, "Project", 123, 0));
        BugDAO.create(new Bug(2, "p2", 0, 0, "", "", 2, "Project", 123, 0));
        BugDAO.create(new Bug(3, "p1", 0, 0, "", "", 1, "Project", 123, 0));
        Vector<Bug> bugs = BugDAO.readByProjectOrderByPriority(new Project("Project", 0));
        assertEquals("p0", bugs.get(0).bug_title);
        assertEquals("p1", bugs.get(1).bug_title);
        assertEquals("p2", bugs.get(2).bug_title);
    }

    @Test
    public void testReadOpenedByProjectOrderByPriority() throws SQLException {
        BugDAO.create(new Bug(1, "p0", 123, 321, "1", "2", 0, "Project", 123, 0));
        BugDAO.create(new Bug(2, "p2", 456, 0, "1", "", 2, "Project", 123, 0));
        BugDAO.create(new Bug(3, "p1", 789, 0, "2", "", 1, "Project", 123, 0));
        Vector<Bug> bugs = BugDAO.readOpenedByProjectOrderByPriority(new Project("Project", 0));
        assertEquals("p1", bugs.get(0).bug_title);
        assertEquals("p2", bugs.get(1).bug_title);
    }

    @Test
    public void testReadClosedByProjectOrderByPriority() throws SQLException {
        BugDAO.create(new Bug(1, "p0", 123, 321, "1", "2", 0, "Project", 123, 0));
        BugDAO.create(new Bug(2, "p2", 456, 0, "1", "", 2, "Project", 123, 0));
        BugDAO.create(new Bug(3, "p1", 789, 0, "2", "", 1, "Project", 123, 0));
        Vector<Bug> bugs = BugDAO.readClosedByProjectOrderByPriority(new Project("Project", 0));
        assertEquals("p0", bugs.get(0).bug_title);
    }

    @Test
    public void testReadOpenedByProjectAndPriority() throws SQLException {
        BugDAO.create(new Bug(1, "b1", 123, 0, "0.1", "", 1, "alpha", 0, 0));
        BugDAO.create(new Bug(2, "b2", 123, 321, "0.1", "0.2", 1, "alpha", 0, 0));
        Vector<Bug> bugs = BugDAO.readOpenedByProjectAndPriority(new Project("alpha", 0), 1);
        assertEquals(1, bugs.size());
        assertEquals("b1", bugs.get(0).bug_title);
    }

    @Test
    public void testClosedByProjectAndPriority() throws SQLException {
        BugDAO.create(new Bug(1, "b1", 123, 0, "0.1", "", 1, "alpha", 0, 0));
        BugDAO.create(new Bug(2, "b2", 123, 321, "0.1", "0.2", 1, "alpha", 0, 0));
        Vector<Bug> bugs = BugDAO.readClosedByProjectAndPriority(new Project("alpha", 0), 1);
        assertEquals(1, bugs.size());
        assertEquals("b2", bugs.get(0).bug_title);
    }


    @Test
    public void testReadOpenedByProjectAndVersion() throws SQLException {
        BugDAO.create(new Bug(1, "b1", 123, 0, "0.1", "", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(2, "b2", 123, 0, "0.1", "", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(3, "b3", 123, 321, "0.1", "", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(4, "b4", 123, 0, "0.2", "", 1, "project 1", 0, 0));
        Vector<Bug> bugs = BugDAO.readOpenedByProjectAndVersion(new Project("project 1", 0), new Version("0.1", "project 1"));
        assertEquals(2, bugs.size());
        assertEquals("b2", bugs.get(1).bug_title);
    }

    @Test
    public void testClosedOpenedByProjectAndVersion() throws SQLException {
        BugDAO.create(new Bug(1, "b1", 123, 0, "0.1", "", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(2, "b2", 123, 0, "0.1", "", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(3, "b3", 123, 321, "0.1", "0.1", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(4, "b4", 123, 321, "0.2", "0.2", 1, "project 1", 0, 0));
        Vector<Bug> bugs = BugDAO.readClosedByProjectAndVersion(new Project("project 1", 0), new Version("0.1", "project 1"));
        assertEquals(1, bugs.size());
        assertEquals("b3", bugs.get(0).bug_title);
    }

    @Test
    public void testReadOpenedOrderByID() throws SQLException {
        BugDAO.create(new Bug(90, "b1", 123, 0, "0.1", "", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(80, "b2", 123, 0, "0.1", "", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(70, "b3", 123, 321, "0.1", "0.1", 1, "project 1", 0, 0));
        BugDAO.create(new Bug(60, "b4", 123, 321, "0.2", "0.2", 1, "project 1", 0, 0));
        Vector<Bug> bugs = BugDAO.readOpenedOrderByID();
        assertEquals(2, bugs.size());
        assertEquals(80, bugs.get(0).bug_id);
        assertEquals(90, bugs.get(1).bug_id);
    }
}