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
package desktopbugtracker.tools;

import desktopbugtracker.data.ApplicationDirectory;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.*;

public class Sqlite {

    private static HashMap<Db, Connection> cons;

    static {
        cons = new HashMap<Db, Connection>();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Sqlite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setup(Db file, Db use) throws SQLException {
        assert !cons.containsKey(file) : "Can't switch between setups.";
        String connect = "jdbc:sqlite:";
        if(file == Db.DATABASE && use == Db.NORMAL_USE) {
            connect += ApplicationDirectory.dataDirectory() + "database.s3";
        } else if(file == Db.DATABASE && use == Db.TEST_USE) {
            connect += "testDb/testdatabase.s3";
        } else if(file == Db.DATABASE && use == Db.TEST_REAL_DATA_USE) {
            connect += "testDb/testrealdatabase.s3";
        } else {
            assert false : "Unknown setup.";
        }
        cons.put(file, DriverManager.getConnection(connect));
    }

    public static Connection get(Db file) {
        assert cons.containsKey(file);
        return cons.get(file);
    }

    //@todo Ne pas pouvoir faire de commit ou de rollback avant le begin ou après le end.
    public static void beginTransaction(Db file) throws SqlTransactionException {
        autoCommit(file, false);
    }

    public static void endTransaction(Db file) throws SqlTransactionException {
        autoCommit(file, true);
    }

    public static void commit(Db file) throws SqlTransactionException {
        try {
            cons.get(file).commit();
        } catch (SQLException ex) {
            throw new SqlTransactionException(ex);
        }
    }

    public static void rollback(Db file) throws SqlTransactionException {
        try {
            cons.get(file).rollback();
        } catch (SQLException ex) {
            throw new SqlTransactionException(ex);
        }
    }

    private static void autoCommit(Db file, boolean b) throws SqlTransactionException {
        try {
            cons.get(file).setAutoCommit(b);
        } catch (SQLException ex) {
            throw new SqlTransactionException(ex);
        }
    }
}
