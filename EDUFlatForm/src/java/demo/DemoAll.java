// src/demo/DemoMcqUserAnswer.java
package demo;

import McqUserAnswerDAO.McqUserAnswerDAO;
import McqUserAnswerDAO.IMcqUserAnswerDAO;
import model.McqUserAnswer;
import model.McqUserAnswerPK;

import java.util.*;
import java.util.UUID;

public class DemoAll {

    private static void print(String label, McqUserAnswer x) {
        if (x == null) { System.out.println(label + " = null"); return; }
        System.out.println(label + " = {submissionId="
            + x.getMcqUserAnswerPK().getSubmissionId()
            + ", mcqChoiceId=" + x.getMcqUserAnswerPK().getMcqChoiceId() + "}");
    }

    public static void main(String[] args) {
        IMcqUserAnswerDAO dao = new McqUserAnswerDAO();

        UUID sub = UUID.randomUUID();
        UUID c1 = UUID.randomUUID();
        UUID c2 = UUID.randomUUID();

        McqUserAnswerPK k1 = new McqUserAnswerPK(sub, c1);
        McqUserAnswerPK k2 = new McqUserAnswerPK(sub, c2);

        // INSERT
        try {
            dao.insert(k1);
            System.out.println(">>> INSERT k1 OK");
        } catch (Exception e) { e.printStackTrace(); return; }

        // EXISTS + FIND
        try {
            System.out.println("exists(k1) = " + dao.exists(k1));
            print("findById(k1)", dao.findById(k1));
        } catch (Exception e) { e.printStackTrace(); }

        // UPDATE KEY (đổi c1 -> c2)
        try {
            dao.updateKey(k1, k2);
            print("after updateKey to k2, findById(k2)", dao.findById(k2));
        } catch (Exception e) { e.printStackTrace(); }

        // FIND BY SUBMISSION
        try {
            var bySub = dao.findBySubmission(sub);
            System.out.println("findBySubmission size = " + bySub.size());
        } catch (Exception e) { e.printStackTrace(); }

        // DELETE
        try {
            boolean ok = dao.delete(k2);
            System.out.println(">>> DELETE " + (ok ? "OK" : "FAIL"));
            print("after delete, findById(k2)", dao.findById(k2)); // mong đợi null
        } catch (Exception e) { e.printStackTrace(); }
    }
}
