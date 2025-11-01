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

    List<Courses> getAllCourses();
    Courses getCourseById(UUID courseId);

}