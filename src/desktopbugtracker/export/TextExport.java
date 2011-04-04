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

public class TextExport {

    ReportData ppd;
    Text text;
    
    public TextExport(String filename, ReportData ppd) throws TextExportException {
        this.ppd = ppd;
        text = new Text(filename);
    }

    public void write() {
        elaborateDocument();
        text.writeAndClose();
    }

    private void elaborateDocument()  {
        addProjectTitle();
        addProjectMetaData();
        addBugList();
    }

    private void addProjectTitle()  {
        text.addParagraph(ppd.getProjectTitle());
        text.addNewLine();
    }

    private void addProjectMetaData()  {
        text.addParagraph(ppd.getTypeOfBugList());
        text.addNewLine();
        text.addNewLine();
    }

    private void addBugList()  {
        for (Bug b : ppd.getListOfBugs()) {
            addBugMetaData(b);
            addBugMessages(b);
        }
    }

    private void addBugMetaData(Bug b)  {
        addBugTitle(b);
        text.addParagraph(b.getOpenedVersionFormatedForPaper());
        text.addParagraph(b.getClosedVersionFormatedForPaper());
        addBugPriority(b);
    }

    private void addBugTitle(Bug b) {
        text.addNewLine();
        String str = b.getTitleFormatedForPaper();
        text.addParagraph(str);
    }

    private void addBugPriority(Bug b)  {
        text.addParagraph(b.getPriorityFormatedForPaper());
        text.addNewLine();
    }

    private void addBugMessages(Bug b)  {
        for (Message m : ppd.getMessagesFromBug(b)) {
            addMessageMetaData(m);
            text.addParagraph(m.mes_text);
            text.addNewLine();
        }
    }

    private void addMessageMetaData(Message m)  {
        text.addParagraph(m.getDateAndTime());
    }
}
