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
package desktopbugtracker.component;

import desktopbugtracker.component.dto.MessageDTO;
import desktopbugtracker.dao.MessageDAO;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Message extends MessageDTO implements ArchivableDomain {

    public Message(int mes_id, String mes_text, long mes_date, long mes_time, int bug_id, int archive) {
        super(mes_id, mes_text, mes_date, mes_time, bug_id, archive);
    }

    public Message(int mes_id, String mes_text, long mes_date, long mes_time, int bug_id) {
        super(mes_id, mes_text, mes_date, mes_time, bug_id, 0);
    }

    public Message(MessageDTO message) {
        this.mes_id = message.mes_id;
        this.mes_text = message.mes_text;
        this.mes_date = message.mes_date;
        this.mes_time = message.mes_time;
        this.bug_id = message.bug_id;
        this.archive = message.archive;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        boolean ret = true;
        if (((Message) o).mes_id != this.mes_id) {
            ret = false;
        }
        if (!(((Message) o).mes_text).equals(this.mes_text)) {
            ret = false;
        }
        if (((Message) o).mes_date != this.mes_date) {
            ret = false;
        }
        if (((Message) o).mes_time != this.mes_time) {
            ret = false;
        }
        if (((Message) o).bug_id != this.bug_id) {
            ret = false;
        }
        return ret;
    }

    public String getTitleForScreenPrinting() {
        String title = (new java.sql.Date(mes_date)).toString() + " --- ";
        title += (new java.sql.Time(mes_time)).toString() + " --- ";
        title += "[Message ID : " + mes_id + "]";
        return title;
    }

    public String getDateAndTime() {
        String str = (new java.sql.Date(mes_date)).toString() + " ";
        str += (new java.sql.Time(mes_time)).toString();
        return str;
    }

    public static Vector<Message> read(String colname, Object value) {
        try {
            return MessageDAO.read(colname, value);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);

        }
        return new Vector<Message>();
    }

    public static void delete(String colname, Object value) {
        try {
            MessageDAO.delete(colname, value);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void archive() {
        try {
            MessageDAO.archive(bug_id);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unarchive() {
        try {
            MessageDAO.unarchive(bug_id);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
