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
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProjectDAOLLTest {

    public ProjectDAOLLTest() {
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
        ProjectDAOLL.delete();
    }

    @Test
    public void testCreate() throws Exception {
        ProjectDAOLL.create(new Project("'pro'", 0));
        assertEquals("'pro', 0", ProjectDAOLL.readPk("'pro'").toString());
    }

    @Test
    public void testCreateWithArchive() throws Exception {
        ProjectDAOLL.create(new Project("'pro'", 1));
        assertEquals("'pro', 1", ProjectDAOLL.readPk("'pro'").toString());
    }

    @Test
    public void testRead_0args() throws Exception {
        ProjectDAOLL.create(new Project("pro", 0));
        Vector result = ProjectDAOLL.read();
        assertEquals(new Project("pro", 0), result.get(0));
    }

    @Test
    public void testRead_String_Object() throws Exception {
        ProjectDAOLL.create(new Project("pro", 0));
        Vector result = ProjectDAOLL.read("pro_name", "pro");
        assertEquals(new Project("pro", 0), result.get(0));
    }

    @Test
    public void testUpdate() throws Exception {
        ProjectDAOLL.create(new Project("pro", 0));
        ProjectDAOLL.update("pro", "project");
        Vector result = ProjectDAOLL.read();
        assertEquals(new Project("project", 0), result.get(0));
    }

    @Test
    public void testArchive() throws Exception {
        ProjectDAOLL.create(new Project("pro"));
        ProjectDAOLL.archive("pro");
        assertEquals(1, ProjectDAOLL.readPk("pro").archive);
    }

    @Test
    public void testUnarchive() throws Exception {
        ProjectDAOLL.create(new Project("pro", 1));
        ProjectDAOLL.unarchive("pro");
        assertEquals(0, ProjectDAOLL.readPk("pro").archive);
    }

    @Test
    public void testDelete_0args() throws Exception {
        ProjectDAOLL.create(new Project("pro", 0));
        ProjectDAOLL.delete();
        assertEquals(0, ProjectDAOLL.read().size());
    }

    @Test
    public void testDelete_String_Object() throws Exception {
        ProjectDAOLL.create(new Project("pro", 0));
        ProjectDAOLL.delete("pro_name", "pro");
        assertEquals(0, ProjectDAOLL.read().size());
    }

    @Test
    public void testReadPk() throws Exception {
        ProjectDAOLL.create(new Project("pro", 0));
        Project result = ProjectDAOLL.readPk("pro");
        assertEquals(new Project("pro", 0), result);
    }

    @Test
    public void testDeletePk() throws Exception {
        ProjectDAOLL.create(new Project("pro", 0));
        ProjectDAOLL.deletePk("pro");
        assertEquals(0, ProjectDAOLL.read().size());
    }

}