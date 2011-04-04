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
package desktopbugtracker.renderer;

import desktopbugtracker.component.Bug;
import java.awt.*;
import javax.swing.*;

public class BugIDRenderer extends JLabel implements ListCellRenderer {

    public BugIDRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setColor(isSelected, list);
        setText(makeString(value));
        return this;
    }

    private void setColor(boolean isSelected, JList list) {
        if (isSelected) {
            viewAsSelected(list);
        } else {
            viewAsNotSelected(list);
        }
    }

    private void viewAsSelected(JList list) {
        setBackground(list.getSelectionBackground());
        setForeground(list.getSelectionForeground());
    }

    private void viewAsNotSelected(JList list) {
        setBackground(Color.WHITE);
        setForeground(list.getForeground());
    }

    private String makeString(Object value) {
        if (!(value instanceof Bug)) {
            return "";
        }

        return "" + ((Bug)value).bug_id;
    }
}
