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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class CategoryDAOTest {

    public CategoryDAOTest() {
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
        clearDatabase();
        CategoryDAO.create(new Category(0, "", 0)); // Catégorie par défaut
    }

    @After
    public void tearDown() throws SQLException {
        clearDatabase();
    }

    public void clearDatabase() throws SQLException {
            CategoryDAO.delete();
            Pro_catDAO.delete();
            ProjectDAO.delete();
            BugDAO.delete();
    }

    public void fillDatabase() {
        try {
            ProjectDAO.create(new Project("alpha", 0));
            ProjectDAO.create(new Project("beta", 0));

            Pro_catDAO.create(new Pro_cat("alpha", 11, 0));
            Pro_catDAO.create(new Pro_cat("alpha", 12, 0));

            Pro_catDAO.create(new Pro_cat("beta", 21, 0));
            Pro_catDAO.create(new Pro_cat("beta", 22, 0));

            CategoryDAO.create(new Category(11, "catego 1 de alpha", 0));
            CategoryDAO.create(new Category(12, "catego 2 de alpha", 0));

            CategoryDAO.create(new Category(21, "catego 1 de beta", 0));
            CategoryDAO.create(new Category(22, "catego 2 de beta", 0));
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillDatabaseWithBugs() throws SQLException {
        BugDAO.create(new Bug(1, "bug1", 0, 0, "0.1", "", 1, "alpha", 11, 0));
    }

    @Test
    public void testRead_Connection_String() throws Exception {
        fillDatabase();
        Vector result = CategoryDAO.read("alpha");
        assertEquals(3, result.size());
        assertEquals("catego 1 de alpha", ((Category)result.get(1)).cat_title);
        assertEquals("catego 2 de alpha", ((Category)result.get(2)).cat_title);
    }

    @Test
    public void testRead_Connection_int() throws Exception {
        fillDatabase();
        Category result = CategoryDAO.read(11);
        assertEquals("catego 1 de alpha", result.cat_title);
    }

    @Test
    public void testCreateAlongWithPro_cat() throws Exception {
        fillDatabase();
        CategoryDAO.createAlongWithPro_cat(new Category(0, "catego 3 de alpha", 0), new Project("alpha", 0));
        assertEquals("catego 3 de alpha", CategoryDAO.read(23).cat_title);
        assertEquals(3, Pro_catDAO.getCategoryID("alpha").size());
    }

    @Test
    public void testIsUsedCategory() throws Exception {
        fillDatabase();
        fillDatabaseWithBugs();
        Project p = new Project("alpha", 0);
        Category c1 = new Category(11, "catego 1 de alpha", 0);
        Category c2 = new Category(12, "catego 2 de alpha", 0);
        assertEquals(true, CategoryDAO.isUsedCategory(p, c1));
        assertEquals(false, CategoryDAO.isUsedCategory(p, c2));
    }

}