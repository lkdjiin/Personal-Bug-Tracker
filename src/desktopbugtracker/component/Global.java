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

import desktopbugtracker.component.dto.GlobalDTO;

public class Global extends GlobalDTO {

    public Global(String global_db_version) {
        this.global_db_version = global_db_version;
    }

    public Global(GlobalDTO global) {
        this.global_db_version = global.global_db_version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Global)) {
            return false;
        }
        if (!(((Global) o).global_db_version).equals(this.global_db_version)) {
            return false;
        }
        return true;
    }
}
