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

import com.lowagie.text.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PdfInitializerTest {

    public PdfInitializerTest() {
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

    @Test(expected=PdfExportException.class)
    public void testConstructorEmptyFilename() throws PdfExportException {
        new PdfInitializer("");
    }

    @Test(expected=AssertionError.class)
    public void testConstructorNoFilename() throws PdfExportException {
        new PdfInitializer(null);
    }

    @Test
    public void testConstructorWithoutExt() throws PdfExportException {
        PdfInitializer pi = new PdfInitializer("filename");
        assertEquals("filename.pdf", pi.filename);
    }

    @Test
    public void testConstructor() throws PdfExportException {
        PdfInitializer pi = new PdfInitializer("filename.pdf");
        assertEquals("filename.pdf", pi.filename);
    }

}