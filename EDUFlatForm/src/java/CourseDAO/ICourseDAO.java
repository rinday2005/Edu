/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CourseDAO;

/**
 *
 * @author ADMIN
 */
import java.util.List;
import java.util.UUID;
import model.Courses;

public interface ICourseDAO {

    boolean insert(Courses c);                      // CREATE

    Courses findById(UUID courseId);                // READ 1
    List<Courses> findAll();                        // READ *
    List<Courses> findByUserId(UUID userId);        // READ theo instructor

    boolean update(Courses c);                      // UPDATE toàn bộ
    boolean updateIsApproved(UUID courseId, boolean approved);  // UPDATE cờ duyệt

    boolean delete(UUID courseId);                  // DELETE
    boolean existsById(UUID courseId);              // tiện ích

}