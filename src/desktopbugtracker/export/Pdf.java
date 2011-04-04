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

import com.lowagie.text.*;
import java.util.logging.*;

public class Pdf {

    Document document;

    public Pdf(String filename) throws PdfExportException {
        document = new PdfInitializer(filename).getDocument();
    }

    public void writeAndClose() {
        document.close();
    }

    public void addNewLine() {
        try {
            document.add(new Paragraph(" "));
        } catch (DocumentException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addParagraph(Paragraph p) {
        try {
            document.add(p);
        } catch (DocumentException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addParagraph(String s) {
        addParagraph(new Paragraph(s));
    }

    public void addParagraph(String s, Font f, PdfAlign align) {
        Paragraph paragraph = new Paragraph(s, f);
        paragraph.setAlignment(align.toITextElement());
        addParagraph(paragraph);
    }
}
