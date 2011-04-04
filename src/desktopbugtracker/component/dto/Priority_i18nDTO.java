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
package desktopbugtracker.component.dto;

public class Priority_i18nDTO {

    public int pri_number;
    public String pri_code;
    public String pri_description;

    public Priority_i18nDTO() {
        pri_number = 0;
        pri_code = "";
        pri_description = "";
    }

    public Priority_i18nDTO(int pri_number, String pri_code, String pri_description) {
        this.pri_number = pri_number;
        this.pri_code = pri_code;
        this.pri_description = pri_description;
    }

    @Override
    public String toString() {
        return "" + pri_number + ", " + pri_code + ", " + pri_description;
    }
}
