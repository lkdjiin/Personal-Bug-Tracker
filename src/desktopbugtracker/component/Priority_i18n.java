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

import desktopbugtracker.component.dto.Priority_i18nDTO;

public class Priority_i18n extends Priority_i18nDTO {

    /**
     * @param pri_number int
     * @param pri_code String
     * @param pri_description String
     */
    public Priority_i18n(int pri_number, String pri_code, String pri_description) {
        super(pri_number, pri_code, pri_description);
    }

    public Priority_i18n(Priority_i18nDTO priority_i18n) {
        super(priority_i18n.pri_number, priority_i18n.pri_code, priority_i18n.pri_description);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Priority_i18n)) {
            return false;
        }
        boolean ret = true;
        if (((Priority_i18n) o).pri_number != this.pri_number) {
            ret = false;
        }
        if (!(((Priority_i18n) o).pri_code).equals(this.pri_code)) {
            ret = false;
        }
        if (!(((Priority_i18n) o).pri_description).equals(this.pri_description)) {
            ret = false;
        }
        return ret;
    }
}
