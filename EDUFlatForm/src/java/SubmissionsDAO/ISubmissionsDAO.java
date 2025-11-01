/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SubmissionsDAO;

import java.util.UUID;
import model.Submissions;

/**
 *
 * @author ADMIN
 */
public interface ISubmissionsDAO {
    void create(Submissions s);
    int  update(Submissions s);
    int  deleteById(UUID submissionId);
    Submissions findById(UUID submissionId);
    int updateStatus(UUID userId, boolean status);
    java.util.List<Submissions> findByAssignment(UUID assignmentId);
    java.util.List<Submissions> findByUser(UUID userId);
    java.util.List<Submissions> findAll();
}
