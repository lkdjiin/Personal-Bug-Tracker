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

import desktopbugtracker.component.Priority;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class PriorityDAOLL {

    public static void create(Priority priority) throws SQLException {
        assert priority != null;
        assert priority.pri_number >= 0;
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into priority values(?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, priority.pri_number);
        ps.executeUpdate();
    }

    /**
     * Lire tous les objets Priority.
     */
    public static Vector<Priority> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Priority> v = new Vector<Priority>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from priority;");
        while (rs.next())
            v.add(new Priority(rs.getInt("pri_number")));
        rs.close();
        return v;
    }

    public static Priority readPk(int pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Priority p = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from priority where pri_number = " + pk + ";");
        if (rs.next())
            p = new Priority(rs.getInt("pri_number"));
        rs.close();
        return p;
    }

    /**
     * Supprimer tous les objets Priority.
     */
    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from priority;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Efface les objets Priority dont la valeur dans la colonne colname est égale à value.
     */
    public static void delete(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = String.format("delete from priority where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setObject(1, value);
        ps.executeUpdate();
    }

    /**
     * Efface l'objet Priority dont la clé primaire est égale à pk.
     */
    public static void deletePk(Integer pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from priority where pri_number = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, pk);
        ps.executeUpdate();
    }

    public static void deletePk(Priority priority) throws SQLException {
        deletePk(priority.pri_number);
    }
}
