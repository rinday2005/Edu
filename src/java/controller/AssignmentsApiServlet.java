package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import model.Assignment;
import service.AssignmentService;

@WebServlet(name = "AssignmentsApiServlet", urlPatterns = {"/api/assignments"})
public class AssignmentsApiServlet extends HttpServlet {

    private final AssignmentService assignmentService = new AssignmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String sectionIdStr = req.getParameter("sectionID");
        String lessionIdStr = req.getParameter("lessionID");
        try (PrintWriter out = resp.getWriter()) {
            List<Assignment> list;
            if (lessionIdStr != null && !lessionIdStr.isBlank()) {
                // Query by lessionID first
                try {
                    UUID lessionId = UUID.fromString(lessionIdStr);
                    list = assignmentService.findByLessionID(lessionId);
                    System.out.println("[AssignmentsApiServlet] Query by lessionID: " + lessionIdStr + ", found: " + (list != null ? list.size() : 0));
                } catch (IllegalArgumentException e) {
                    System.err.println("[AssignmentsApiServlet] Invalid lessionID format: " + lessionIdStr);
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"error\":\"Invalid lessionID format\"}");
                    return;
                }
            } else if (sectionIdStr != null && !sectionIdStr.isBlank()) {
                // If no lessionID, query by sectionID
                try {
                    UUID sectionId = UUID.fromString(sectionIdStr);
                    list = assignmentService.findBySectionID(sectionId);
                    System.out.println("[AssignmentsApiServlet] Query by sectionID: " + sectionIdStr + ", found: " + (list != null ? list.size() : 0));
                } catch (IllegalArgumentException e) {
                    System.err.println("[AssignmentsApiServlet] Invalid sectionID format: " + sectionIdStr);
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"error\":\"Invalid sectionID format\"}");
                    return;
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Missing sectionID or lessionID\"}");
                return;
            }
            
            if (list == null) {
                list = new java.util.ArrayList<>();
            }
            
            System.out.println("[AssignmentsApiServlet] Returning " + list.size() + " assignments");
            if (list.size() > 0) {
                System.out.println("[AssignmentsApiServlet] First assignment: " + list.get(0).getName() + " (lessionID: " + list.get(0).getLessionID() + ")");
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < list.size(); i++) {
                Assignment a = list.get(i);
                sb.append("{")
                  .append("\"assignmentID\":\"").append(a.getAssignmentID()).append("\",")
                  .append("\"name\":").append(toJson(a.getName())).append(",")
                  .append("\"description\":").append(toJson(a.getDescription())).append(",")
                  .append("\"order\":").append(a.getOrder())
                  .append("}");
                if (i < list.size() - 1) sb.append(",");
            }
            sb.append("]");
            String result = sb.toString();
            System.out.println("[AssignmentsApiServlet] Response JSON: " + result);
            out.write(result);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = resp.getWriter()) {
                out.write("{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }

    private String toJson(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}


