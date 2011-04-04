/*
 *   This file is part of Personal Bug Tracker.
 *   Copyright 2010, Xavier Nayrac <xavier.nayrac@gmail.com>.
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

import desktopbugtracker.component.Attachment;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class AttachmentDAOLL {

    public static void create(Attachment a) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into attachment values(?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, a.id);
        ps.setString(2, a.name);
        ps.setInt(3, a.archive);
        ps.setInt(4, a.mes_id);
        ps.executeUpdate();
    }

    public static Vector<Attachment> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Attachment> v = new Vector<Attachment>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from attachment;");
        while (rs.next()) {
            v.add(new Attachment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
        }
        rs.close();
        return v;
    }

    public static Vector<Attachment> read(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Attachment> v = new Vector<Attachment>();
        Statement statement = connection.createStatement();
        String requete = "select * from attachment where " + colname + " = ";
        if (value instanceof String) {
            requete += "'" + ((String) value).replaceAll("'", "''") + "'";
        } else if (value instanceof Integer) {
            requete += value;
        } else {
            requete += "null";
        }
        requete += " order by id;";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(new Attachment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
        }
        rs.close();
        return v;
    }

    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from attachment;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    public static void delete(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from attachment where " + colname + " =";
        if (value instanceof String) {
            requete += "'" + ((String) value).replaceAll("'", "''") + "'";
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

    public static void createPk(Attachment a) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into attachment values(null,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, a.name);
        ps.setInt(2, a.archive);
        ps.setInt(3, a.mes_id);
        ps.executeUpdate();
    }

    public static int getNextId() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select id from attachment order by id desc limit 1;");
        int nextId = 0;
        if (rs.next()) {
            nextId = rs.getInt(1);
        }
        nextId += 1;
        rs.close();
        return nextId;
    }

    public static Attachment readPk(Integer pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Attachment v = null;
        Statement statement = connection.createStatement();
        String requete = "select * from attachment where id = ";
        requete += pk;
        requete += ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v = new Attachment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
        }
        rs.close();
        return v;
    }

    public static void deletePk(Integer pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from attachment where id = ";
        requete += pk;
        requete += ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    public static void deletePk(Attachment a) throws SQLException {
        deletePk(a.id);
    }
}
