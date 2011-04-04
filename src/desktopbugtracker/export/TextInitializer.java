/*
 *   This file is part of Personal Bug Tracker.
 *   Copyright 2009, Xavier Nayrac <xavier.nayrac@gmail.com>.
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
package desktopbugtracker.export;

import desktopbugtracker.tools.filename.Extension;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextInitializer {

    String filename;

    public TextInitializer(String filename) throws TextExportException {
        assert filename != null;
        if (filename.equals("")) {
            throw new TextExportException("No filename");
        }
        if (!Extension.isExt(filename, ".txt")) {
            filename += ".txt";
        }
        this.filename = filename;
    }

     public PrintWriter getDocument() throws TextExportException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            return out;
        } catch (IOException ex) {
            Logger.getLogger(TextInitializer.class.getName()).log(Level.SEVERE, null, ex);
            throw new TextExportException(ex);
        }
     }

}
