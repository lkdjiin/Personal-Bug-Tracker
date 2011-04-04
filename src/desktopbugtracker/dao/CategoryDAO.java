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

import desktopbugtracker.component.*;
import desktopbugtracker.tools.*;
import java.sql.*;
import java.util.Vector;

public class CategoryDAO extends CategoryDAOLL {

    /**
     * Si le projet possède des catégories, cette méthode les obtient toutes, Y COMPRIS la catégorie par défaut.
     * Sinon, c'est un Vector vide qui est renvoyé.
     */
    public static Vector<Category> read(String projectName) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Category> categories = new Vector<Category>();
        Vector<Integer> catIDs = Pro_catDAO.getCategoryID(projectName);
        for (Integer e : catIDs) {
            Statement statement = connection.createStatement();
            String requete = "select * from category where cat_id=" + e + ";";
            ResultSet rs = statement.executeQuery(requete);
            while (rs.next()) {
                categories.add(new Category(rs.getInt("cat_id"), rs.getString("cat_title"), rs.getInt("archive")));
            }
            rs.close();
        }
        if (!categories.isEmpty()) {
            categories.insertElementAt(new Category(0, " ", 0), 0);
        }
        return categories;
    }

    public static Category read(int id) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Category ret = null;
        Statement statement = connection.createStatement();
        String requete = "select * from category where cat_id=" + id + ";";
        ResultSet rs = statement.executeQuery(requete);
        if (rs.next()) {
            ret = new Category(rs.getInt("cat_id"), rs.getString("cat_title"), rs.getInt("archive"));
        }
        rs.close();
        return ret;
    }

    /**
     * Enregistrer une nouvelle catégorie <code>category</code> et l'associer au
     * projet <code>project</code> grâce à la table de liaison pro_cat.
     * En se servant de cette méthode, on ne se préoccupe pas de l'ID dans
     * l'objet category ; il sera de toutes façons remplacé.
     *
     * @param connection
     * @param category
     * @param project
     * @throws java.sql.SQLException
     */
    public static void createAlongWithPro_cat(Category category, Project project) throws SQLException {
        int id = getNextId();
        category.cat_id = id;
        create(category);
        Pro_catDAO.create(new Pro_cat(project.pro_name, id, 0));
    }

    public static boolean isUsedCategory(Project project, Category category) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        boolean ret = false;
        Statement statement = connection.createStatement();
        String requete = "select bug_id from bug where cat_id=" + category.cat_id + " and pro_name='" + project.pro_name + "'" + ";";
        ResultSet rs = statement.executeQuery(requete);
        if (rs.next()) {
            ret = true;
        }
        rs.close();
        return ret;
    }
}
