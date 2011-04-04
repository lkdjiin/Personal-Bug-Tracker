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

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import desktopbugtracker.tools.filename.Extension;
import java.io.*;

public class PdfInitializer {

    String filename;

    public PdfInitializer(String filename) throws PdfExportException {
        assert filename != null;
        if (filename.equals("")) {
            throw new PdfExportException("No filename");
        }
        if (!Extension.isExt(filename, ".pdf")) {
            filename += ".pdf";
        }
        this.filename = filename;
    }

    public Document getDocument() throws PdfExportException {
        Document document = null;
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
        } catch (DocumentException ex) {
            throw new PdfExportException(ex);
        } catch (FileNotFoundException ex) {
            throw new PdfExportException(ex);
        }
        return document;
    }
}
