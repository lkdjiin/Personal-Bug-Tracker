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

import desktopbugtracker.component.Pro_cat;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class Pro_catDAOLL {

    public static void create(Pro_cat pro_cat) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into pro_cat values(?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, pro_cat.pro_name);
        ps.setInt(2, pro_cat.cat_id);
        ps.setInt(3, pro_cat.archive);
        ps.executeUpdate();
    }

    public static Vector<Pro_cat> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Pro_cat> v = new Vector<Pro_cat>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from pro_cat;");
        while (rs.next()) {
            v.add(new Pro_cat(rs.getString("pro_name"), rs.getInt("cat_id"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    public static void delete(Pro_cat pro_cat) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from pro_cat where pro_name = ? and cat_id = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, pro_cat.pro_name);
        ps.setInt(2, pro_cat.cat_id);
        ps.executeUpdate();
    }

    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from pro_cat;");
    }
}
