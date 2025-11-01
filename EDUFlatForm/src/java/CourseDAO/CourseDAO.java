// ==============================
// CoursesDAO.java  (JDBC, SQL Server)
// ==============================
package CourseDAO;

 // nếu bạn dùng User làm FK
import DAO.DBConnection; // đổi sang class cung cấp Connection của bạn

import java.sql.*;
import java.util.*;
import java.util.UUID;
import model.Courses;

public class CourseDAO implements ICourseDAO {
     private static final String SELECT_ONE =
        "SELECT c.courseID, c.userID, c.name, c.description, c.imgURL, " +
        "       c.rating, c.price, c.level, c.isApproved, u.fullName AS instructorName " +
        "FROM dbo.Courses c " +
        "LEFT JOIN [User] u ON u.userID = c.userID " +   // <--- thêm dấu cách ở đây
        "WHERE c.courseID = ?";
     
    @Override
    public List<Courses> getAllCourses() {
        List<Courses> list = new ArrayList<>();
        String sql = """
            SELECT c.courseID, c.userID, c.name, c.description, c.imgURL, 
                   c.rating, c.price, c.level, c.isApproved, u.fullName AS instructorName
            FROM Courses c
            LEFT JOIN [User] u ON c.userID = u.userID
            WHERE c.isApproved = 1
        """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Courses c = new Courses();
                c.setCourseID(UUID.fromString(rs.getString("courseID")));
                c.setUserID(rs.getString("userID") != null 
                            ? UUID.fromString(rs.getString("userID")) : null);
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setImgURL(rs.getString("imgURL"));
                c.setRating(rs.getInt("rating"));
                c.setPrice(rs.getInt("price"));
                c.setLevel(rs.getString("level"));
                c.setApproved(rs.getBoolean("isApproved"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Courses getCourseById(UUID courseId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ONE)) {
            ps.setString(1, courseId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Courses c = new Courses();
                    c.setCourseID(UUID.fromString(rs.getString("courseID")));
                    String userStr = rs.getString("userID");
                    c.setUserID(userStr != null ? UUID.fromString(userStr) : null);
                    c.setName(rs.getString("name"));
                    c.setDescription(rs.getString("description"));
                    c.setImgURL(rs.getString("imgURL"));
                    c.setRating(rs.getInt("rating"));
                    c.setPrice(rs.getInt("price"));
                    c.setLevel(rs.getString("level"));
                    c.setApproved(rs.getBoolean("isApproved"));
                    return c;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}

