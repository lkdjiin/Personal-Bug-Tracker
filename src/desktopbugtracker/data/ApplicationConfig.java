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

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class ApplicationConfig {
    
    private static Properties properties = null;
    private static Locale locale = new Locale("en");

    public static void loadProperties() {
        FileInputStream in = null;
        try {
            properties = new Properties();
            in = new FileInputStream(ApplicationDirectory.dataDirectory() + "pbt.properties");
            properties.load(in);
            in.close();
            locale = new Locale(properties.getProperty("language", "en"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, null, ex);
            properties = null;
        } catch (IOException ex) {
            Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, null, ex);
            properties = null;
        }
    }

    public static void saveProperties() {
        if (properties != null) {
            FileOutputStream out = null;
            try {
                properties.setProperty("language", locale.toString());
                out = new FileOutputStream(ApplicationDirectory.dataDirectory() + "pbt.properties");
                properties.store(out, "---No Comment---");
                out.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        ApplicationConfig.locale = locale;
    }
}
