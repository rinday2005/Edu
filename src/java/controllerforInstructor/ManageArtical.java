/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerforInstructor;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import model.*;
import service.*;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageArtical", urlPatterns = {"/ManageArtical"})
public class ManageArtical extends HttpServlet {

    ArticleService articleservice = new ArticleService();

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
            out.println("<title>Servlet ManageArtical</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageArtical at " + request.getContextPath() + "</h1>");
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
        if (action == null || action.isEmpty()) {
            // Nếu không có action, mặc định load danh sách bài viết
            listartical(request, response);
            return;
        }

        switch (action) {
            case "listartical":
                listartical(request,response);
                break;
            case "deleteartical":
                deleteartical(request,response);
                break;
            case "updateartical":
                updateartical(request,response);
                break;
            default:
                listartical(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "createArtical":
                createartical(request, response);
                break;
            case "deleteartical":
                deleteartical(request,response);
                break;    
            case "updateartical":
                updateartical(request,response);
                break;
        }
    }

    private void createartical(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Không tạo session mới – chỉ dùng lại session đã có từ Login
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
            String title = request.getParameter("titleartical");
            String content = request.getParameter("contentArtical");
            String status = request.getParameter("statusArtical"); // đã thêm name trong form
            UUID articleID = UUID.randomUUID();                 // PK NOT NULL
            java.util.Date now = new java.util.Date();
            Article article = new Article(articleID, userID, now, status, title, content);
            articleservice.create(article);
            // Redirect về trang danh sách để hiển thị bài viết mới
            response.sendRedirect(request.getContextPath() + "/ManageArtical?action=listartical");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Tạo bài viết thất bại: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void listartical(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy userID từ session
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect(req.getContextPath() + "/login/jsp/login.jsp");
                return;
            }
            
            UUID userID = null;
            Object u = session.getAttribute("user");
            if (u instanceof User userObj) {
                Object idObj = userObj.getUserID();
                if (idObj instanceof UUID) {
                    userID = (UUID) idObj;
                } else if (idObj != null) {
                    userID = UUID.fromString(String.valueOf(idObj));
                }
            }
            
            // Dự phòng: nếu không lấy được từ user object, thử lấy từ session attribute "userID"
            if (userID == null) {
                Object uidAttr = session.getAttribute("userID");
                if (uidAttr != null) {
                    if (uidAttr instanceof UUID) {
                        userID = (UUID) uidAttr;
                    } else {
                        userID = UUID.fromString(String.valueOf(uidAttr));
                    }
                }
            }
            
            if (userID == null) {
                resp.sendRedirect(req.getContextPath() + "/login/jsp/login.jsp");
                return;
            }
            
            // Lấy danh sách bài viết của instructor hiện tại
            List<Article> allArticles = articleservice.findAllWithStats();
            List<Article> articallist = new ArrayList<>();
            for (Article article : allArticles) {
                if (article.getUserID() != null && article.getUserID().equals(userID)) {
                    articallist.add(article);
                }
            }
            
            req.setAttribute("articallist", articallist);
            req.setAttribute("fromServlet", "true");
            req.getRequestDispatcher("/instructor/jsp/ArticleManage.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi tải danh sách bài viết: " + e.getMessage());
            req.setAttribute("fromServlet", "true");
            req.getRequestDispatcher("/instructor/jsp/ArticleManage.jsp").forward(req, resp);
        }
    }


    private void deleteartical(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        UUID articalid;

        try {
            articalid = UUID.fromString(request.getParameter("id"));
            articleservice.deleteById(articalid);
        } catch (Exception e) {
            e.printStackTrace();

        }
        response.sendRedirect(request.getContextPath() + "/ManageArtical?action=listartical");
    }

    private void updateartical(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String method = request.getMethod();
        
        if ("GET".equals(method)) {
            // Hiển thị form edit
            showEditForm(request, response);
        } else if ("POST".equals(method)) {
            // Xử lý update
            handleUpdateArticle(request, response);
        }
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }
            
            UUID articleID = null;
            String articleIdParam = request.getParameter("id");
            if (articleIdParam != null && !articleIdParam.isEmpty()) {
                articleID = UUID.fromString(articleIdParam);
            }
            
            if (articleID == null) {
                response.sendRedirect(request.getContextPath() + "/ManageArtical?action=listartical");
                return;
            }
            
            // Lấy thông tin bài viết
            Article article = articleservice.findById(articleID);
            if (article == null) {
                request.setAttribute("error", "Không tìm thấy bài viết!");
                listartical(request, response);
                return;
            }
            
            // Kiểm tra quyền sở hữu
            UUID userID = null;
            Object u = session.getAttribute("user");
            if (u instanceof User userObj) {
                Object idObj = userObj.getUserID();
                if (idObj instanceof UUID) {
                    userID = (UUID) idObj;
                } else if (idObj != null) {
                    userID = UUID.fromString(String.valueOf(idObj));
                }
            }
            
            if (userID == null || !userID.equals(article.getUserID())) {
                request.setAttribute("error", "Bạn không có quyền sửa bài viết này!");
                listartical(request, response);
                return;
            }
            
            request.setAttribute("article", article);
            request.setAttribute("isEdit", true);
            listartical(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải form sửa: " + e.getMessage());
            listartical(request, response);
        }
    }
    
    private void handleUpdateArticle(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
                return;
            }
            
            UUID articleID = null;
            String articleIdParam = request.getParameter("id");
            if (articleIdParam != null && !articleIdParam.isEmpty()) {
                articleID = UUID.fromString(articleIdParam);
            }
            
            if (articleID == null) {
                request.setAttribute("error", "Thiếu thông tin bài viết!");
                listartical(request, response);
                return;
            }
            
            // Lấy thông tin bài viết hiện tại
            Article existingArticle = articleservice.findById(articleID);
            if (existingArticle == null) {
                request.setAttribute("error", "Không tìm thấy bài viết!");
                listartical(request, response);
                return;
            }
            
            // Kiểm tra quyền sở hữu
            UUID userID = null;
            Object u = session.getAttribute("user");
            if (u instanceof User userObj) {
                Object idObj = userObj.getUserID();
                if (idObj instanceof UUID) {
                    userID = (UUID) idObj;
                } else if (idObj != null) {
                    userID = UUID.fromString(String.valueOf(idObj));
                }
            }
            
            if (userID == null || !userID.equals(existingArticle.getUserID())) {
                request.setAttribute("error", "Bạn không có quyền sửa bài viết này!");
                listartical(request, response);
                return;
            }
            
            // Lấy dữ liệu từ form
            String title = request.getParameter("titleartical");
            String content = request.getParameter("contentArtical");
            String status = request.getParameter("statusArtical");
            
            // Cập nhật bài viết
            existingArticle.setTitle(title != null ? title : existingArticle.getTitle());
            existingArticle.setContent(content != null ? content : existingArticle.getContent());
            existingArticle.setStatus(status != null ? status : existingArticle.getStatus());
            
            // Update vào DB
            int updated = articleservice.update(existingArticle);
            
            if (updated > 0) {
                request.setAttribute("message", "✅ Cập nhật bài viết thành công!");
            } else {
                request.setAttribute("error", "❌ Cập nhật bài viết thất bại!");
            }
            
            response.sendRedirect(request.getContextPath() + "/ManageArtical?action=listartical");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi cập nhật bài viết: " + e.getMessage());
            listartical(request, response);
        }
    }
}
/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
