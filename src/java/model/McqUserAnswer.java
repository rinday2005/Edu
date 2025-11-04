/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */

public class McqUserAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected McqUserAnswerPK mcqUserAnswerPK;
   
    private UUID mcqChoices;

    public McqUserAnswer() {
    }

    public McqUserAnswer(McqUserAnswerPK mcqUserAnswerPK) {
        this.mcqUserAnswerPK = mcqUserAnswerPK;
    }

    public McqUserAnswer(UUID submissionId, UUID mcqChoiceId) {
        this.mcqUserAnswerPK = new McqUserAnswerPK(submissionId, mcqChoiceId);
    }

    public McqUserAnswerPK getMcqUserAnswerPK() {
        return mcqUserAnswerPK;
    }

    public void setMcqUserAnswerPK(McqUserAnswerPK mcqUserAnswerPK) {
        this.mcqUserAnswerPK = mcqUserAnswerPK;
    }

    public UUID getMcqChoices() {
        return mcqChoices;
    }

    public void setMcqChoices(UUID mcqChoices) {
        this.mcqChoices = mcqChoices;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mcqUserAnswerPK != null ? mcqUserAnswerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McqUserAnswer)) {
            return false;
        }
        McqUserAnswer other = (McqUserAnswer) object;
        if ((this.mcqUserAnswerPK == null && other.mcqUserAnswerPK != null) || (this.mcqUserAnswerPK != null && !this.mcqUserAnswerPK.equals(other.mcqUserAnswerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.McqUserAnswer[ mcqUserAnswerPK=" + mcqUserAnswerPK + " ]";
    }
    
}
