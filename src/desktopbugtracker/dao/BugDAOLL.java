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

import desktopbugtracker.component.Bug;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class BugDAOLL {

    public static void create(Bug bug) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into bug values(?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, bug.bug_id);
        ps.setString(2, bug.bug_title);
        ps.setLong(3, bug.bug_date_opened);
        ps.setLong(4, bug.bug_date_closed);
        ps.setString(5, bug.bug_version_opened);
        ps.setString(6, bug.bug_version_closed);
        ps.setInt(7, bug.pri_number);
        ps.setString(8, bug.pro_name);
        ps.setInt(9, bug.cat_id);
        ps.setInt(10, bug.archive);
        ps.executeUpdate();
    }

    /**
     * Lire tous les objets Bug.
     */
    public static Vector<Bug> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from bug;");
        while (rs.next()) {
            v.add(new Bug(rs.getInt("bug_id"), rs.getString("bug_title"), rs.getLong("bug_date_opened"),
                    rs.getLong("bug_date_closed"), rs.getString("bug_version_opened"), rs.getString("bug_version_closed"),
                    rs.getInt("pri_number"), rs.getString("pro_name"), rs.getInt("cat_id"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    public static Vector<Bug> read(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where " + colname + " = ";
        if (value instanceof String) {
            requete += "'" + ((String) value).replaceAll("'", "''") + "'";
        } else if (value instanceof Integer) {
            requete += value;
        } else if (value instanceof Double) {
            requete += value;
        } else {
            requete += "null";
        }
        requete += " order by bug_title;";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(new Bug(rs.getInt("bug_id"), rs.getString("bug_title"), rs.getLong("bug_date_opened"),
                    rs.getLong("bug_date_closed"), rs.getString("bug_version_opened"),
                    rs.getString("bug_version_closed"), rs.getInt("pri_number"), rs.getString("pro_name"),
                    rs.getInt("cat_id"), rs.getInt("archive") ));
        }
        rs.close();
        return v;
    }

    /**
     * Modifie les objets Bug dont la valeur dans la colonne colname est égale à value.
     */
    public static void update(Bug bug, String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update bug set ";
        requete += "bug_id = ";
        requete += bug.bug_id;
        requete += ", ";
        requete += "bug_title = ";
        requete += "'" + bug.bug_title.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "bug_date_opened = ";
        requete += bug.bug_date_opened;
        requete += ", ";
        requete += "bug_date_closed = ";
        requete += bug.bug_date_closed;
        requete += ", ";
        requete += "bug_version_opened = ";
        requete += "'" + bug.bug_version_opened.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "bug_version_closed = ";
        requete += "'" + bug.bug_version_closed.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "pri_number = ";
        requete += bug.pri_number;
        requete += ", ";
        requete += "pro_name = ";
        requete += "'" + bug.pro_name.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "cat_id = ";
        requete += bug.cat_id;
        requete += " where " + colname + " = ";
        if (value instanceof String) {
            requete += "'" + ((String)value).replaceAll("'", "''") + "'";
        } else if (value instanceof Integer) {
            requete += value;
        } else if (value instanceof Double) {
            requete += value;
        } else {
            requete += "null";
        }
        requete += ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Supprimer tous les objets Bug.
     */
    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from bug;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Efface les objets Bug dont la valeur dans la colonne colname est égale à value.
     */
    public static void delete(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from bug where " + colname + " =";
        if (value instanceof String) {
            requete += "'" + ((String)value).replaceAll("'", "''") + "'";
        } else if (value instanceof Integer) {
            requete += value;
        } else if (value instanceof Double) {
            requete += value;
        } else {
            requete += "null";
        }
        requete += ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Enregistrer l'objet Bug dans la base de données en spécifiant null comme
     * clé primaire, ce qui active l'auto-incrémentation dans sqlite3.
     *
     * Vous pouvez entrer une valeur quelconque pour la colonne servant de clé
     * primaire à l'objet Bug, celle-ci sera de toute façon ignorée.
     */
    public static void createPk(Bug bug) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into bug values(";
        requete += "null";
        requete += ", ";
        requete += "'" + bug.bug_title.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += bug.bug_date_opened;
        requete += ", ";
        requete += bug.bug_date_closed;
        requete += ", ";
        requete += "'" + bug.bug_version_opened.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "'" + bug.bug_version_closed.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += bug.pri_number;
        requete += ", ";
        requete += "'" + bug.pro_name.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += bug.cat_id;
        requete += ", ";
        requete += bug.archive;
        requete += ");";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Obtenir la valeur de la prochaine clé primaire.
     */
    public static int getNextId() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select bug_id from bug order by bug_id desc limit 1;");
        int nextId = 0;
        if (rs.next())
            nextId = rs.getInt(1);
        nextId += 1;
        rs.close();
        return nextId;
    }

    /**
     * Renvoie l'objet Bug dont la clé primaire correspond à pk.
     */
    public static Bug readPk(Integer pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Bug v = null;
        Statement statement = connection.createStatement();
        String requete = "select * from bug where bug_id = ";
        requete += pk;
        requete += ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v = new Bug(rs.getInt("bug_id"), rs.getString("bug_title"), rs.getLong("bug_date_opened"),
                    rs.getLong("bug_date_closed"), rs.getString("bug_version_opened"),
                    rs.getString("bug_version_closed"), rs.getInt("pri_number"), rs.getString("pro_name"),
                    rs.getInt("cat_id"), rs.getInt("archive"));
        }
        rs.close();
        return v;
    }

    /**
     * Tous les attributs peuvent être modifiés, à part la clé primaire.
     */
    public static void updatePk(Bug bug) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update bug set ";
        requete += "bug_id = ";
        requete += bug.bug_id;
        requete += ", ";
        requete += "bug_title = ";
        requete += "'" + bug.bug_title.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "bug_date_opened = ";
        requete += bug.bug_date_opened;
        requete += ", ";
        requete += "bug_date_closed = ";
        requete += bug.bug_date_closed;
        requete += ", ";
        requete += "bug_version_opened = ";
        requete += "'" + bug.bug_version_opened.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "bug_version_closed = ";
        requete += "'" + bug.bug_version_closed.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "pri_number = ";
        requete += bug.pri_number;
        requete += ", ";
        requete += "pro_name = ";
        requete += "'" + bug.pro_name.replaceAll("'", "''") + "'";
        requete += ", ";
        requete += "cat_id = ";
        requete += bug.cat_id;
        requete += " where bug_id = ";
        requete += bug.bug_id;
        requete += ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Efface l'objet Bug dont la clé primaire est égale à pk.
     */
    public static void deletePk(Integer pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from bug where bug_id = ";
        requete += pk;
        requete += ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    public static void deletePk(Bug bug) throws SQLException {
        deletePk(bug.bug_id);
    }
}
