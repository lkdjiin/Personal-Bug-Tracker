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
package desktopbugtracker.component;

import desktopbugtracker.component.dto.CategoryDTO;
import desktopbugtracker.dao.CategoryDAO;
import java.sql.*;
import java.util.Vector;
import java.util.logging.*;

public class Category extends CategoryDTO {

    public Category(int cat_id, String cat_title, int archive) {
        super(cat_id, cat_title, archive);
    }

    public Category(int cat_id, String cat_title) {
        super(cat_id, cat_title, 0);
    }

    public Category(CategoryDTO c) {
        this.cat_id = c.cat_id;
        this.cat_title = c.cat_title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        if (((Category) o).cat_id == this.cat_id) {
            return true;
        } else {
            return false;
        }
    }

    public static Vector<Category> read(Project p) {
        assert p != null;
        try {
            return CategoryDAO.read(p.pro_name);
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
            return new Vector<Category>();
        }
    }

    public void delete() throws SQLException {
        if(this.cat_id != 0) { // Zero is the default category, want to keep it.
            CategoryDAO.delete(this);
        }
    }
}
