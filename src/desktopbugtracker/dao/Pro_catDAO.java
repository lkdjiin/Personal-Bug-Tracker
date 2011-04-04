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

public class Pro_catDAO extends Pro_catDAOLL {

    public static Vector<Integer> getCategoryID(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Integer> v = new Vector<Integer>();
        Statement statement = connection.createStatement();
        String requete = "select * from pro_cat where pro_name=" + "'" + projectName.replaceAll("'", "''") + "'" + ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(rs.getInt("cat_id"));
        }
        rs.close();
        return v;
    }

    public static void updateProjectName(String oldName, String newName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update pro_cat set pro_name = ? where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, newName);
        ps.setString(2, oldName);
        ps.executeUpdate();
    }

    public static void deleteWhereProject(Project project) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from pro_cat where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, project.pro_name);
        ps.executeUpdate();
    }

    public static void archive(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update pro_cat set archive = 1 where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, projectName);
        ps.executeUpdate();
    }

    public static void unarchive(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update pro_cat set archive = 0 where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, projectName);
        ps.executeUpdate();
    }
}
