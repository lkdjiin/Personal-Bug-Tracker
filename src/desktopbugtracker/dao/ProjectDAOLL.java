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

public class ProjectDAOLL {

    public static void create(Project project) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "insert into project values(?, ?);";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, project.pro_name);
        ps.setInt(2, project.archive);
        ps.executeUpdate();
    }

    /**
     * Lire tous les objets Project.
     */
    public static Vector<Project> read() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Project> v = new Vector<Project>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from project order by pro_name;");
        while (rs.next()) {
            v.add(new Project(rs.getString("pro_name"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    /**
     * Lire un ou plusieurs objets Project, trié par alpha.
     * Cette méthode renvoie les objets Project dont la valeur dans
     * la colonne colname est égale à value.
     */
    public static Vector<Project> read(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Project> v = new Vector<Project>();
        Statement statement = connection.createStatement();
        String requete = "select * from project where " + colname + " = ";
        if (value instanceof String) {
            requete += "'" + ((String) value).replaceAll("'", "''") + "'";
        } else if (value instanceof Integer) {
            requete += value;
        } else if (value instanceof Double) {
            requete += value;
        } else {
            requete += "null";
        }
        requete += " order by pro_name;";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(new Project(rs.getString("pro_name"), rs.getInt("archive")));
        }
        rs.close();
        return v;
    }

    /**
     * Remplacer chaque ligne de la table où pro_name = <code>oldName</code>
     * par <code>newName</code>.
     */
    public static void update(String oldName, String newName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update project set pro_name = ? where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, newName);
        ps.setString(2, oldName);
        ps.executeUpdate();
    }

    public static void archive(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update project set archive = 1 where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, projectName);
        ps.executeUpdate();
    }

    public static void unarchive(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update project set archive = 0 where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, projectName);
        ps.executeUpdate();
    }

    /**
     * Supprimer tous les objets Project.
     */
    public static void delete() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from project;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    /**
     * Cette méthode efface les objets Project dont la valeur dans
     * la colonne colname est égale à value.
     */
    public static void delete(String colname, Object value) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = String.format("delete from project where %s = ?;", colname);
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setObject(1, value);
        ps.executeUpdate();
    }

    /**
     * Cette méthode renvoie l'objet Project dont la clé
     * primaire correspond à pk.
     *
     * @return un objet Project si pk est trouvé ou null si pk n'existe pas
     */
    public static Project readPk(String pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Project v = null;
        Statement statement = connection.createStatement();
        String requete = String.format("select * from project where pro_name = '%s';", pk.replaceAll("'", "''"));
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v = new Project(rs.getString("pro_name"), rs.getInt("archive"));
        }
        rs.close();
        return v;
    }

    /**
     * Cette méthode efface l'objet Project dont la clé primaire est égale à pk.
     */
    public static void deletePk(String pk) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "delete from project where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, pk);
        ps.executeUpdate();
    }
}
