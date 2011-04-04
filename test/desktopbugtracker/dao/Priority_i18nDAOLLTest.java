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

import desktopbugtracker.component.Priority_i18n;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Priority_i18nDAOLLTest {

    public Priority_i18nDAOLLTest() {
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
        Priority_i18nDAOLL.delete();
    }

    @After
    public void tearDown() throws SQLException {
        Priority_i18nDAOLL.delete();
    }

    @Test
    public void testCreate() throws Exception {
        Priority_i18n priority_i18n = new Priority_i18n(1, "'2'", "'desc'");
        Priority_i18nDAOLL.create(priority_i18n);
    }

    @Test
    public void testRead_Connection() throws Exception {
        Priority_i18n priority_i18n = new Priority_i18n(1, "'2'", "'desc'");
        Priority_i18nDAOLL.create(priority_i18n);
        Vector result = Priority_i18nDAOLL.read();
        assertEquals(1, result.size());
    }

    @Test
    public void testRead_3args() throws Exception {
        Priority_i18n p = new Priority_i18n(1, "'2'", "'desc'");
        Priority_i18nDAOLL.create(p);
        Vector result = Priority_i18nDAOLL.read("pri_code", "'2'");
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdate() throws Exception {
        Priority_i18n p = new Priority_i18n(1, "'2'", "'desc'");
        Priority_i18nDAOLL.create(p);
        p.pri_code = "2";
        Priority_i18nDAOLL.update(p, "pri_code", "'2'");
        Vector result = Priority_i18nDAOLL.read("pri_code", "2");
        assertEquals(1, result.size());
    }

    @Test
    public void testDelete_Connection() throws Exception {
        Priority_i18nDAOLL.create(new Priority_i18n(1, "'2'", "'desc'"));
        Priority_i18nDAOLL.delete();
        assertEquals(true, Priority_i18nDAOLL.read().isEmpty());
    }

    @Test
    public void testDelete_3args() throws Exception {
        Priority_i18nDAOLL.create(new Priority_i18n(1, "'1'", "'desc1'"));
        Priority_i18nDAOLL.create(new Priority_i18n(2, "'2'", "'desc2'"));
        Priority_i18nDAOLL.delete("pri_description", "'desc2'");
        assertEquals(1, Priority_i18nDAOLL.read().size());
    }

    @Test
    public void testReadPk_3args() throws Exception {
        Priority_i18nDAOLL.create(new Priority_i18n(1, "'1'", "'desc1'"));
        Priority_i18n result = Priority_i18nDAOLL.readPk(1, "'1'");
        assertEquals("'desc1'", result.pri_description);
    }

    @Test
    public void testUpdatePk() throws Exception {
        Priority_i18n p = new Priority_i18n(1, "'1'", "'desc'");
        Priority_i18nDAOLL.create(p);
        p.pri_description = "desc";
        Priority_i18nDAOLL.updatePk(p);
        Priority_i18n result = Priority_i18nDAOLL.readPk(1, "'1'");
        assertEquals("desc", result.pri_description);
    }

    @Test
    public void testDeletePk_3args() throws Exception {
        Priority_i18n p = new Priority_i18n(1, "'1'", "'desc'");
        Priority_i18nDAOLL.create(p);
        Priority_i18nDAOLL.delete("pri_description", "'desc'");
        assertEquals(true, Priority_i18nDAOLL.read().isEmpty());
    }

}