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

import desktopbugtracker.component.Priority_i18n;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class Priority_i18nDAOLL {

    public static void create(Priority_i18n priority_i18n) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into priority_i18n values(?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, priority_i18n.pri_number);
        ps.setString(2, priority_i18n.pri_code);
        ps.setString(3, priority_i18n.pri_description);
        ps.executeUpdate();
    }

    /**
     * Lire tous les objets Priority_i18n.
     */
    public static Vector<Priority_i18n> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Priority_i18n> v = new Vector<Priority_i18n>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from priority_i18n;");
        while (rs.next()) {
            v.add(new Priority_i18n(rs.getInt("pri_number"), rs.getString("pri_code"), rs.getString("pri_description")));
        }
        rs.close();
        return v;
    }

    /**
     * Renvoie les objets Priority_i18n dont la valeur dans la colonne colname est égale à value.
     */
    public static Vector<Priority_i18n> read(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Priority_i18n> v = new Vector<Priority_i18n>();
        Statement statement = connection.createStatement();
        String requete = "select * from priority_i18n where " + colname + " = ";
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
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(new Priority_i18n(rs.getInt("pri_number"), rs.getString("pri_code"), rs.getString("pri_description")));
        }
        rs.close();
        return v;
    }

    /**
     * Modifie les objets Priority_i18n dont la valeur dans la colonne colname est égale à value.
     */
    public static void update(Priority_i18n priority_i18n, String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = String.format(
                "update priority_i18n set pri_number = ?, pri_code = ?, pri_description = ? where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, priority_i18n.pri_number);
        ps.setString(2, priority_i18n.pri_code);
        ps.setString(3, priority_i18n.pri_description);
        ps.setObject(4, value);
        ps.executeUpdate();
    }

    /**
     * Supprimer tous les objets Priority_i18n.
     */
    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from priority_i18n;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Efface les objets Priority_i18n dont la valeur dans la colonne colname est égale à value.
     */
    public static void delete(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = String.format("delete from priority_i18n where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setObject(1, value);
        ps.executeUpdate();
    }

    /**
     * Renvoie l'objet Priority_i18n dont les clés primaires correspondent à pri_number, pri_code.
     *
     * @return un objet Priority_i18n si la combinaison de clé primaire est trouvée ou null si elle ne l'est pas
     */
    public static Priority_i18n readPk(Integer pri_number, String pri_code) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Priority_i18n obj = null;
        Statement statement = connection.createStatement();
        String requete = "select * from priority_i18n where pri_number = ";
        requete += pri_number;
        requete += " and pri_code = ";
        requete += "'" + pri_code.replaceAll("'", "''") + "'";
        requete += ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            obj = new Priority_i18n(rs.getInt("pri_number"), rs.getString("pri_code"),
                    rs.getString("pri_description"));
        }
        rs.close();
        return obj;
    }

    /**
     * Tous les attributs peuvent être modifiés, à part les clés primaires.
     */
    public static void updatePk(Priority_i18n priority_i18n) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update priority_i18n set pri_number = ?, pri_code = ?, pri_description = ? ";
        requete += "where pri_number = ? and pri_code = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, priority_i18n.pri_number);
        ps.setString(2, priority_i18n.pri_code);
        ps.setString(3, priority_i18n.pri_description);
        ps.setInt(4, priority_i18n.pri_number);
        ps.setString(5, priority_i18n.pri_code);
        ps.executeUpdate();
    }

    /**
     * Efface l'objet Priority_i18n qui correspond aux clés primaires pri_number, pri_code.
     */
    public static void deletePk(Integer pri_number, String pri_code) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from priority_i18n where pri_number = ? and pri_code = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, pri_number);
        ps.setString(2, pri_code);
        ps.executeUpdate();
    }
}
