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
package desktopbugtracker.dao;

import desktopbugtracker.component.Project;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class ProjectDAO extends ProjectDAOLL {

    /**
     * Test if a project named <code>name</code> exists.
     */
    public static boolean exists(String name) throws SQLException {
        Vector<Project> v = read("pro_name", name);
        if (v.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isArchived(String name) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Statement statement = connection.createStatement();
        String requete = String.format("select * from project where archive = 1 and pro_name = '%s';", name.replaceAll("'", "''"));
        ResultSet rs = statement.executeQuery(requete);
        boolean ret = false;
        if (rs.next()) {
            ret = true;
        }
        rs.close();
        return ret;
    }

    public static Vector<Project> readArchivedProjects() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Project> v = new Vector<Project>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from project where archive=1 order by pro_name;");
        while (rs.next()) {
            v.add(new Project(rs.getString("pro_name"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }
}
