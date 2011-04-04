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
package desktopbugtracker.tools;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Xavier Nayrac <xavier.nayrac@gmail.com>
 */
public class RawFile {

    private String filename = "";

    public RawFile(String filename) throws FileNotFoundException {
//        file = new BufferedInputStream(new FileInputStream(filename));
        this.filename = filename;
    }

    /**
     * Copie rawFile dans dest.
     * @param dest
     * @return true si la copie s'est bien passée, false sinon.
     */
    private boolean copy(BufferedInputStream source, BufferedOutputStream dest) {
        try {
            int b;
            while((b = source.read()) != -1)
                dest.write(b);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(RawFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean copyTo(String destination) {
        BufferedInputStream source = null;
        BufferedOutputStream dest = null;
        try {
            source = new BufferedInputStream(new FileInputStream(filename));
            dest = new BufferedOutputStream(new FileOutputStream(destination));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RawFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        boolean status = copy(source, dest);
        try {
            source.close();
            dest.close();
        } catch (IOException ex) {
            Logger.getLogger(RawFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
