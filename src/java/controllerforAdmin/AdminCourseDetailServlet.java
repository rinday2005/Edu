package controllerforAdmin;

import CourseDAO.CourseDAO;
import SectionsDAO.SectionDAO;
import LessionDAO.LessionDAO;
import AssignmentDAO.AssignmentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "AdminCourseDetailServlet", urlPatterns = {"/admin/course/detail"})
public class AdminCourseDetailServlet extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();
    private final SectionDAO sectionDAO = new SectionDAO();
    private final LessionDAO lessionDAO = new LessionDAO();
    private final AssignmentDAO assignmentDAO = new AssignmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object role = request.getSession().getAttribute("role");
        if (role == null || !"Admin".equals(String.valueOf(role))) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/courses");
            return;
        }
        try {
            java.util.UUID cid = java.util.UUID.fromString(id);
            model.Courses course = courseDAO.findById(cid);
            request.setAttribute("course", course);

            java.util.List<model.Sections> sections = sectionDAO.findByCourseId(cid);
            request.setAttribute("sections", sections);

            // lessons theo danh sách sectionIds
            java.util.List<java.util.UUID> sectionIds = new java.util.ArrayList<>();
            for (model.Sections s : sections) if (s.getSectionID() != null) sectionIds.add(s.getSectionID());
            java.util.List<model.Lession> lessons = lessionDAO.findBySectionIds(sectionIds);
            request.setAttribute("lessons", lessons);

            java.util.List<model.Assignment> assignments = assignmentDAO.findAll();
            // lọc assignment theo sectionId thuộc course
            java.util.List<model.Assignment> asg = new java.util.ArrayList<>();
            java.util.Set<java.util.UUID> secSet = new java.util.HashSet<>(sectionIds);
            for (model.Assignment a : assignments) if (a.getSectionID() != null && secSet.contains(a.getSectionID())) asg.add(a);
            request.setAttribute("assignments", asg);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/admin/jsp/course-detail.jsp").forward(request, response);
    }
}


