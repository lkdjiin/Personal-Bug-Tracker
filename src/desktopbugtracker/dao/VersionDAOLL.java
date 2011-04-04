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

public class VersionDAOLL {

    public static void create(Version version) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into version values(?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, version.ver_name);
        ps.setString(2, version.pro_name);
        ps.setInt(3, version.archive);
        ps.executeUpdate();
    }

    /**
     * Lire tous les objets Version.
     */
    public static Vector<Version> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Version> v = new Vector<Version>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from version;");
        while (rs.next()) {
            v.add(new Version(rs.getString("ver_name"), rs.getString("pro_name"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    public static Vector<Version> read(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Version> v = new Vector<Version>();
        Statement statement = connection.createStatement();
        String requete = "select * from version where " + colname + " = ";
        if (value instanceof String)
            requete += "'" + ((String) value).replaceAll("'", "''") + "'";
        else if (value instanceof Integer)
            requete += value;
        else if (value instanceof Double)
            requete += value;
        else
            requete += "null";
        requete += ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next())
            v.add(new Version(rs.getString("ver_name"), rs.getString("pro_name"), rs.getInt("archive")));
        rs.close();
        return v;
    }

    /**
     * Modifie les objets Version dont la valeur dans la colonne colname est égale à value.
     */
    public static void update(Version version, String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = String.format("update version set ver_name = ?, pro_name = ? where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, version.ver_name);
        ps.setString(2, version.pro_name);
        ps.setObject(3, value);
        ps.executeUpdate();
    }

    /**
     * Supprimer tous les objets Version.
     */
    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from version;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Efface les objets Version dont la valeur dans la colonne colname est égale à value.
     */
    public static void delete(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = String.format("delete from version where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setObject(1, value);
        ps.executeUpdate();
    }

    /**
     * Renvoie l'objet Version dont les clés primaires correspondent à ver_name, pro_name.
     * @return un objet Version si la combinaison de clé primaire est trouvée ou null si elle ne l'est pas
     */
    public static Version readPk(String ver_name, String pro_name) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Version obj = null;
        Statement statement = connection.createStatement();
        String requete = "select * from version where ver_name = ";
        requete += "'" + ver_name.replaceAll("'", "''") + "'";
        requete += " and pro_name = ";
        requete += "'" + pro_name.replaceAll("'", "''") + "'";
        requete += ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            obj = new Version(
                    rs.getString("ver_name"),
                    rs.getString("pro_name"), rs.getInt("archive"));
        }
        rs.close();
        return obj;
    }

    /**
     * Efface l'objet Version qui correspond aux clés primaires ver_name, pro_name.
     */
    public static void deletePk(String ver_name, String pro_name) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from version where ver_name = ? and pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, ver_name);
        ps.setString(2, pro_name);
        ps.executeUpdate();
    }

    public static void deletePk(Version v) throws SQLException {
        deletePk(v.ver_name, v.pro_name);
    }
}
