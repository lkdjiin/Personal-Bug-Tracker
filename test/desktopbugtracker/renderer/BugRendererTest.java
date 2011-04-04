/*
 *   This file is part of Personal Bug Tracker.
 *   Copyright 2010, Xavier Nayrac <xavier.nayrac@gmail.com>.
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

package desktopbugtracker.renderer;

import desktopbugtracker.component.Bug;
import java.awt.Component;
import javax.swing.JList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BugRendererTest {

    public BugRendererTest() {
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

    @Test
    public void testConstructor() {
        BugRenderer br = new BugRenderer();
        assertEquals(true, br != null);
    }

    @Test
    public void testGetListCellRendererComponent() {
        JList list = new JList();
        BugRenderer br = new BugRenderer();
        Bug b = new Bug(1, "titre", 0, 0, "", "", 0, "", 0);
        Component c = br.getListCellRendererComponent(list, b, 0, true, true);

        assertEquals("titre", br.getText());
    }

    @Test
    public void testGetListCellRendererComponent2() {
        JList list = new JList();
        BugRenderer br = new BugRenderer();
        Component c = br.getListCellRendererComponent(list, new Object(), 0, true, true);

        assertEquals("", br.getText());
    }
}