// SectionsService.java
package service;

import model.Sections;
import DAO.*;
import java.util.UUID;
import SectionsDAO.SectionDAO;
import java.util.List;

public class SectionsService {

    SectionDAO dao = new SectionDAO();

    public boolean insert(Sections s) {
        return dao.insert(s);
    }

    public Sections findById(UUID sectionID) {
        return dao.findById(sectionID);
    }

    public List<Sections> findAll() {
        return dao.findAll();
    }

    public List<Sections> findByCourseId(UUID courseID) {
        return dao.findByCourseId(courseID);
    }

    public boolean update(Sections s) {
        return dao.update(s);
    }

    public boolean delete(UUID sectionID) {
        return dao.delete(sectionID);
    }

    public boolean updateStatus(UUID sectionID, boolean status) {
        return dao.updateStatus(sectionID, status);
    }
}
