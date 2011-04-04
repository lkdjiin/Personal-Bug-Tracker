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

public class Pro_catDTO {
    public String pro_name;
    public int cat_id;
    public int archive;

    public Pro_catDTO() {
        pro_name = "";
        cat_id = 0;
        archive = 0;
    }

    public Pro_catDTO(String pro_name, int cat_id, int archive) {
        this.pro_name = pro_name;
        this.cat_id = cat_id;
        this.archive = archive;
    }

    @Override
    public String toString() {
        return pro_name + ", " + cat_id + ", " + archive;
    }
}
