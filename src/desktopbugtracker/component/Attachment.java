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
package desktopbugtracker.component;

import desktopbugtracker.component.dto.AttachmentDTO;
import desktopbugtracker.dao.AttachmentDAO;
import desktopbugtracker.data.ApplicationDirectory;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.*;

public class Attachment extends AttachmentDTO implements ArchivableDomain {

    public Attachment() {
        super();
    }

    public Attachment(String text, int mes_id) {
        super(0, text, 0, mes_id);
    }

    public Attachment(int id, String text, int mes_id) {
        super(id, text, 0, mes_id);
    }

    public Attachment(int id, String text, int archive, int mes_id) {
        super(id, text, archive, mes_id);
    }

    public Attachment(AttachmentDTO dto) {
        this.archive = dto.archive;
        this.id = dto.id;
        this.mes_id = dto.mes_id;
        this.name = dto.name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Attachment)) {
            return false;
        }
        boolean ret = true;
        if (((Attachment) o).id != this.id) {
            ret = false;
        }
        return ret;
    }

    @Override
    public int hashCode() {
        return id + archive + mes_id + name.hashCode();
    }

    public void archive() {
        try {
            AttachmentDAO.archive(mes_id);
        } catch (SQLException ex) {
            Logger.getLogger(Attachment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unarchive() {
        try {
            AttachmentDAO.unarchive(mes_id);
        } catch (SQLException ex) {
            Logger.getLogger(Attachment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vector<Attachment> readFromMessage(int messageId) {
        Vector<Attachment> attachments = new Vector<Attachment>();
        try {
            attachments = AttachmentDAO.read("mes_id", messageId);
        } catch (SQLException ex) {
            Logger.getLogger(Attachment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attachments;
    }

    /**
     * Save this object in database with auto-incremented id.
     * If the value returned is 0, there was a problem and the object wasn't created.
     * @return the id of the object
     * //@todo create if id is 0, else update
     */
    public int save() {
        try {
            int next = AttachmentDAO.getNextId();
            AttachmentDAO.createPk(new Attachment(next, name, mes_id));
            return next;
        } catch (SQLException ex) {
            Logger.getLogger(Attachment.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public boolean delete() {
        try {
            AttachmentDAO.deletePk(this);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Attachment.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String getAbsolutePath() {
        return ApplicationDirectory.attachmentDirectory() + id + name;
    }

    @Override
    public String toString() {
        return name;
    }


}
