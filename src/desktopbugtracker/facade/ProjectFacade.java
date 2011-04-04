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
package desktopbugtracker.facade;

import desktopbugtracker.component.*;
import desktopbugtracker.dao.*;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.logging.*;

//@todo Il y a du code à factoriser
public class ProjectFacade {
    public static boolean rename(Project project, String newName) throws SqlTransactionException {
        boolean succes = true;
        Sqlite.beginTransaction(Db.DATABASE);
        try {
            ProjectDAO.update(project.pro_name, newName);
            VersionDAO.updateProjectName(project.pro_name, newName);
            BugDAO.updateProjectName(project.pro_name, newName);
            Pro_catDAO.updateProjectName(project.pro_name, newName);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectFacade.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        commitOrRollback(succes);
        Sqlite.endTransaction(Db.DATABASE);
        return succes;
    }

    private static void commitOrRollback(boolean status) throws SqlTransactionException {
        if (status) {
            Sqlite.commit(Db.DATABASE);
        } else {
            Sqlite.rollback(Db.DATABASE);
        }
    }

    public static boolean delete(Project project) throws SqlTransactionException {
        boolean succes = true;
        Sqlite.beginTransaction(Db.DATABASE);
        try {
            for (Bug b : BugDAO.read("pro_name", project.pro_name)) {
                for (Message m : MessageDAO.read("bug_id", b.bug_id)) {
                    MessageDAO.deletePk(m);
                    for(Attachment a : AttachmentDAO.read("mes_id", m.mes_id)) {
                        a.delete();
                    }
                }
            }
            for(Category c : Category.read(project)) {
                c.delete();
            }
            Pro_catDAO.deleteWhereProject(project);
            VersionDAO.delete("pro_name", project.pro_name);
            BugDAO.delete("pro_name", project.pro_name);
            ProjectDAO.delete("pro_name", project.pro_name);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectFacade.class.getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }
        commitOrRollback(succes);
        Sqlite.endTransaction(Db.DATABASE);
        return succes;
    }
}
