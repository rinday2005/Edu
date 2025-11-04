/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package McqQuestionDAO;

import java.util.List;
import java.util.UUID;
import model.McqQuestions;

/**
 *
 * @author ADMIN
 */
public interface IMcqQuestionDAO {
    boolean insert(McqQuestions question);
    McqQuestions findById(UUID questionId);
    List<McqQuestions> findAll();
    List<McqQuestions> findByAssignmentId(UUID assignmentId);
    boolean update(McqQuestions question);
    boolean deleteById(UUID questionId);
}
