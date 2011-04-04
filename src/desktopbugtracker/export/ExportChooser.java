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
package desktopbugtracker.export;

import desktopbugtracker.I18N;
import desktopbugtracker.tools.filename.CustomFileFilter;
import java.awt.Component;
import java.io.File;
import javax.swing.*;

public class ExportChooser {
    private String filter;
    private String filename = "";
    private boolean okToContinue = true;
    private Component parent;

    public ExportChooser(String fileExtWithoutDot, Component parent) {
        filter = fileExtWithoutDot;
        this.parent = parent;
    }

    public void choose() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new CustomFileFilter("." + filter, filter));
        int returnVal = fc.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            filename = file.getAbsolutePath();
            if (file.exists()) {
                int response = JOptionPane.showConfirmDialog(parent, I18N.getBundle("Really_overwrite_") + " "
                        + file.getAbsolutePath() + " ?");
                if (response == JOptionPane.CANCEL_OPTION || response == JOptionPane.NO_OPTION) {
                    okToContinue = false;
                }
            }
        } else {
            okToContinue = false;
        }
    }

    public String getFilename() {
        if(isOkToContinue())
            return filename;
        else
            return "";
    }

    public boolean isOkToContinue() {
        return okToContinue;
    }
}
