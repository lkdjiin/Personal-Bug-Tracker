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

import desktopbugtracker.tools.*;
import java.sql.*;

public class GlobalDAO {

    public static String getWorkingDatabaseVersion() throws SQLException {
        Connection c = Sqlite.get(Db.DATABASE);
        String dbVersion = null;
        Statement statement = c.createStatement();
        String requete = "select global_db_version from global;";
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            dbVersion = rs.getString("global_db_version");
        }
        rs.close();
        return dbVersion;
    }
}
