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

package desktopbugtracker.tools;

import desktopbugtracker.component.*;
import desktopbugtracker.dao.*;
import java.sql.*;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArchivingTest {

    public ArchivingTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Sqlite.setup(Db.DATABASE, Db.TEST_USE);
        clearWorkingDatabase();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        clearWorkingDatabase();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws SQLException {
        clearWorkingDatabase();
    }

    private static void clearWorkingDatabase() throws SQLException {
        BugDAO.delete();
        ProjectDAO.delete();
        VersionDAO.delete();
        MessageDAO.delete();
        CategoryDAO.delete();
        Pro_catDAO.delete();
    }

    private void fillWorkingDatabase() throws SQLException {
        CategoryDAO.create(new Category(0, "", 0));

        ProjectDAO.create(new Project("alpha", 0));
        VersionDAO.create(new Version("0.1", "alpha", 0));
        VersionDAO.create(new Version("0.2", "alpha", 0));
        CategoryDAO.create(new Category(1, "catégorie 1", 0));
        Pro_catDAO.create(new Pro_cat("alpha", 1, 0));
        BugDAO.create(new Bug(1, "bug 1", 1234, 4567, "0.1", "0.2", 1, "alpha", 0, 0));
        BugDAO.create(new Bug(2, "bug 2", 789789, 0, "0.2", "", 2, "alpha", 1, 0));
        MessageDAO.create(new Message(1, "text 1 de bug 1", 123456, 123456, 1, 0));
        MessageDAO.create(new Message(2, "text 2 de bug 1", 234567, 234567, 1, 0));
        MessageDAO.create(new Message(3, "text de bug 2", 334567, 334567, 2, 0));

        ProjectDAO.create(new Project("beta", 0));
        VersionDAO.create(new Version("0.1", "beta", 0));
        VersionDAO.create(new Version("0.2", "beta", 0));
        BugDAO.create(new Bug(3, "bug 3", 1234, 4567, "0.1", "0.2", 1, "beta", 0, 0));
        MessageDAO.create(new Message(4, "text 1 de bug 3", 123456, 123456, 3, 0));

        // Archive
        ProjectDAO.create(new Project("omega", 1));
        VersionDAO.create(new Version("0.1", "omega", 1));
        CategoryDAO.create(new Category(2, "catégorie 2", 0));
        Pro_catDAO.create(new Pro_cat("omega", 2, 1));
        BugDAO.create(new Bug(4, "bug 4", 1234, 0, "0.1", "", 1, "omega", 0, 1));
        BugDAO.create(new Bug(5, "bug 5", 4321, 0, "0.1", "", 1, "omega", 0, 1));
        MessageDAO.create(new Message(5, "text de bug 4", 123456, 123456, 4, 1));
        MessageDAO.create(new Message(6, "text de bug 5", 123456, 123456, 5, 1));
    }

    @Test
    public void testArchive() throws SQLException, ArchivingException, SqlTransactionException {
        fillWorkingDatabase();
        Archiving archive = new Archiving(new Project("alpha", 0));
        boolean result = archive.archive();
        assertEquals(true, result);
        assertEquals(true, projectAlphaIsReallyArchived());
    }

    private boolean projectAlphaIsReallyArchived() throws SQLException {
        return isArchivedProject() && isArchivedVersions() && isArchivedBugs() && isArchivedMessages();
    }

    private boolean isArchivedProject() throws SQLException {
        Vector<Project> archProjects = ProjectDAO.readArchivedProjects();
        assertEquals("alpha", archProjects.get(0).pro_name);
        return true;
    }

    private boolean isArchivedVersions() throws SQLException {
        Vector<Version> archVersions = VersionDAO.readArchiveFrom("alpha");
        assertEquals(2, archVersions.size());
        assertEquals("0.1, alpha, 1", archVersions.get(0).toString());
        assertEquals("0.2, alpha, 1", archVersions.get(1).toString());
        return true;
    }

    private boolean isArchivedBugs() throws SQLException {
        Vector<Bug> archBugs = BugDAO.readArchiveFrom("alpha");
        assertEquals(2, archBugs.size());
        assertEquals("1, bug 1, 1234, 4567, 0.1, 0.2, 1, alpha, 0, 1", archBugs.get(0).toString());
        assertEquals("2, bug 2, 789789, 0, 0.2, , 2, alpha, 1, 1", archBugs.get(1).toString());
        return true;
    }

    private boolean isArchivedMessages() throws SQLException {
        Vector<Message> archMessages = MessageDAO.readArchiveFrom("alpha");
        assertEquals(3, archMessages.size());
        assertEquals("1, text 1 de bug 1, 123456, 123456, 1, 1", archMessages.get(0).toString());
        assertEquals("2, text 2 de bug 1, 234567, 234567, 1, 1", archMessages.get(1).toString());
        assertEquals("3, text de bug 2, 334567, 334567, 2, 1", archMessages.get(2).toString());
        return true;
    }

    @Test
    public void testLoadArchivedProject() throws SQLException, SqlTransactionException, ArchivingException {
        fillWorkingDatabase();
        Archiving instance = new Archiving(new Project("omega", 1));
        boolean result = instance.loadArchivedProject();
        assertEquals(true, result);
        assertEquals(true, projectBetaIsReallyReloaded());
    }

    
    private boolean projectBetaIsReallyReloaded() throws SQLException {
        return isReloadedProject() && isReloadedVersion() && isReloadedBug() && isReloadedMessage()
                && isReloadedPro_cat();
    }

    private boolean isReloadedProject() throws SQLException {
        Project p = ProjectDAO.readPk("omega");
        assertEquals(0, p.archive);
        return true;
    }

    private boolean isReloadedVersion() throws SQLException {
        Vector<Version> versions = VersionDAO.read("pro_name", "omega");
        assertEquals(1, versions.size());
        assertEquals("0.1, omega, 0", versions.get(0).toString());
        return true;
    }

    private boolean isReloadedBug() throws SQLException {
        Vector<Bug> bugs = BugDAO.read("pro_name", "omega");
        assertEquals(2, bugs.size());
        assertEquals("4, bug 4, 1234, 0, 0.1, , 1, omega, 0, 0", bugs.get(0).toString());
        assertEquals("5, bug 5, 4321, 0, 0.1, , 1, omega, 0, 0", bugs.get(1).toString());
        return true;
    }

    private boolean isReloadedMessage() throws SQLException {
        Message message = MessageDAO.readPk(5);
        assertEquals("5, text de bug 4, 123456, 123456, 4, 0", message.toString());
        message = MessageDAO.readPk(6);
        assertEquals("6, text de bug 5, 123456, 123456, 5, 0", message.toString());
        return true;
    }

    private boolean isReloadedPro_cat() throws SQLException {
        Vector<Pro_cat> proCats = Pro_catDAO.read();
        assertEquals("alpha, 1, 0", proCats.get(0).toString());
        assertEquals("omega, 2, 0", proCats.get(1).toString());
        return true;
    }
}