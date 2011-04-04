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

import desktopbugtracker.component.Message;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class MessageDAOLL {

    public static void create(Message message) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into message values(?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, message.mes_id);
        ps.setString(2, message.mes_text);
        ps.setLong(3, message.mes_date);
        ps.setLong(4, message.mes_time);
        ps.setInt(5, message.bug_id);
        ps.setInt(6, message.archive);
        ps.executeUpdate();
    }

    /**
     * Lire tous les objets Message.
     */
    public static Vector<Message> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Message> v = new Vector<Message>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from message;");
        while (rs.next()) {
            v.add(new Message(rs.getInt("mes_id"), rs.getString("mes_text"), rs.getLong("mes_date"),
                    rs.getLong("mes_time"), rs.getInt("bug_id"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    /**
     * Renvoie les objets Message dont la valeur dans la colonne colname est égale à value.
     */
    public static Vector<Message> read(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Message> v = new Vector<Message>();
        Statement statement = connection.createStatement();
        String requete = "select * from message where " + colname + " = ";
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
            v.add(new Message(rs.getInt("mes_id"), rs.getString("mes_text"), rs.getLong("mes_date"),
                    rs.getLong("mes_time"), rs.getInt("bug_id"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    /**
     * Modifie les objets Message dont la valeur dans la colonne colname est égale à value.
     */
    public static void update(Message message, String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update message set mes_id = ?, mes_text = ?, mes_date = ?, mes_time = ?, bug_id = ?, archive = ? ";
        requete += String.format("where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, message.mes_id);
        ps.setString(2, message.mes_text);
        ps.setLong(3, message.mes_date);
        ps.setLong(4, message.mes_time);
        ps.setInt(5, message.bug_id);
        ps.setInt(6, message.archive);
        ps.setObject(7, value);
        ps.executeUpdate();
    }

    /**
     * Supprimer tous les objets Message.
     */
    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from message;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Efface les objets Message dont la valeur dans la colonne colname est égale à value.
     */
    public static void delete(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = String.format("delete from message where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setObject(1, value);
        ps.executeUpdate();
    }

    /**
     * Enregistrer l'objet Message dans la base de données en spécifiant null comme
     * clé primaire, ce qui active l'auto-incrémentation dans sqlite3.
     *
     * Vous pouvez entrer une valeur quelconque pour la colonne servant de clé
     * primaire à l'objet Message, celle-ci sera de toute façon ignorée.
     */
    public static void createPk(Message message) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into message values(null, ?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, message.mes_text);
        ps.setLong(2, message.mes_date);
        ps.setLong(3, message.mes_time);
        ps.setInt(4, message.bug_id);
        ps.setInt(5, message.archive);
        ps.executeUpdate();
    }

    /**
     * Obtenir la valeur de la prochaine clé primaire.
     */
    public static int getNextId() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select mes_id from message order by mes_id desc limit 1;");
        int nextId = 0;
        if (rs.next())
            nextId = rs.getInt(1);
        nextId += 1;
        rs.close();
        return nextId;
    }

    /**
     * Renvoie l'objet Message dont la clé primaire correspond à pk.
     */
    public static Message readPk(Integer pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Message v = null;
        Statement statement = connection.createStatement();
        String requete = "select * from message where mes_id = ";
        requete += pk;
        requete += ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v = new Message(rs.getInt("mes_id"), rs.getString("mes_text"), rs.getLong("mes_date"),
                    rs.getLong("mes_time"), rs.getInt("bug_id"), rs.getInt("archive"));
        }
        rs.close();
        return v;
    }

    /**
     * Tous les attributs peuvent être modifiés, à part la clé primaire.
     */
    public static void updatePk(Message message) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update message set mes_id = ?, mes_text = ?, mes_date = ?, mes_time = ?, bug_id = ?, archive = ? ";
        requete += "where mes_id = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, message.mes_id);
        ps.setString(2, message.mes_text);
        ps.setLong(3, message.mes_date);
        ps.setLong(4, message.mes_time);
        ps.setInt(5, message.bug_id);
        ps.setInt(6, message.archive);
        ps.setInt(7, message.mes_id);
        ps.executeUpdate();
    }

    /**
     * Efface l'objet Message dont la clé primaire est égale à pk.
     */
    public static void deletePk(Integer pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from message where mes_id = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, pk);
        ps.executeUpdate();
    }

    public static void deletePk(Message message) throws SQLException {
        deletePk(message.mes_id);
    }
}
