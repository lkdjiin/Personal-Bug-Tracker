/*
 *  This file is part of Personal Bug Tracker.
 *  Copyright (C) 2009, Xavier Nayrac
 *
 *  Personal Bug Tracker is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package desktopbugtracker.component;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GlobalTest {

    public GlobalTest() {
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
    public void testEqualsNull() {
        Global instance = new Global("1.2");
        assertEquals(false, instance.equals(null));
    }

    @Test
    public void testEqualsBadType() {
        Global instance = new Global("1.2");
        boolean result = instance.equals(new Integer(123));
        assertEquals(false, result);
    }

    @Test
    public void testEqualsNotOk() {
        Global instance = new Global("1.2");
        boolean result = instance.equals(new Global("1.3"));
        assertEquals(false, result);
    }

    @Test
    public void testEqualsOk() {
        Global instance = new Global("1.2");
        boolean result = instance.equals(new Global("1.2"));
        assertEquals(true, result);
    }

    @Test
    public void testToString() {
        Global instance = new Global("1.3");
        assertEquals("1.3", instance.toString());
    }
}