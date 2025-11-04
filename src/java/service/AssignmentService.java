/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import AssignmentDAO.AssignmentDAO;
import model.Assignment;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public class AssignmentService {
    AssignmentDAO dao = new AssignmentDAO();
    
    public void create(Assignment a) {
        dao.create(a);
    }
    
    public Assignment findById(UUID id) {
        return dao.findById(id);
    }
    
    public List<Assignment> findAll() {
        return dao.findAll();
    }
    
    public List<Assignment> findByUserID(UUID userID) {
        List<Assignment> all = dao.findAll();
        List<Assignment> result = new java.util.ArrayList<>();
        for (Assignment a : all) {
            if (a.getUserID() != null && a.getUserID().equals(userID)) {
                result.add(a);
            }
        }
        return result;
    }
    
    public List<Assignment> findBySectionID(UUID sectionID) {
        List<Assignment> all = dao.findAll();
        List<Assignment> result = new java.util.ArrayList<>();
        for (Assignment a : all) {
            if (a.getSectionID() != null && a.getSectionID().equals(sectionID)) {
                result.add(a);
            }
        }
        return result;
    }
    
    public int update(Assignment a) {
        return dao.update(a);
    }
    
    public int deleteById(UUID id) {
        return dao.deleteById(id);
    }
}
