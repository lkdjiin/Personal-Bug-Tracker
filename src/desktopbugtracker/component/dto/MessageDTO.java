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

public class MessageDTO {

    public int mes_id;
    public String mes_text;
    public long mes_date;
    public long mes_time;
    public int bug_id;
    public int archive;

    public MessageDTO() {
        mes_id = 0;
        mes_text = "";
        mes_date = 0;
        mes_time = 0;
        bug_id = 0;
        archive = 0;
    }

    public MessageDTO(int mes_id, String mes_text, long mes_date, long mes_time, int bug_id, int archive) {
        this.mes_id = mes_id;
        this.mes_text = mes_text;
        this.mes_date = mes_date;
        this.mes_time = mes_time;
        this.bug_id = bug_id;
        this.archive = archive;
    }

    @Override
    public String toString() {
        return "" + mes_id + ", " + mes_text + ", " + mes_date + ", " + mes_time + ", " + bug_id + ", " + archive;
    }
}
