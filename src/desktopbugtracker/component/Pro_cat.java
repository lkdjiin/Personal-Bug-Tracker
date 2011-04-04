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

import desktopbugtracker.component.dto.Pro_catDTO;
import desktopbugtracker.dao.Pro_catDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pro_cat extends Pro_catDTO implements ArchivableDomain {

    public Pro_cat(String pro_name, int cat_id, int archive) {
        super(pro_name, cat_id, archive);
    }

    public Pro_cat(String pro_name, int cat_id) {
        super(pro_name, cat_id, 0);
    }

    public Pro_cat(Pro_catDTO tab) {
        this.pro_name = tab.pro_name;
        this.cat_id = tab.cat_id;
        this.archive = tab.archive;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Pro_cat)) {
            return false;
        }
        boolean ret = true;
        if (((Pro_cat) o).cat_id != this.cat_id) {
            ret = false;
        }
        if (!((Pro_cat) o).pro_name.equals(this.pro_name)) {
            ret = false;
        }
        return ret;
    }

    public void archive() {
        try {
            Pro_catDAO.archive(pro_name);
        } catch (SQLException ex) {
            Logger.getLogger(Pro_cat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unarchive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
