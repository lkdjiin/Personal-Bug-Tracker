package desktopbugtracker.util;

import java.io.File;

public class FileUtility {
    
    public static boolean makeAppDataDirIfNeeded(String name) {
        File appDataDir = new File(name);
        if (!appDataDir.exists() && !appDataDir.mkdir()) {
            return false;
        }
        return true;
    }
}
