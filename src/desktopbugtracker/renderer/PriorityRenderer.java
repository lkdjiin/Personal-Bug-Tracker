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
package desktopbugtracker.renderer;

import desktopbugtracker.component.*;
import desktopbugtracker.dao.Priority_i18nDAO;
import desktopbugtracker.data.ApplicationConfig;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.*;
import javax.swing.*;

public class PriorityRenderer extends JLabel implements ListCellRenderer {

    public PriorityRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(Color.WHITE);
            setForeground(list.getForeground());
        }
        Priority p = (Priority) value;
        Priority_i18n p_i18n = null;
        try {
            p_i18n = Priority_i18nDAO.readPk(p.pri_number, ApplicationConfig.getLocale().getLanguage());
            setText(p_i18n.pri_description);
        } catch (SQLException ex) {
            Logger.getLogger(PriorityRenderer.class.getName()).log(Level.SEVERE, null, ex);
            setText("Error");
        }

        return this;
    }
}
