// src/SectionsDAO/SectionDAO.java
package SectionsDAO;

import DAO.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.UUID;
import model.Sections;


public class SectionDAO implements ISectionDAO {

    private static final String SELECT_BY_COURSE =
        "SELECT sectionID, userID, status, name, description, courseID " +
        "FROM dbo.Sections WHERE courseID = ? AND status = 1 ORDER BY name ASC";

    @Override
    public List<Sections> getByCourseId(UUID courseID) {
        List<Sections> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_COURSE)) {
            ps.setString(1, courseID.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sections s = new Sections();
                    s.setSectionID(UUID.fromString(rs.getString("sectionID")));
                    String usr = rs.getString("userID");
                    s.setUserID(usr != null ? UUID.fromString(usr) : null);
                    s.setStatus(rs.getBoolean("status"));
                    s.setName(rs.getString("name"));
                    s.setDescription(rs.getString("description"));
                    s.setCourseID(UUID.fromString(rs.getString("courseID")));
                    list.add(s);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}