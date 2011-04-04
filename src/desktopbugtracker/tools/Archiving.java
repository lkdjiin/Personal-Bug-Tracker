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
package desktopbugtracker.tools;

import desktopbugtracker.component.*;
import desktopbugtracker.dao.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.*;

public class Archiving {

    private Project project;

    public Archiving(Project project) {
        assert project != null;
        this.project = project;
    }

    public boolean archive() throws SqlTransactionException {
        Sqlite.beginTransaction(Db.DATABASE);
        boolean success = true;
        try {
            project.archive();
            archiveVersions();
            archiveBugsAndAssociatedBuisness();
        } catch (SQLException ex) {
            Logger.getLogger(Archiving.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        if (success) {
            Sqlite.commit(Db.DATABASE);
        } else {
            Sqlite.rollback(Db.DATABASE);
        }
        Sqlite.endTransaction(Db.DATABASE);
        return success;
    }

    private void archiveVersions() throws SQLException {
        for (Version v : VersionDAO.read("pro_name", project.pro_name)) {
            v.archive();
        }
    }

    private void archiveBugsAndAssociatedBuisness() throws SQLException {
        BugDAO.archive(project.pro_name);
        Pro_catDAO.archive(project.pro_name);
        for (Bug bug : BugDAO.read("pro_name", project.pro_name)) {
            MessageDAO.archive(bug.bug_id);
            Vector<Message> messages = MessageDAO.read("bug_id", bug.bug_id);
            for (Message m : messages) {
                m.archive();
            }
        }
    }

    public boolean loadArchivedProject() throws SqlTransactionException {
        boolean success = true;
        Sqlite.beginTransaction(Db.DATABASE);
        try {
            project.unarchive();
            VersionDAO.unarchive(project.pro_name);
            reloadBugsAndAssociatedBuisness();
        } catch (SQLException ex) {
            Logger.getLogger(Archiving.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        if (success) {
            Sqlite.commit(Db.DATABASE);
        } else {
            Sqlite.rollback(Db.DATABASE);
        }
        Sqlite.endTransaction(Db.DATABASE);
        return success;
    }

    private void reloadBugsAndAssociatedBuisness() throws SQLException {
        BugDAO.unarchive(project.pro_name);
        Pro_catDAO.unarchive(project.pro_name);
        for (Bug bug : BugDAO.read("pro_name", project.pro_name)) {
            MessageDAO.unarchive(bug.bug_id);
            Vector<Message> messages = MessageDAO.read("bug_id", bug.bug_id);
            for(Message m : messages) {
                m.unarchive();
            }
        }
    }

}
