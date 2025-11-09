package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.User;
import model.Courses;
import UserCourseDAO.UserCourseDAO;
import UserCourseDAO.IUserCourseDAO;
import model.UserCourse;
import CourseDAO.CourseDAO;
import CourseDAO.ICourseDAO;

@WebServlet(name = "MyCoursesServlet", urlPatterns = {"/learner/mycourses"})
public class MyCoursesServlet extends HttpServlet {

    private IUserCourseDAO userCourseDAO = new UserCourseDAO();
    private ICourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }

        Object userObj = session.getAttribute("user");
        if (userObj == null || !(userObj instanceof User)) {
            response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
            return;
        }

        User user = (User) userObj;
        
        try {
            UUID userID = UUID.fromString(user.getUserID());
            
            // Get all courses purchased by this user
            List<Courses> myCourses = getMyCourses(userID);
            
            request.setAttribute("myCourses", myCourses);
            request.setAttribute("totalCourses", myCourses != null ? myCourses.size() : 0);
            
            // Forward to JSP
            request.getRequestDispatcher("/learner/jsp/Course/myCourses.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("[MyCoursesServlet] Error loading my courses: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("myCourses", new ArrayList<Courses>());
            request.setAttribute("totalCourses", 0);
            request.setAttribute("error", "Lỗi khi tải danh sách khóa học của tôi");
            request.getRequestDispatcher("/learner/jsp/Course/myCourses.jsp").forward(request, response);
        }
    }

    private List<Courses> getMyCourses(UUID userID) {
        List<Courses> courses = new ArrayList<>();
        try {
            System.out.println("[MyCoursesServlet] Getting courses for userID: " + userID);
            
            // Get UserCourse list for this user
            List<UserCourse> userCourses = userCourseDAO.findByUser(userID);
            System.out.println("[MyCoursesServlet] Found " + (userCourses != null ? userCourses.size() : 0) + " UserCourse entries");
// Get course details for each UserCourse
            if (userCourses != null && !userCourses.isEmpty()) {
                for (UserCourse userCourse : userCourses) {
                    if (userCourse != null) {
                        System.out.println("[MyCoursesServlet] Processing UserCourse - userCourseID: " + userCourse.getUserCourseID() + 
                                         ", userID: " + userCourse.getUserID() + 
                                         ", courseID: " + userCourse.getCourseID());
                        
                        if (userCourse.getCourseID() != null) {
                            try {
                                Courses course = courseDAO.findById(userCourse.getCourseID());
                                if (course != null) {
                                    System.out.println("[MyCoursesServlet] Found course: " + course.getName() + " (ID: " + course.getCourseID() + ")");
                                    courses.add(course);
                                } else {
                                    System.err.println("[MyCoursesServlet] WARNING: Course not found for courseID: " + userCourse.getCourseID());
                                }
                            } catch (Exception e) {
                                System.err.println("[MyCoursesServlet] Error finding course with ID " + userCourse.getCourseID() + ": " + e.getMessage());
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("[MyCoursesServlet] WARNING: UserCourse has null courseID");
                        }
                    }
                }
            } else {
                System.out.println("[MyCoursesServlet] No UserCourse entries found for userID: " + userID);
            }
            
            System.out.println("[MyCoursesServlet] Total courses retrieved: " + courses.size());
        } catch (Exception e) {
            System.err.println("[MyCoursesServlet] Error in getMyCourses: " + e.getMessage());
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
