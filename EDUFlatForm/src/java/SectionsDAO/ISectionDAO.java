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
    List<Sections> getByCourseId(UUID courseID);
}
