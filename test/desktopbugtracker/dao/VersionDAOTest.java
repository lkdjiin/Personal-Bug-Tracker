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

import desktopbugtracker.component.Version;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VersionDAOTest {

    public VersionDAOTest() {
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
        VersionDAO.delete();
    }

    @Test
    public void testUpdateProjectName() throws Exception {
        VersionDAO.create(new Version("1", "alpha", 0));
        VersionDAO.create(new Version("1", "beta", 0));
        VersionDAO.create(new Version("2", "alpha", 0));
        VersionDAO.updateProjectName("alpha", "omega");
        Vector<Version> v = VersionDAO.read("pro_name", "omega");
        assertEquals("1, omega, 0", v.get(0).toString());
        assertEquals("2, omega, 0", v.get(1).toString());
    }

    @Test
    public void testArchive() throws SQLException {
        VersionDAO.create(new Version("1'", "alpha", 0));
        VersionDAO.archive(new Version("1'", "alpha", 0));
        assertEquals(1, VersionDAO.readPk("1'", "alpha").archive);
    }

    @Test
    public void testUnarchive() throws SQLException {
        VersionDAO.create(new Version("1'", "alpha", 1));
        VersionDAO.unarchive("alpha");
        assertEquals(0, VersionDAO.readPk("1'", "alpha").archive);
    }
}