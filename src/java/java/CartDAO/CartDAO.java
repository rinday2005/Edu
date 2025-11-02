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
            ps.setString(2, courseID.toString());
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
        ps.setString(2, courseID.toString());
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
        String query = "SELECT c.* FROM courses c JOIN cart ca ON c.courseID = ca.courseID WHERE ca.userID = ?";
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
                   "FROM Cart ca JOIN Courses c ON ca.courseID = c.courseID " +
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
        CartDAO dao = new CartDAO();

        // Giả sử có sẵn các UUID trong database
        UUID userID = UUID.fromString("99E99036-D5D3-4DED-9FBB-74BBF1A7F2BB");
        UUID courseID = UUID.fromString("2816A3AF-5A5C-4FC3-B3BB-ABE5C8A13FEC");

        // Thêm khóa học vào giỏ
//        System.out.println("=== Test addToCart ===");
//        dao.addToCart(userID, courseID);
//        System.out.println("Đã thêm course vào cart.");

//        // Kiểm tra tồn tại
//        System.out.println("=== Test isCourseExist ===");
//        boolean exists = dao.isCourseExist(userID, courseID);
//        System.out.println("Course tồn tại trong cart: " + exists);
//
//        // Đếm số lượng item trong cart
//        System.out.println("=== Test countItemCart ===");
//        int count = dao.countItemCart(userID);
//        System.out.println("Số item trong cart: " + count);
//
//        // Lấy danh sách course trong cart
//        System.out.println("=== Test findCourseInCart ===");
//        ArrayList<Courses> list = dao.findCourseInCart(userID);
//        for (Courses c : list) {
//            System.out.println("Course: " + c.getName() + " - Giá: " + c.getPrice());
//        }
//
        // Tính tổng tiền
//        System.out.println("=== Test getTotalAmount ===");
//        int total = dao.getTotalAmount(userID);
//        System.out.println("Tổng tiền trong cart: " + total);
//
//        // Xóa khóa học theo ID
//        System.out.println("=== Test deleteCourseByID ===");
//        dao.deleteCourseByID(courseID);
//        System.out.println("Đã xóa course khỏi cart.");
//
//        // Đếm lại
//        count = dao.countItemCart(userID);
//        System.out.println("Số item sau khi xóa: " + count);
    }


}