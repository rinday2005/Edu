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
import java.sql.Types;
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
    
    private static final String TABLE = "dbo.Lession";
    
    private static final String INSERT_SQL = "INSERT INTO " + TABLE + 
        " (lessionID, userID, status, name, description, videoUrl, sectionID, videoDuration) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_BY_ID_SQL = "SELECT lessionID, userID, status, name, description, videoUrl, sectionID, videoDuration " +
        "FROM " + TABLE + " WHERE lessionID = ?";
    
    private static final String SELECT_ALL_SQL = "SELECT lessionID, userID, status, name, description, videoUrl, sectionID, videoDuration " +
        "FROM " + TABLE + " ORDER BY name ASC";
    
    private static final String SELECT_BY_SECTION_SQL = "SELECT lessionID, userID, status, name, description, videoUrl, sectionID, videoDuration " +
        "FROM " + TABLE + " WHERE sectionID = ? ORDER BY name ASC";
    
    private static final String UPDATE_SQL = "UPDATE " + TABLE + 
        " SET userID=?, status=?, name=?, description=?, videoUrl=?, sectionID=?, videoDuration=? " +
        "WHERE lessionID=?";
    
    private static final String DELETE_SQL = "DELETE FROM " + TABLE + " WHERE lessionID = ?";
    
    private void setNullableUuid(PreparedStatement ps, int idx, UUID value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.VARCHAR);
        } else {
            ps.setString(idx, value.toString());
        }
    }
    
    private UUID getUuid(ResultSet rs, String col) throws SQLException {
        String s = rs.getString(col);
        return (s == null) ? null : UUID.fromString(s);
    }
    
    private Lession mapRow(ResultSet rs) throws SQLException {
        Lession l = new Lession();
        l.setLessionID(getUuid(rs, "lessionID"));
        l.setUserID(getUuid(rs, "userID"));
        l.setName(rs.getString("name"));
        l.setDescription(rs.getString("description"));
        l.setVideoUrl(rs.getString("videoUrl"));
        l.setSectionID(getUuid(rs, "sectionID"));
        l.setVideoDuration(rs.getInt("videoDuration"));
        l.setStatus(rs.getBoolean("status"));
        return l;
    }

    @Override
    public List<Lession> findBySectionIds(List<UUID> sectionIds) {
        List<Lession> list = new ArrayList<>();
        if (sectionIds == null || sectionIds.isEmpty()) return list;

        String placeholders = String.join(",", Collections.nCopies(sectionIds.size(), "?"));
        String sql =
            "SELECT lessionID, userID, status, name, description, videoUrl, sectionID, videoDuration " +
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
    
    @Override
    public boolean insert(Lession lesson) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            
            // Thứ tự: lessionID, userID, status, name, description, videoUrl, sectionID, videoDuration
            ps.setString(1, lesson.getLessionID().toString());
            setNullableUuid(ps, 2, lesson.getUserID());
            ps.setBoolean(3, lesson.isStatus());
            ps.setString(4, lesson.getName());
            ps.setString(5, lesson.getDescription());
            ps.setString(6, lesson.getVideoUrl());
            setNullableUuid(ps, 7, lesson.getSectionID());
            ps.setInt(8, lesson.getVideoDuration());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Lession findById(UUID lessonId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {
            
            ps.setString(1, lessonId.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Lession> findAll() {
        List<Lession> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<Lession> findBySectionId(UUID sectionId) {
        List<Lession> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_SECTION_SQL)) {
            
            setNullableUuid(ps, 1, sectionId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public boolean update(Lession lesson) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
            
            // Thứ tự: userID, status, name, description, videoUrl, sectionID, videoDuration, lessionID (WHERE)
            setNullableUuid(ps, 1, lesson.getUserID());
            ps.setBoolean(2, lesson.isStatus());
            ps.setString(3, lesson.getName());
            ps.setString(4, lesson.getDescription());
            ps.setString(5, lesson.getVideoUrl());
            setNullableUuid(ps, 6, lesson.getSectionID());
            ps.setInt(7, lesson.getVideoDuration());
            ps.setString(8, lesson.getLessionID().toString());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteById(UUID lessonId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            
            ps.setString(1, lessonId.toString());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
