/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import model.Courses;
import model.Lession;
import model.Sections;
import service.CourseDetailDTO;
import service.CourseServiceImpl;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RoomServlet", urlPatterns = {"/room"})
public class RoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseServiceImpl courseService = new CourseServiceImpl();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy sectionID và courseID nếu có
        String sectionIDStr = request.getParameter("sectionID");
        String courseIDStr  = request.getParameter("courseID");

        UUID sectionID = null;
        UUID courseID  = null;

        if (sectionIDStr != null && !sectionIDStr.trim().isEmpty()) {
            try {
                sectionID = UUID.fromString(sectionIDStr.trim());
            } catch (IllegalArgumentException e) {
                sectionID = null;
            }
        }

        if (courseIDStr != null && !courseIDStr.trim().isEmpty()) {
            try {
                courseID = UUID.fromString(courseIDStr.trim());
            } catch (IllegalArgumentException e) {
                courseID = null;
            }
        }

        try {
            // Nếu chưa có courseID nhưng có sectionID, lấy từ service
            if (courseID == null && sectionID != null) {
                courseID = courseService.getCourseIdBySectionId(sectionID);
            }

            if (courseID == null) {
                // Không có thông tin đủ để load khóa học
                response.sendRedirect(request.getContextPath() + "/error.jsp?msg=missingCourseOrSection");
                return;
            }

            // Lấy chi tiết khóa học
            CourseDetailDTO dto = courseService.getCourseDetail(courseID);
            Courses course        = dto.getCourse();
            List<Sections> sections   = dto.getSections();
            Map<UUID, List<Lession>> lessonsMap = dto.getLessonsMap();

            // Set dữ liệu vào request để JSP dùng JSTL/EL
            request.setAttribute("course", course);
            request.setAttribute("sections", sections);
            request.setAttribute("lessonsMap", lessonsMap);
            request.setAttribute("currentSectionID", sectionID);
            request.setAttribute("currentCourseID",courseID);
            // Chuyển tới room.jsp
            request.getRequestDispatcher("/learner/jsp/Course/room.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading room page", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
