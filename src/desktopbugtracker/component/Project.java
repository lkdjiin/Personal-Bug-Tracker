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

import desktopbugtracker.component.dto.ProjectDTO;
import desktopbugtracker.I18N;
import desktopbugtracker.dao.ProjectDAO;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Project extends ProjectDTO implements ArchivableDomain {

    public Project(String pro_name, int archive) {
        super(pro_name, archive);
    }

    public Project(String pro_name) {
        super(pro_name, 0);
    }

    public Project(ProjectDTO project) {
        super(project.pro_name, project.archive);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        boolean ret = true;
        if (!(((Project) o).pro_name).equals(this.pro_name)) {
            ret = false;
        }
        return ret;
    }

    public String getTitleFormatedForPaper() {
        return I18N.getBundle("Project_") + " : " + pro_name;
    }

    public static Vector<Project> readWithoutArchive() {
        try {
            return ProjectDAO.read("archive", 0);
        } catch (SQLException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Project>();
        }
    }

    public void archive() {
        try {
            ProjectDAO.archive(this.pro_name);
        } catch (SQLException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unarchive() {
        try {
            ProjectDAO.unarchive(this.pro_name);
        } catch (SQLException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
