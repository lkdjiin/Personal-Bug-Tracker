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

import desktopbugtracker.component.dto.BugDTO;
import desktopbugtracker.I18N;
import desktopbugtracker.dao.*;
import desktopbugtracker.data.ApplicationConfig;
import java.sql.*;
import java.util.Vector;
import java.util.logging.*;

public class Bug extends BugDTO implements PrintableBug, ArchivableDomain {

    public Bug(int bug_id, String bug_title, long bug_date_opened, long bug_date_closed, String bug_version_opened,
            String bug_version_closed, int pri_number, String pro_name, int cat_id, int archive) {
        super(bug_id, bug_title, bug_date_opened, bug_date_closed, bug_version_opened, bug_version_closed, pri_number,
                pro_name, cat_id, archive);
    }

    public Bug(int bug_id, String bug_title, long bug_date_opened, long bug_date_closed, String bug_version_opened,
            String bug_version_closed, int pri_number, String pro_name, int cat_id) {
        super(bug_id, bug_title, bug_date_opened, bug_date_closed, bug_version_opened, bug_version_closed, pri_number,
                pro_name, cat_id, 0);
    }

    public Bug(BugDTO bug) {
        this.bug_id = bug.bug_id;
        this.bug_title = bug.bug_title;
        this.bug_date_opened = bug.bug_date_opened;
        this.bug_date_closed = bug.bug_date_closed;
        this.bug_version_opened = bug.bug_version_opened;
        this.bug_version_closed = bug.bug_version_closed;
        this.pri_number = bug.pri_number;
        this.pro_name = bug.pro_name;
        this.cat_id = bug.cat_id;
        this.archive = bug.archive;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Bug)) {
            return false;
        }
        boolean ret = true;
        if (((Bug) o).bug_id != this.bug_id) {
            ret = false;
        }
        if (!(((Bug) o).bug_title).equals(this.bug_title)) {
            ret = false;
        }
        if (((Bug) o).bug_date_opened != this.bug_date_opened) {
            ret = false;
        }
        if (((Bug) o).bug_date_closed != this.bug_date_closed) {
            ret = false;
        }
        if (!(((Bug) o).bug_version_opened).equals(this.bug_version_opened)) {
            ret = false;
        }
        if (!(((Bug) o).bug_version_closed).equals(this.bug_version_closed)) {
            ret = false;
        }
        if (((Bug) o).pri_number != this.pri_number) {
            ret = false;
        }
        if (!(((Bug) o).pro_name).equals(this.pro_name)) {
            ret = false;
        }
        if (((Bug) o).archive != this.archive) {
            ret = false;
        }
        return ret;
    }

    public boolean isOpened() {
        if (bug_version_closed.equals("")) {
            return true;
        }
        return false;
    }

    public boolean isClosed() {
        return !isOpened();
    }

    public boolean isGoodPriorityForPrinting(Priority p) {
        if (pri_number == p.pri_number || p.pri_number == 0) {
            return true;
        }
        return false;
    }

    public boolean isGoodCategoryForPrinting(Category c) {
        if (c == null || cat_id == c.cat_id || c.cat_id == 0) {
            return true;
        }
        return false;
    }

    public String getI18NDescriptionOfMyPriority() {
        try {
            Priority prio = PriorityDAO.readPk(pri_number);
            Priority_i18n prio_i18n = Priority_i18nDAO.readPk(prio.pri_number, ApplicationConfig.getLocale().getLanguage());
            return prio_i18n.pri_description;
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String getPriorityFormatedForPaper() {
        return I18N.getExport("priority") + " : " + getI18NDescriptionOfMyPriority();
    }

    public String getTitleOfMyCategory() {
         String ret = "";
        // 0 signifie Toutes catégories, je ne veux pas le traiter
        if (cat_id != 0) {
            try {
                Category c = CategoryDAO.read(cat_id);
                ret = c.cat_title;
            } catch (SQLException ex) {
                Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public String getTitleFormatedForPaper() {
        String ret = "[" + bug_id + "] " + bug_title;
        String tmpCat = getTitleOfMyCategory();
        if (!tmpCat.equals("")) {
            ret += " [" + tmpCat + "]";
        }
        return ret;
    }

    public String getOpenedVersionFormatedForPaper() {
        return I18N.getExport("open") + " : " + (new java.sql.Date(bug_date_opened)).toString() + " "
                    + I18N.getExport("in_version") + " " + bug_version_opened;
    }

    public String getClosedVersionFormatedForPaper() {
        String str = I18N.getExport("close") + " : ";
        long temp = bug_date_closed;
        if (temp != 0) {
            str += (new java.sql.Date(temp)).toString() + " " + I18N.getExport("in_version") + " " + bug_version_closed;
        }
        return str;
    }

    public static Vector<Bug> read(String colname, String value) {
        try {
            return BugDAO.read(colname, value);
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Bug>();
        }
    }

    public void updatePk() {
        try {
            BugDAO.updatePk(this);
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePk() {
        try {
            BugDAO.deletePk(this);
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void archive() {
        try {
            BugDAO.archive(pro_name);
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unarchive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash * this.bug_id;
    }
}
