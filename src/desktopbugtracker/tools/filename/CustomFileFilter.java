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

package desktopbugtracker.tools.filename;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class CustomFileFilter extends FileFilter {
    String extension = null;
    String description = null;

    /**
     * @param extension with the dot !
     * @param description usually the same as extension without the dot or a more descriptive sentence
     */
    public CustomFileFilter(String extension, String description) {
        this.description = description;
        this.extension = extension;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String name = f.getName();
        if (name.length() < 5 || ! Extension.isExt(name, extension)) {
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
