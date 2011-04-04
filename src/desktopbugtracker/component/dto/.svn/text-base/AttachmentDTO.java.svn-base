/*
 *   This file is part of Personal Bug Tracker.
 *   Copyright 2010, Xavier Nayrac <xavier.nayrac@gmail.com>.
 * 
 *   Personal Bug Tracker is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package desktopbugtracker.component.dto;

public class AttachmentDTO {
    public int id;
    public String name;
    public int archive;
    public int mes_id;

    public AttachmentDTO() {
        id = 0;
        name = "";
        archive = 0;
        mes_id = 0;
    }

    public AttachmentDTO(int id, String text, int archive, int mes_id) {
        this.id = id;
        this.name = text;
        this.archive = archive;
        this.mes_id = mes_id;
    }

    @Override
    public String toString() {
        return "" + id + ", " + name + ", " + archive + ", " + mes_id;
    }
}
