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

public class McqChoices implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private UUID id;
    
    private String content;
 
    private boolean isCorrect;
  
    private UUID mcqQuestionId;
 
    private Collection<McqUserAnswer> mcqUserAnswerCollection;

    public McqChoices() {
    }

    public McqChoices(UUID id, String content, boolean isCorrect, UUID mcqQuestionId, Collection<McqUserAnswer> mcqUserAnswerCollection) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
        this.mcqQuestionId = mcqQuestionId;
        this.mcqUserAnswerCollection = mcqUserAnswerCollection;
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

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public UUID getMcqQuestionId() {
        return mcqQuestionId;
    }

    public void setMcqQuestionId(UUID mcqQuestionId) {
        this.mcqQuestionId = mcqQuestionId;
    }

    public Collection<McqUserAnswer> getMcqUserAnswerCollection() {
        return mcqUserAnswerCollection;
    }

    public void setMcqUserAnswerCollection(Collection<McqUserAnswer> mcqUserAnswerCollection) {
        this.mcqUserAnswerCollection = mcqUserAnswerCollection;
    }

    
}
