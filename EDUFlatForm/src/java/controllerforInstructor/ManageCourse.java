/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerforInstructor;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import model.*;
import service.*;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageCourse", urlPatterns = {"/ManageCourse"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB - file tạm lưu RAM
        maxFileSize = 1024 * 1024 * 10, // 10MB mỗi file
        maxRequestSize = 1024 * 1024 * 50 // 50MB tổng request
)
public class ManageCourse extends HttpServlet {

    CourseServiceImpl courseservice = new CourseServiceImpl();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageLession</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageLession at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
           default:
               listcourse(request,response);
            

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "createcourse": {
                createcourse(request, response);
            }
            break;
        }

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

    private void createcourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        

            HttpSession session = request.getSession(false);
            if (session == null) {
                // Chưa đăng nhập hoặc mất cookie session
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }

            // Lấy user từ session (hỗ trợ cả "user" và "authUser" phòng nơi khác dùng tên khác)
            UUID userID = null;
            Object u = session.getAttribute("user");
            if (u == null) {
                u = session.getAttribute("authUser");
            }

            if (u instanceof User userObj) {
                // userObj.getUserID() có thể là UUID hoặc String -> ép kiểu an toàn
                Object idObj = userObj.getUserID();
                if (idObj instanceof UUID) {
                    userID = (UUID) idObj;
                } else if (idObj != null) {
                    userID = UUID.fromString(String.valueOf(idObj));
                }
            }
            // Dự phòng: nếu bạn còn set "userID" (String) riêng trong Login
            if (userID == null) {
                Object uidAttr = session.getAttribute("userID");
                if (uidAttr != null) {
                    userID = UUID.fromString(String.valueOf(uidAttr));
                }
            }

            if (userID == null) {
                // Có session nhưng thiếu thông tin người dùng -> coi như chưa đăng nhập
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }
            String name = request.getParameter("namecourse");
            String description = request.getParameter("descriptioncourse");
            int price = Integer.parseInt(request.getParameter("pricecourse"));
            String level = request.getParameter("levelcourse");
            boolean status = Boolean.parseBoolean(request.getParameter("status")); // Draft/Published/...
            Part filePart = request.getPart("picturecourse"); // name trùng trong form
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = request.getServletContext().getRealPath("") + "uploads" + File.separator + "course_images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Lưu file vào thư mục
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // Lưu đường dẫn (tương đối) vào DB
            String imagePath = "uploads/course_images/" + fileName;
            Courses course = new Courses(UUID.randomUUID(),userID, name, description, imagePath, 0, price, level, status);
            courseservice.insert(course);
            response.sendRedirect(request.getContextPath()+"/instructor/jsp/CourseManagement.jsp");

    }

    private void listcourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Courses> courselist = courseservice.getAllCourses();
    request.setAttribute("courselist", courselist);
    request.getRequestDispatcher("/instructor/jsp/CourseManagement.jsp").forward(request, response);
    }

}
