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

public class MessageDAO extends MessageDAOLL {

    public static void archive(int bug_id) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update message set archive = 1 where bug_id = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, bug_id);
        ps.executeUpdate();
    }

    public static void unarchive(int bug_id) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        String requete = "update message set archive = 0 where bug_id = ?;";
        PreparedStatement ps = connection.prepareStatement(requete);
        ps.setInt(1, bug_id);
        ps.executeUpdate();
    }

    public static Vector<Message> readArchiveFrom(String projectName) throws SQLException {
        Vector<Message> m = new Vector<Message>();
        Vector<Bug> bugs = BugDAO.readArchiveFrom(projectName);
        for(Bug bug : bugs) {
            m.addAll(readFromBug(bug.bug_id));
        }
        return m;
    }

    private static Vector<Message> readFromBug(int bug_id) throws SQLException {
        Connection connection = Sqlite.get(Db.DATABASE);
        Vector<Message> m = new Vector<Message>();
        String requete = String.format("select * from message where archive = 1 and bug_id = %d;", bug_id);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(requete);
        while (rs.next()) {
            m.add(new Message(rs.getInt("mes_id"), rs.getString("mes_text"), rs.getLong("mes_date"),
                    rs.getLong("mes_time"), rs.getInt("bug_id"), rs.getInt("archive")));
        }
        rs.close();
        return m;
    }
}
