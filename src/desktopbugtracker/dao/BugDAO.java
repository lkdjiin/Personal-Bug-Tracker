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

import desktopbugtracker.component.*;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

/**
 * Classe d'accès aux données des objets Bug pour SQLite3.
 *
 * Fichier généré automatiquement par Radical Data beta.1.1.
 * 01 Feb 2009 14:24
 * (C) 2008-2009, Xavier Nayrac
 */
public class BugDAO extends BugDAOLL {

    /**
     * Obtenir les bugs d'un projet, triés par priorité.
     *
     * @param connection connexion à une base de données
     * @param project le projet d'où proviennent les bugs
     * @return un Vector d'objets Bug
     * @throws java.sql.SQLException
     */
    public static Vector<Bug> readByProjectOrderByPriority(Project project) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where pro_name = ";
        requete += "'" + project.pro_name.replaceAll("'", "''") + "'";
        requete += " order by pri_number;";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(getBugFromResultSet(rs));
        }
        rs.close();
        return v;
    }

    /**
     * Obtenir les bugs ouverts d'un projet, triés par priorité.
     *
     * @param connection connexion à une base de données
     * @param project le projet d'où proviennent les bugs
     * @return un Vector d'objets Bug
     * @throws java.sql.SQLException
     */
    public static Vector<Bug> readOpenedByProjectOrderByPriority(Project project) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where pro_name = ";
        requete += "'" + project.pro_name.replaceAll("'", "''") + "'";
        requete += " and bug_date_closed = 0 ";
        requete += " order by pri_number;";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(getBugFromResultSet(rs));
        }
        rs.close();
        return v;
    }

    /**
     * Obtenir les bugs fermés d'un projet, triés par priorité.
     *
     * @param connection connexion à une base de données
     * @param project le projet d'où proviennent les bugs
     * @return un Vector d'objets Bug
     * @throws java.sql.SQLException
     */
    public static Vector<Bug> readClosedByProjectOrderByPriority(Project project) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where pro_name = ";
        requete += "'" + project.pro_name.replaceAll("'", "''") + "'";
        requete += " and bug_date_closed <> 0 ";
        requete += " order by pri_number;";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(getBugFromResultSet(rs));
        }
        rs.close();
        return v;
    }

    /**
     * Obtenir les bugs ouverts d'un projet, d'une certaine priorité.
     *
     * @param connection connexion à une base de données
     * @param project le projet d'où proviennent les bugs
     * @param  priority la priorité à prendre en compte
     * @return un Vector d'objets Bug
     * @throws java.sql.SQLException
     */
    public static Vector<Bug> readOpenedByProjectAndPriority(Project project, int priority) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where pro_name = ";
        requete += "'" + project.pro_name.replaceAll("'", "''") + "'";
        requete += " and bug_date_closed = 0 ";
        requete += " and pri_number = " + priority + ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(getBugFromResultSet(rs));
        }
        rs.close();
        return v;
    }

    public static Vector<Bug> readOpenedByProjectAndVersion(Project project, Version version) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where pro_name = ";
        requete += "'" + project.pro_name.replaceAll("'", "''") + "'";
        requete += " and bug_date_closed = 0 ";
        requete += " and bug_version_opened = '" + version.ver_name.replaceAll("'", "''") + "';";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(getBugFromResultSet(rs));
        }
        rs.close();
        return v;
    }

    public static Vector<Bug> readClosedByProjectAndVersion(Project project, Version version) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where pro_name = ";
        requete += "'" + project.pro_name.replaceAll("'", "''") + "'";
        requete += " and bug_version_closed = '" + version.ver_name.replaceAll("'", "''") + "';";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(getBugFromResultSet(rs));
        }
        rs.close();
        return v;
    }

    /**
     * Obtenir les bugs fermés d'un projet, d'une certaine priorité.
     *
     * @param connection connexion à une base de données
     * @param project le projet d'où proviennent les bugs
     * @param  priority la priorité à prendre en compte
     * @return un Vector d'objets Bug
     * @throws java.sql.SQLException
     */
    public static Vector<Bug> readClosedByProjectAndPriority(Project project, int priority) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> v = new Vector<Bug>();
        Statement statement = connection.createStatement();
        String requete = "select * from bug where pro_name = ";
        requete += "'" + project.pro_name.replaceAll("'", "''") + "'";
        requete += " and bug_date_closed <> 0 ";
        requete += " and pri_number = " + priority + ";";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            v.add(getBugFromResultSet(rs));
        }
        rs.close();
        return v;
    }

    public static void updateProjectName(String oldName, String newName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update bug set pro_name = '" + newName.replaceAll("'", "''") + "' where pro_name = '"
                + oldName.replaceAll("'", "''") + "';";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    public static void turnOffCategory(Project project, Category category) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update bug set cat_id=0 where pro_name='" + project.pro_name.replaceAll("'", "''")
                + "' and cat_id=" + category.cat_id + ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
    }

    public static void archive(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update bug set archive = 1 where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, projectName);
        ps.executeUpdate();
    }

    public static void unarchive(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update bug set archive = 0 where pro_name = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setString(1, projectName);
        ps.executeUpdate();
    }

    public static Vector<Bug> readArchiveFrom(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> b = new Vector<Bug>();
        String requete = String.format("select * from bug where archive = 1 and pro_name = '%s';", projectName.replaceAll("'", "''"));
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next())
            b.add(getBugFromResultSet(rs));
        rs.close();
        return b;
    }

    public static Vector<Bug> readOpenedOrderByID() throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Bug> b = new Vector<Bug>();
        String requete = "select * from bug where bug_date_closed = 0 order by bug_id;";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next())
            b.add(getBugFromResultSet(rs));
        rs.close();
        return b;
    }

    private static Bug getBugFromResultSet(ResultSet rs) throws SQLException {
        return new Bug(rs.getInt("bug_id"), rs.getString("bug_title"), rs.getLong("bug_date_opened"),
                       rs.getLong("bug_date_closed"), rs.getString("bug_version_opened"),
                       rs.getString("bug_version_closed"), rs.getInt("pri_number"), rs.getString("pro_name"),
                       rs.getInt("cat_id"), rs.getInt("archive"));
    }
}
