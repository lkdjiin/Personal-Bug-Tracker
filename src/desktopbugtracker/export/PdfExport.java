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

import desktopbugtracker.component.*;

public class PdfExport {

    ReportData ppd;
    Pdf pdf;

    public PdfExport(String filename, ReportData ppd) throws PdfExportException {
        this.ppd = ppd;
        pdf = new Pdf(filename);
    }

    public void write() throws PdfExportException {
        elaborateDocument();
        pdf.writeAndClose();
    }

    private void elaborateDocument() throws PdfExportException {
        addProjectTitle();
        addProjectMetaData();
        addBugList();
    }

    private void addProjectTitle() throws PdfExportException {
        pdf.addParagraph(ppd.getProjectTitle(), PdfFont.forTitleProject(), PdfAlign.CENTER);
        pdf.addNewLine();
    }

    private void addProjectMetaData() throws PdfExportException {
        pdf.addParagraph(ppd.getTypeOfBugList(), PdfFont.bold(), PdfAlign.CENTER);
        pdf.addNewLine();
        pdf.addNewLine();
    }

    private void addBugList() throws PdfExportException {
        for (Bug b : ppd.getListOfBugs()) {
            addBugMetaData(b);
            addBugMessages(b);
        }
    }

    private void addBugMetaData(PrintableBug b) throws PdfExportException {
        addBugTitle(b);
        pdf.addParagraph(b.getOpenedVersionFormatedForPaper());
        pdf.addParagraph(b.getClosedVersionFormatedForPaper());
        addBugPriority(b);
    }
    
    private void addBugTitle(PrintableBug b) throws PdfExportException {
        pdf.addNewLine();
        String str = b.getTitleFormatedForPaper();
        pdf.addParagraph(str, PdfFont.forTitleBug(), PdfAlign.LEFT);
    }

    private void addBugPriority(PrintableBug b) throws PdfExportException {
        pdf.addParagraph(b.getPriorityFormatedForPaper());
        pdf.addNewLine();
    }

    private void addBugMessages(Bug b) throws PdfExportException {
        for (Message m : ppd.getMessagesFromBug(b)) {
            addMessageMetaData(m);
            pdf.addParagraph(m.mes_text);
            pdf.addNewLine();
        }
    }

    private void addMessageMetaData(Message m) throws PdfExportException {
        pdf.addParagraph(m.getDateAndTime(), PdfFont.bold(), PdfAlign.LEFT);
    }
}
