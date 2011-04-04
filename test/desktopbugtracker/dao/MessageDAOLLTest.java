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

import desktopbugtracker.component.Message;
import desktopbugtracker.tools.*;
import java.sql.SQLException;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageDAOLLTest {

    public MessageDAOLLTest() {
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
        MessageDAOLL.delete();
    }

    @After
    public void tearDown() throws SQLException {
        MessageDAOLL.delete();
    }

    @Test
    public void testRead_Connection() throws Exception {
        Message m = new Message(1, "text", 0, 0, 1, 0);
        MessageDAOLL.create(m);
        Vector result = MessageDAOLL.read();
        assertEquals(1, result.size());
    }

    @Test
    public void testCreateWithQuote() throws Exception {
        Message m = new Message(1, "'text'", 0, 0, 1, 0);
        MessageDAOLL.create(m);
        assertEquals(1, MessageDAOLL.read().size());
    }

    @Test
    public void testRead_3args() throws Exception {
        MessageDAOLL.create(new Message(1, "'text'", 0, 0, 1, 0));
        Vector<Message> result = MessageDAOLL.read("mes_text", "'text'");
        assertEquals("'text'", result.get(0).mes_text);
    }

    @Test
    public void testUpdate() throws Exception {
        Message m = new Message(1, "'text'", 0, 0, 1, 0);
        MessageDAOLL.create(m);
        m.mes_date = 123456;
        MessageDAOLL.update(m, "mes_text", "'text'");
        assertEquals(123456, MessageDAOLL.read().get(0).mes_date);
    }

    @Test
    public void testDelete_3args() throws Exception {
        MessageDAOLL.create(new Message(1, "'text'", 0, 0, 1, 0));
        MessageDAOLL.delete("mes_text", "'text'");
    }

    @Test
    public void testCreatePkAndReadPk() throws Exception {
        MessageDAOLL.create(new Message(110, "'text110'", 0, 0, 1, 0));
        Message m = new Message(0, "'text111'", 0, 0, 1, 0);
        MessageDAOLL.createPk(m);
        Message result = MessageDAOLL.readPk(111);
        assertEquals("'text111'", result.mes_text);
    }

    @Test
    public void testUpdatePk() throws Exception {
        Message m = new Message(123, "'text111'", 0, 0, 1, 0);
        MessageDAOLL.create(m);
        m.mes_text = "'t'";
        MessageDAOLL.updatePk(m);
        Message result = MessageDAOLL.readPk(123);
        assertEquals("'t'", result.mes_text);
    }

    @Test
    public void testDeletePk_Connection_Integer() throws Exception {
        MessageDAOLL.create(new Message(110, "'text110'", 0, 0, 1, 0));
        MessageDAOLL.deletePk(110);
        assertEquals(true, MessageDAOLL.read().isEmpty());
    }

    @Test
    public void testDeletePk_Connection_Message() throws Exception {
        Message m = new Message(123, "'text111'", 0, 0, 1, 0);
        MessageDAOLL.create(m);
        MessageDAOLL.deletePk(m);
        assertEquals(true, MessageDAOLL.read().isEmpty());
    }

}