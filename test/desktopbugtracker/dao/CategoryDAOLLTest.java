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

import desktopbugtracker.component.Category;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoryDAOLLTest {

    public CategoryDAOLLTest() {
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
        CategoryDAOLL.delete();
    }

    @After
    public void tearDown() throws SQLException {
        CategoryDAOLL.delete();
    }

    @Test
    public void testRead() throws Exception {
        Category c = new Category(1, "title", 0);
        CategoryDAO.create(c);
        Vector result = CategoryDAOLL.read();
        assertEquals(true, result.size() == 1);
    }

    @Test
    public void testCreate() throws Exception {
        Category c = new Category(1, "'title'", 0);
        CategoryDAOLL.create(c);
    }

    @Test
    public void testGetNextId() throws Exception {
        Category c = new Category(123, "title", 0);
        CategoryDAO.create(c);
        int result = CategoryDAOLL.getNextId();
        assertEquals(124, result);
    }

    @Test
    public void testDelete() throws Exception {
        Category c = new Category(123, "title", 0);
        CategoryDAO.create(c);
        CategoryDAOLL.delete(c);
        assertEquals(true, CategoryDAOLL.read().isEmpty());
    }

}