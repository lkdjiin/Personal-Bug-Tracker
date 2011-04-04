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
import java.sql.*;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VersionDAOLLTest {

    public VersionDAOLLTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        VersionDAO.delete();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        VersionDAO.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws SQLException {
        VersionDAO.delete();
    }

    @Test
    public void testCreateWithQuote() throws Exception {
        Version version = new Version("'ver'", "'pro'", 0);
        VersionDAOLL.create(version);
    }

    @Test
    public void testRead_Connection() throws Exception {
        VersionDAOLL.create(new Version("version", "project", 0));
        Vector<Version> result = VersionDAOLL.read();
        assertEquals(1, result.size());
        assertEquals("project", result.get(0).pro_name);
        assertEquals("version", result.get(0).ver_name);
    }

    @Test
    public void testRead_3args() throws Exception {
        VersionDAOLL.create(new Version("'version'", "'project'", 0));
        Vector<Version> result = VersionDAOLL.read("pro_name", "'project'");
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdate() throws Exception {
        Version v = new Version("'version'", "project", 0);
        VersionDAOLL.create(v);
        v.pro_name = "'project'";
        VersionDAOLL.update(v, "pro_name", "project");
        Vector<Version> result = VersionDAOLL.read("pro_name", "'project'");
        assertEquals(1, result.size());
    }

    @Test
    public void testDelete_Connection() throws Exception {
        VersionDAOLL.create(new Version("'version'", "project", 0));
        VersionDAOLL.delete();
        assertEquals(0, VersionDAOLL.read().size());
    }

    @Test
    public void testDelete_3args() throws Exception {
        VersionDAOLL.create(new Version("'version'", "project", 0));
        VersionDAOLL.delete("ver_name", "'version'");
        assertEquals(0, VersionDAOLL.read().size());
    }

    @Test
    public void testReadPk() throws Exception {
        VersionDAOLL.create(new Version("'version'", "project", 0));
        Version result = VersionDAOLL.readPk("'version'", "project");
        assertEquals("project", result.pro_name);
        assertEquals("'version'", result.ver_name);
    }

    @Test
    public void testDeletePk_3args() throws Exception {
        VersionDAOLL.create(new Version("'version'", "project", 0));
        VersionDAOLL.deletePk("'version'", "project");
        assertEquals(0, VersionDAOLL.read().size());
    }

    @Test
    public void testDeletePk_Connection_Version() throws Exception {
        Version version = new Version("'version'", "project", 0);
        VersionDAOLL.create(version);
        VersionDAOLL.deletePk(version);
        assertEquals(0, VersionDAOLL.read().size());
    }

}