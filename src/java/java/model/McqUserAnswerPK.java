package model;

import java.io.Serializable;
import java.util.UUID;

public class McqUserAnswerPK implements Serializable {
    private UUID submissionId;
    private UUID mcqChoiceId;

    public McqUserAnswerPK() {}

    public McqUserAnswerPK(UUID submissionId, UUID mcqChoiceId) {
        this.submissionId = submissionId;
        this.mcqChoiceId = mcqChoiceId;
    }

    public UUID getSubmissionId() { return submissionId; }
    public void setSubmissionId(UUID submissionId) { this.submissionId = submissionId; }

    public UUID getMcqChoiceId() { return mcqChoiceId; }
    public void setMcqChoiceId(UUID mcqChoiceId) { this.mcqChoiceId = mcqChoiceId; }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (submissionId != null ? submissionId.hashCode() : 0);
        hash = 31 * hash + (mcqChoiceId != null ? mcqChoiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof McqUserAnswerPK)) return false;
        McqUserAnswerPK other = (McqUserAnswerPK) o;
        return (submissionId != null && submissionId.equals(other.submissionId))
            && (mcqChoiceId != null && mcqChoiceId.equals(other.mcqChoiceId));
    }

    @Override
    public String toString() {
        return "McqUserAnswerPK[submissionId=" + submissionId + ", mcqChoiceId=" + mcqChoiceId + "]";
    }
}
