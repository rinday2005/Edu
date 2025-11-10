package controller;

import CommentsDAO.CommentsDAO;
import CommentsDAO.ICommentsDAO;
import UserDAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import model.Comments;
import model.User;

@WebServlet(name = "CommentsApiServlet", urlPatterns = {"/api/comments"})
public class CommentsApiServlet extends HttpServlet {

    private final ICommentsDAO commentsDAO = new CommentsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String lessionIdStr = req.getParameter("lessionID");
        String courseIdStr = req.getParameter("courseID");
        try (PrintWriter out = resp.getWriter()) {
            List<Comments> list;
            if (courseIdStr != null && !courseIdStr.isBlank()) {
                UUID courseId = UUID.fromString(courseIdStr);
                list = commentsDAO.findByCourseId(courseId);
            } else {
                if (lessionIdStr == null || lessionIdStr.isBlank()) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"error\":\"Missing lessionID or courseID\"}");
                    return;
                }
                UUID lessionId = UUID.fromString(lessionIdStr);
                list = commentsDAO.findByLessionId(lessionId);
            }
            // Manually build JSON
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            UserDAO userDAO = new UserDAO();
            for (int i = 0; i < list.size(); i++) {
                Comments c = list.get(i);
                String authorName = null;
                String avatarUrl = null;
                try {
                    model.User u = userDAO.getUserById(c.getUserID().toString());
                    if (u != null) {
                        authorName = (u.getFullName() != null && !u.getFullName().isBlank()) ? u.getFullName() : u.getUserName();
                        avatarUrl = u.getAvatarUrl();
                    }
                } catch (Exception ignore) {}
                sb.append("{")
                  .append("\"commentID\":\"").append(c.getCommentID()).append("\",")
                  .append("\"userID\":\"").append(c.getUserID()).append("\",")
                  .append("\"parentID\":").append(c.getParentID() != null ? "\"" + c.getParentID() + "\"" : "null").append(",")
                  .append("\"content\":").append(toJsonString(c.getContent())).append(",")
                  .append("\"authorName\":").append(toJsonString(authorName != null ? authorName : "Người dùng")).append(",")
                  .append("\"avatarUrl\":").append(toJsonString(avatarUrl)).append(",")
                  .append("\"createAt\":\"").append(c.getCreateAt() != null ? c.getCreateAt().getTime() : System.currentTimeMillis()).append("\"")
                  .append("}");
                if (i < list.size() - 1) sb.append(",");
            }
            sb.append("]");
            out.write(sb.toString());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Support update/delete via _method=PUT / _method=DELETE (some containers don't parse x-www-form-urlencoded for PUT/DELETE)
        String methodOverride = req.getParameter("_method");
        if ("PUT".equalsIgnoreCase(methodOverride)) {
            String commentIdStr = req.getParameter("commentID");
            String content = req.getParameter("content");
            boolean isAdmin = "Admin".equalsIgnoreCase(user.getRole());
            if (commentIdStr == null || content == null || content.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            boolean ok = new CommentsDAO().updateContent(UUID.fromString(commentIdStr), UUID.fromString(user.getUserID()), content, isAdmin);
            resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
            if (ok) {
                try (PrintWriter out = resp.getWriter()) { out.write("{\"success\":true}"); }
            }
            return;
        }
        if ("DELETE".equalsIgnoreCase(methodOverride)) {
            String commentIdStr = req.getParameter("commentID");
            boolean isAdmin = "Admin".equalsIgnoreCase(user.getRole());
            if (commentIdStr == null) { resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); return; }
            boolean ok = new CommentsDAO().delete(UUID.fromString(commentIdStr), UUID.fromString(user.getUserID()), isAdmin);
            resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
            if (ok) {
                try (PrintWriter out = resp.getWriter()) { out.write("{\"success\":true}"); }
            }
            return;
        }

        String lessionIdStr = req.getParameter("lessionID");
        String content = req.getParameter("content");
        String parentIdStr = req.getParameter("parentID");
        try (PrintWriter out = resp.getWriter()) {
            if (lessionIdStr == null || content == null || content.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Missing params\"}");
                return;
            }
            Comments c = new Comments();
            c.setCommentID(UUID.randomUUID());
            c.setUserID(UUID.fromString(user.getUserID()));
            c.setCreateAt(new Date());
            c.setContent(content);
            c.setLessionID(UUID.fromString(lessionIdStr));
            if (parentIdStr != null && !parentIdStr.isBlank()) {
                c.setParentID(UUID.fromString(parentIdStr));
            }
            boolean ok = commentsDAO.insert(c);
            if (!ok) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            out.write("{\"success\":true,\"commentID\":\"" + c.getCommentID() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null) { resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); return; }
        String commentIdStr = req.getParameter("commentID");
        String content = req.getParameter("content");
        boolean isAdmin = "Admin".equalsIgnoreCase(user.getRole());
        if (commentIdStr == null || content == null) { resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); return; }
        boolean ok = new CommentsDAO().updateContent(UUID.fromString(commentIdStr), UUID.fromString(user.getUserID()), content, isAdmin);
        resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null) { resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); return; }
        String commentIdStr = req.getParameter("commentID");
        boolean isAdmin = "Admin".equalsIgnoreCase(user.getRole());
        if (commentIdStr == null) { resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); return; }
        boolean ok = new CommentsDAO().delete(UUID.fromString(commentIdStr), UUID.fromString(user.getUserID()), isAdmin);
        resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
    }

    private String toJsonString(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}


