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

import desktopbugtracker.component.dto.VersionDTO;
import desktopbugtracker.dao.VersionDAO;
import java.sql.SQLException;
import java.util.logging.*;

public class Version extends VersionDTO implements ArchivableDomain {

    public Version(String ver_name, String pro_name, int archive) {
        super(ver_name, pro_name, archive);
    }

    public Version(String ver_name, String pro_name) {
        super(ver_name, pro_name, 0);
    }

    public Version(VersionDTO version) {
        this.ver_name = version.ver_name;
        this.pro_name = version.pro_name;
        this.archive = version.archive;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Version)) {
            return false;
        }
        boolean ret = true;
        if (!(((Version) o).ver_name).equals(this.ver_name)) {
            ret = false;
        }
        if (!(((Version) o).pro_name).equals(this.pro_name)) {
            ret = false;
        }
        return ret;
    }

    public void archive() {
        try {
            VersionDAO.archive(this);
        } catch (SQLException ex) {
            Logger.getLogger(Version.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unarchive() {
        try {
            VersionDAO.unarchive(this.pro_name);
        } catch (SQLException ex) {
            Logger.getLogger(Version.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
