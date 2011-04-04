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

import desktopbugtracker.component.*;
import desktopbugtracker.data.ApplicationDirectory;
import desktopbugtracker.tools.*;
import java.io.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PdfExportTest {

    public PdfExportTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_REAL_DATA_USE);
        new ApplicationDirectory();
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

    @Test
    public void testWrite() throws Exception {
        ProjectPaperData ppd = new ProjectPaperData(new Project("Desktop Bug Tracker", 0), null, TypeOfBug.ALL_BUGS,
                new Priority(0));
        PdfExport pdfExport = new PdfExport("test.pdf", ppd);
        pdfExport.write();
        // Want to test the md5 but impossible because md5 changes everytime the file is recreated. Certainly due to
        // a date field inserted into it by the iText library.
        assertEquals(3623, new File("test.pdf").length());
    }

//    @Test
//    public void testWriteArchive() throws Exception {
//        ArchivedProjectPaperData appd = new ArchivedProjectPaperData(new ArchivedProject("Test archivage", 1249464049499L, "1.3"));
//        PdfExport pdfExport = new PdfExport("testarchive.pdf", appd);
//        pdfExport.write();
//    }

}
