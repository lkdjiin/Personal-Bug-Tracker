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
package desktopbugtracker.tools;

import desktopbugtracker.dao.GlobalDAO;
import java.sql.*;
import java.util.logging.*;

public class UpgradeDB {
    public static boolean upgradeIfNeeded() {
        try {
            String dbVersion = GlobalDAO.getWorkingDatabaseVersion();
            if(dbVersion.equals("1.5")) {
                return true;
            } else if(dbVersion.equals("1.4")) {
                upgradeDatabase14To15();
            } else if(dbVersion.equals("1.3")) {
                upgradeDatabase13To14();
                upgradeDatabase14To15();
            } else {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UpgradeDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static void upgradeDatabase13To14() throws SQLException {
        Connection c = Sqlite.get(Db.DATABASE);
        Statement statement = c.createStatement();
        statement.executeUpdate("ALTER TABLE bug ADD COLUMN archive integer;");
        statement.executeUpdate("UPDATE bug set archive = 0;");
        statement.executeUpdate("ALTER TABLE project ADD COLUMN archive integer;");
        statement.executeUpdate("UPDATE project set archive = 0;");
        statement.executeUpdate("ALTER TABLE version ADD COLUMN archive integer;");
        statement.executeUpdate("UPDATE version set archive = 0;");
        statement.executeUpdate("ALTER TABLE message ADD COLUMN archive integer;");
        statement.executeUpdate("UPDATE message set archive = 0;");
        statement.executeUpdate("ALTER TABLE category ADD COLUMN archive integer;");
        statement.executeUpdate("UPDATE category set archive = 0;");
        statement.executeUpdate("ALTER TABLE pro_cat ADD COLUMN archive integer;");
        statement.executeUpdate("UPDATE pro_cat set archive = 0;");
        statement.executeUpdate("DELETE FROM global;");
        statement.executeUpdate("INSERT INTO global VALUES('1.4');");
    }

    private static void upgradeDatabase14To15() throws SQLException {
        Connection c = Sqlite.get(Db.DATABASE);
        Statement statement = c.createStatement();
        statement.executeUpdate("CREATE TABLE attachment (id integer primary key NOT NULL, name text,"
                + "archive integer,mes_id integer CONSTRAINT FK_att_mes_id REFERENCES message (mes_id));");
        statement.executeUpdate("DELETE FROM global;");
        statement.executeUpdate("INSERT INTO global VALUES('1.5');");
    }

}
