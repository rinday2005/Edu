package CartDAO;

import DAO.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.UUID;
import model.Courses;

public class CartDAO implements ICartDAO{
    @Override
    public void addToCart(UUID userID, UUID courseID){
        String query = "INSERT INTO cart (userID, courseID) VALUES (?, ?);";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userID.toString());
            ps.setString(1, courseID.toString());
            ps.executeUpdate();
    }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteCourse(UUID userID){
        String query = "DELETE FROM cart WHERE userID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, userID.toString());
        ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteCourseByID(UUID courseID){
        String query = "DELETE FROM cart WHERE courseID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, courseID.toString());;
        ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int countItemCart(UUID userID) {
    String query = "SELECT COUNT(*) as totalItems FROM cart WHERE userID = ?";
    try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, userID.toString());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("totalItems");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } 
    return 0;
}
    @Override
   public boolean isCourseExist(UUID userID, UUID courseID) {
    String query = "SELECT * FROM cart WHERE userID = ? AND courseID = ?";
    try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, userID.toString());
        ps.setString(1, courseID.toString());
        ResultSet rs = ps.executeQuery();
        // Nếu có dòng kết quả -> tồn tại
        if (rs.next()) {
            return true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    public ArrayList<Courses> findCourseInCart(UUID userID){
        ArrayList<Courses> CourseInCart = new ArrayList<>();
        String query = "SELECT c.* FROM course c JOIN cart ca ON c.courseID = ca.courseID WHERE ca.userID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, userID.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
                Courses c = new Courses();
                c.setCourseID(UUID.fromString(rs.getString("courseID")));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setImgURL(rs.getString("imgURL"));
                c.setRating(rs.getInt("rating"));
                c.setPrice(rs.getInt("price"));
                c.setLevel(rs.getString("level"));
                CourseInCart.add(c);                 
            }
        return CourseInCart;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return CourseInCart;
    }
    public int getTotalAmount(UUID userID) {
    int total = 0;
    String query = "SELECT SUM(c.price) AS totalAmount " +
                   "FROM Cart ca JOIN Course c ON ca.courseID = c.courseID " +
                   "WHERE ca.userID = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, userID.toString());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getInt("totalAmount");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return total;
}
    
     
        public static void main(String[] args) {
    CartDAO dao = new CartDAO(); // hoặc CartDAO nếu hàm nằm trong lớp CartDAO
    UUID userID = UUID.fromString("1sdwdsaw");

    int count = dao.countItemCart(userID);
    System.out.println("Số lượng khóa học trong giỏ hàng của userID " + userID + " là: " + count);
}

}