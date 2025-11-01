/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import model.Courses;
import model.Lession;
import service.CourseDetailDTO;
import service.CourseServiceImpl;

/**
 *
 * @author trank
 */
public class CourseServletController extends HttpServlet {
    private CourseServiceImpl courseService = new CourseServiceImpl();


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CourseServletController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseServletController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.equals("list")) {
            showCourseList(request, response);
            return;
        }

        switch (action) {
            case "detail":
                showCourseDetail(request, response);
                break;
            case "showall":
                showAllCourse(request, response);
                break;
            default:
                showCourseList(request, response);
                break;
        }
    }

    private void showCourseList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Courses> courses = courseService.getAllCourses();
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/eduHome/eduHome.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách khóa học");
        }
    }

    private void showCourseDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID khóa học");
                return;
            }

            try {
                UUID courseId = UUID.fromString(idStr);
                CourseDetailDTO dto = courseService.getCourseDetail(courseId);

                if (dto == null || dto.course == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy khóa học");
                    return;
                }

                // Calculate total lessons and minutes directly from DTO
                Map<UUID, List<Lession>> lessonsByUuid = dto.lessonsMap != null ? dto.lessonsMap : java.util.Collections.emptyMap();

                int totalLessons = lessonsByUuid.values().stream().mapToInt(List::size).sum();
                int totalMinutes = lessonsByUuid.values().stream()
                        .flatMap(Collection::stream)
                        .mapToInt(Lession::getVideoDuration)
                        .sum();

                // Set attributes for JSP
                request.setAttribute("course", dto.course);
                request.setAttribute("sections", dto.sections != null ? dto.sections : java.util.Collections.emptyList());
                request.setAttribute("lessonsMap", lessonsByUuid);
                request.setAttribute("totalLessons", totalLessons);
                request.setAttribute("totalMinutes", totalMinutes);

                System.out.println("[v0] Course Detail Loaded: " + dto.course.getName() +
                        " | Sections: " + (dto.sections != null ? dto.sections.size() : 0) +
                        " | Lessons: " + totalLessons);

            } catch (IllegalArgumentException ex) {
                Courses freeCourse = new Courses();
                freeCourse.setName("Khóa học miễn phí #" + idStr);
                freeCourse.setDescription("Đây là nội dung khóa học miễn phí demo.");
                freeCourse.setLevel("Beginner");

                request.setAttribute("course", freeCourse);
                request.setAttribute("sections", java.util.Collections.emptyList());
                request.setAttribute("lessonsMap", java.util.Collections.emptyMap());
                request.setAttribute("totalLessons", 0);
                request.setAttribute("totalMinutes", 0);
            }

            request.getRequestDispatcher("/course/lessonNewbie.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("[v0] Lỗi khi tải chi tiết khóa học:");
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Lỗi khi tải chi tiết khóa học: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // dùng chung logic GET
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void showAllCourse(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}