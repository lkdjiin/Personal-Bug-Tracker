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

import desktopbugtracker.tools.*;
import java.sql.*;

public class AttachmentDAO extends AttachmentDAOLL {
    
    public static void archive(int mes_id) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update attachment set archive = 1 where mes_id = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, mes_id);
        ps.executeUpdate();
    }

    public static void unarchive(int mes_id) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update attachment set archive = 0 where mes_id = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, mes_id);
        ps.executeUpdate();
    }
}
