/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LessionDAO;

import DAO.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import model.Lession;

/**
 *
 * @author ADMIN
 */
public class LessionDAO implements ILessionDAO {

    @Override
    public List<Lession> findBySectionIds(List<UUID> sectionIds) {
        List<Lession> list = new ArrayList<>();
        if (sectionIds == null || sectionIds.isEmpty()) return list;

        String placeholders = String.join(",", Collections.nCopies(sectionIds.size(), "?"));
        String sql =
            "SELECT lessionID, userID, name, description, videoUrl, sectionID, videoDuration, status " +
            "FROM dbo.Lession " +
            "WHERE sectionID IN (" + placeholders + ") AND status = 1 " +
            "ORDER BY name ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            for (UUID id : sectionIds) ps.setString(i++, id.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lession l = new Lession();
                    l.setLessionID(UUID.fromString(rs.getString("lessionID")));
                    String usr = rs.getString("userID");
                    l.setUserID(usr != null ? UUID.fromString(usr) : null);
                    l.setName(rs.getString("name"));
                    l.setDescription(rs.getString("description"));
                    l.setVideoUrl(rs.getString("videoUrl"));
                    l.setSectionID(UUID.fromString(rs.getString("sectionID")));
                    l.setVideoDuration(rs.getInt("videoDuration"));
                    l.setStatus(rs.getBoolean("status"));
                    list.add(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("[ERROR] LessionDAO.findBySectionIds() thất bại: " + e.getMessage());
        }
        return list;
    }
}
