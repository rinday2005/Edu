/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SectionsDAO;

/**
 *
 * @author ADMIN
 */
import model.Sections;
import java.util.List;
import java.util.UUID;

public interface ISectionDAO {
      boolean insert(Sections s);
    Sections findById(UUID sectionID);
    List<Sections> findAll();
    List<Sections> findByCourseId(UUID courseID);
    boolean update(Sections s);
    boolean delete(UUID sectionID);

    // yêu cầu thêm
    boolean updateStatus(UUID sectionID, boolean status);
}
