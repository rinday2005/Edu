/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;


/**
 *
 * @author ADMIN
 */

public class McqQuestions implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private UUID id;
   
    private String content;
    
    private Collection<McqChoices> mcqChoicesCollection;
    
    private UUID assignmentId;

    public McqQuestions() {
    }

    public McqQuestions(UUID id, String content, Collection<McqChoices> mcqChoicesCollection, UUID assignmentId) {
        this.id = id;
        this.content = content;
        this.mcqChoicesCollection = mcqChoicesCollection;
        this.assignmentId = assignmentId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Collection<McqChoices> getMcqChoicesCollection() {
        return mcqChoicesCollection;
    }

    public void setMcqChoicesCollection(Collection<McqChoices> mcqChoicesCollection) {
        this.mcqChoicesCollection = mcqChoicesCollection;
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(UUID assignmentId) {
        this.assignmentId = assignmentId;
    }

    
    
}
