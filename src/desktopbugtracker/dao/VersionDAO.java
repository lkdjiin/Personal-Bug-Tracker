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

import desktopbugtracker.component.Version;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.*;

public class VersionDAO extends VersionDAOLL {

    /**
     * Remplacer chaque ligne de la table où pro_name = oldName par newName.
     */
    public static void updateProjectName(String oldName, String newName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update version set pro_name = ? where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, newName);
        ps.setString(2, oldName);
        ps.executeUpdate();
    }

    public static void archive(Version v) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update version set archive = 1 where pro_name = ? and ver_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, v.pro_name);
        ps.setString(2, v.ver_name);
        ps.executeUpdate();
    }

    public static void unarchive(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update version set archive = 0 where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, projectName);
        ps.executeUpdate();
    }

    public static Vector<Version> readArchiveFrom(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Version> v = new Vector<Version>();
        String requete = String.format("select * from version where archive = 1 and pro_name = '%s';", projectName.replaceAll("'", "''"));
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next())
            v.add(new Version(rs.getString("ver_name"), rs.getString("pro_name"), rs.getInt("archive")));
        rs.close();
        return v;
    }
}
