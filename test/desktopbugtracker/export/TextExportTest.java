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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextExportTest {

    public TextExportTest() {
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
    public void testWrite() throws TextExportException, FileNotFoundException, NoSuchAlgorithmException, IOException {
        ProjectPaperData ppd = new ProjectPaperData(new Project("Desktop Bug Tracker", 0), null, TypeOfBug.ALL_BUGS,
                new Priority(0));
        TextExport textExport = new TextExport("test.txt", ppd);
        textExport.write();
        String OS  = System.getProperty("os.name");
        if(OS.equalsIgnoreCase("Linux")) {
            assertEquals("7a82b692a8cac013119ea13f69e4d4b6", getMD5Checksum("test.txt"));
        } else {
            assertEquals("b6098418738aec152e0ce552b57a476d", getMD5Checksum("test.txt"));
        }
        
    }

    private static String getMD5Checksum(String filename) throws FileNotFoundException, NoSuchAlgorithmException,
            IOException  {
        byte[] b = createChecksum(filename);
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    private static byte[] createChecksum(String filename) throws FileNotFoundException, NoSuchAlgorithmException,
            IOException {
        InputStream fis = new FileInputStream(filename);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    
}
