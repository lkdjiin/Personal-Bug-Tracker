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
package desktopbugtracker;

import desktopbugtracker.data.ApplicationConfig;
import java.util.ResourceBundle;

public class I18N {
    public static String getBundle(String key) {
        return ResourceBundle.getBundle("desktopbugtracker/Bundle", ApplicationConfig.getLocale()).getString(key);
    }

    public static String getExport(String key) {
        return ResourceBundle.getBundle("desktopbugtracker/export/Export", ApplicationConfig.getLocale()).getString(key);
    }
}
