/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package McqChoiceDAO;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;
import model.McqChoices;

/**
 *
 * @author ADMIN
 */
public interface IMcqChoiceDAO {
    boolean insert(McqChoices choice);
    boolean insertWithConnection(McqChoices choice, Connection con);
    McqChoices findById(UUID choiceId);
    List<McqChoices> findAll();
    List<McqChoices> findByQuestionId(UUID questionId);
    boolean update(McqChoices choice);
    boolean deleteById(UUID choiceId);
    boolean deleteByQuestionId(UUID questionId);
}

