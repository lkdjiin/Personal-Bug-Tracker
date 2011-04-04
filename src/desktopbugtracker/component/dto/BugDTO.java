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

public class BugDTO {

    public int bug_id;
    public String bug_title;
    public long bug_date_opened;
    public long bug_date_closed;
    public String bug_version_opened;
    public String bug_version_closed;
    public int pri_number;
    public String pro_name;
    public int cat_id;
    public int archive;

    public BugDTO() {
        bug_id = 0;
        bug_title = "";
        bug_date_opened = 0;
        bug_date_closed = 0;
        bug_version_opened = "";
        bug_version_closed = "";
        pri_number = 0;
        pro_name = "";
        cat_id = 0;
        archive = 0;
    }

    /**
     * @param bug_id int
     * @param bug_title String
     * @param bug_date_opened double
     * @param bug_date_closed double
     * @param bug_version_opened String
     * @param bug_version_closed String
     * @param pri_number int
     * @param pro_name String
     * @param cat_id
     */
    public BugDTO(int bug_id, String bug_title, long bug_date_opened, long bug_date_closed, String bug_version_opened,
            String bug_version_closed, int pri_number, String pro_name, int cat_id, int archive) {
        this.bug_id = bug_id;
        this.bug_title = bug_title;
        this.bug_date_opened = bug_date_opened;
        this.bug_date_closed = bug_date_closed;
        this.bug_version_opened = bug_version_opened;
        this.bug_version_closed = bug_version_closed;
        this.pri_number = pri_number;
        this.pro_name = pro_name;
        this.cat_id = cat_id;
        this.archive = archive;
    }

    @Override
    public String toString() {
        return "" + bug_id + ", " + bug_title + ", " + bug_date_opened + ", " + bug_date_closed
                + ", " + bug_version_opened + ", " + bug_version_closed + ", " + pri_number
                + ", " + pro_name + ", " + cat_id + ", " + archive;
    }
}
