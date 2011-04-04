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

import desktopbugtracker.component.dto.PriorityDTO;
import desktopbugtracker.dao.*;
import desktopbugtracker.data.ApplicationConfig;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.*;

public class Priority extends PriorityDTO {

    public Priority(int pri_number) {
        this.pri_number = pri_number;
    }

    public Priority(PriorityDTO priority) {
        this.pri_number = priority.pri_number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Priority)) {
            return false;
        }
        boolean ret = true;
        if (((Priority) o).pri_number != this.pri_number) {
            ret = false;
        }
        return ret;
    }

    public String getI18NDescription() {
        if (pri_number == 0) { //@todo si ça existe dans la table, autant y mettre la valeur ""
            return "";
        }
        try {
            Priority_i18n prio_i18n = Priority_i18nDAO.readPk(pri_number, ApplicationConfig.getLocale().getLanguage());
            return prio_i18n.pri_description;
        } catch (SQLException ex) {
            Logger.getLogger(Priority.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public static Vector<Priority> read() {
        try {
            return PriorityDAO.read();
        } catch (SQLException ex) {
            Logger.getLogger(Priority.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Priority>();
        }
    }

    @Override
    public int hashCode() {
        return pri_number;
    }
}
