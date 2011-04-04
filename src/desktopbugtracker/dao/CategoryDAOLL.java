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

import desktopbugtracker.component.Category;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class CategoryDAOLL {

    public static Vector<Category> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Category> v = new Vector<Category>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from category;");
        while (rs.next()) {
            v.add(new Category(rs.getInt("cat_id"), rs.getString("cat_title"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    public static void create(Category category) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into category values(";
        requete += category.cat_id;
        requete += ", ";
        requete += "'" + category.cat_title.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += category.archive;
        requete += ");";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    public static int getNextId() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select cat_id from category order by cat_id desc limit 1;");
        int nextId = 0;
        if (rs.next()) {
            nextId = rs.getInt(1);
        }
        nextId += 1;
        rs.close();
        return nextId;
    }

    public static void delete(Category category) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from category where cat_id=" + category.cat_id + ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from category");
    }
}
