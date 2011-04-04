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
package desktopbugtracker.data;

public class ApplicationVersion {
    public static final String name = "Personal Bug Tracker";
    public static final String major = "0";
    public static final String minor = "13";
    public static final String build = "";

    public static String getFullName() {
        return name + " " + getVersion();
    }

    public static String getVersion() {
        return major + "." + minor + getBuild();
    }

    private static String getBuild() {
        if(build == null || build.equals("")) {
            return "";
        } else {
            return "-" + build;
        }
    }
}
