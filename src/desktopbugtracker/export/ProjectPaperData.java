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
package desktopbugtracker.export;

import desktopbugtracker.I18N;
import desktopbugtracker.component.*;
import desktopbugtracker.dao.*;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.*;

public class ProjectPaperData implements ReportData {

    private Project project;
    private Version version;
    private TypeOfBug typeOfBug;
    private Priority priority;
    private Vector<Bug> bugs;

    public ProjectPaperData(Project pro, Version ver, TypeOfBug tob, Priority pri) {
        bugs = new Vector<Bug>();
        project = pro;
        version = ver;
        typeOfBug = tob;
        priority = pri;
        fillBugsAndVersion();
    }

    private void fillBugsAndVersion() {
        try {
            if (typeOfBug == TypeOfBug.ALL_BUGS) {
                bugs = BugDAO.readByProjectOrderByPriority(project);
            } else if (typeOfBug == TypeOfBug.OPENED_BUGS) {
                bugs = BugDAO.readOpenedByProjectOrderByPriority(project);
            } else if (typeOfBug == TypeOfBug.CLOSED_BUGS) {
                bugs = BugDAO.readClosedByProjectOrderByPriority(project);
            } else if (typeOfBug == TypeOfBug.OPENED_BUGS_OF_ONE_PRIORITY) {
                bugs = BugDAO.readOpenedByProjectAndPriority(project, priority.pri_number);
            } else if (typeOfBug == TypeOfBug.CLOSED_BUGS_OF_ONE_PRIORITY) {
                bugs = BugDAO.readClosedByProjectAndPriority(project, priority.pri_number);
            } else if (typeOfBug == TypeOfBug.OPENED_BUGS_IN_A_VERSION) {
                bugs = BugDAO.readOpenedByProjectAndVersion(project, version);
            } else if (typeOfBug == TypeOfBug.CLOSED_BUGS_IN_A_VERSION) {
                bugs = BugDAO.readClosedByProjectAndVersion(project, version);
            }
        } catch (SQLException ex) {
                Logger.getLogger(ProjectPaperData.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public String getProjectTitle() {
        return project.getTitleFormatedForPaper();
    }

    public String getTypeOfBugList() {
        String listType = I18N.getBundle("List_type") + " : ";
        if (typeOfBug == TypeOfBug.ALL_BUGS) {
            listType += I18N.getBundle("All_bugs");
        } else if (typeOfBug == TypeOfBug.OPENED_BUGS) {
            listType += I18N.getBundle("All_opened_bugs");
        } else if (typeOfBug == TypeOfBug.CLOSED_BUGS) {
            listType += I18N.getBundle("All_closed_bugs");
        } else if (typeOfBug == TypeOfBug.OPENED_BUGS_OF_ONE_PRIORITY) {
            listType += I18N.getBundle("Opened_bugs_of_priority") + " " + priority.getI18NDescription();
        } else if (typeOfBug == TypeOfBug.CLOSED_BUGS_OF_ONE_PRIORITY) {
            listType += I18N.getBundle("Closed_bugs_of_priority") + " " + priority.getI18NDescription();
        } else if (typeOfBug == TypeOfBug.OPENED_BUGS_IN_A_VERSION) {
            listType += I18N.getBundle("Bugs_opened_in_version") + " " + version.ver_name;
        } else if (typeOfBug == TypeOfBug.CLOSED_BUGS_IN_A_VERSION) {
            listType += I18N.getBundle("Bugs_closed_in_version") + " " + version.ver_name;
        }
        return listType;
    }

    public Vector<Bug> getListOfBugs() {
        return bugs;
    }

    public Vector<Message> getMessagesFromBug(Bug b) {
        try {
            return MessageDAO.read("bug_id", b.bug_id);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectPaperData.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Message>();
        }
    }
}
