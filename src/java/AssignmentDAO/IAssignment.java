/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AssignmentDAO;

/**
 *
 * @author ADMIN
 */
import java.util.List;
import java.util.UUID;
import model.Assignment;
public interface IAssignment {
    void create(Assignment a);                // C
    Assignment findById(UUID id);             // R1
    List<Assignment> findAll();               // R2
    List<Assignment> findByLessionID(UUID lessionID); // R3 - Find by lesson ID
    int update(Assignment a);                 // U
    int deleteById(UUID id);                  // D
}
