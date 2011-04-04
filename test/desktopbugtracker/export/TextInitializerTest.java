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

package desktopbugtracker.export;

import java.io.PrintWriter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextInitializerTest {

    public TextInitializerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(expected=TextExportException.class)
    public void testConstructorEmptyFilename() throws TextExportException {
        new TextInitializer("");
    }

    @Test(expected=AssertionError.class)
    public void testConstructorNoFilename() throws TextExportException {
        new TextInitializer(null);
    }

    @Test
    public void testConstructorWithoutExt() throws TextExportException {
        TextInitializer ti = new TextInitializer("filename");
        assertEquals("filename.txt", ti.filename);
    }

    @Test
    public void testConstructor() throws TextExportException {
        TextInitializer ti = new TextInitializer("filename.txt");
        assertEquals("filename.txt", ti.filename);
    }

    @Test
    public void testGetDocument() throws Exception {
        TextInitializer ti = new TextInitializer("filename.txt");
        PrintWriter out = ti.getDocument();
        assertEquals(true, out != null);
        out.close();
    }

}