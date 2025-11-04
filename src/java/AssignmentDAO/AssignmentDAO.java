/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AssignmentDAO;


import model.Assignment;
import DAO.DBConnection;

import java.sql.*;
import java.util.*;

public class AssignmentDAO implements IAssignment {

    @Override
    public void create(Assignment a) {
        String sql = "INSERT INTO Assignment (userID, name, description, [Order], sectionID) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, a.getUserID());
            ps.setString(2, a.getName());
            ps.setString(3, a.getDescription());
            ps.setInt(4, a.getOrder());
            ps.setObject(5, a.getSectionID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Assignment findById(UUID id) {
        String sql = "SELECT * FROM Assignment WHERE assignmentID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractAssignment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Assignment> findAll() {
        List<Assignment> list = new ArrayList<>();
        String sql = "SELECT * FROM Assignment ORDER BY [Order] ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractAssignment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int update(Assignment a) {
        String sql = "UPDATE Assignment SET userID = ?, name = ?, description = ?, [Order] = ?, sectionID = ? WHERE assignmentID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, a.getUserID());
            ps.setString(2, a.getName());
            ps.setString(3, a.getDescription());
            ps.setInt(4, a.getOrder());
            ps.setObject(5, a.getSectionID());
            ps.setObject(6, a.getAssignmentID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(UUID id) {
        String sql = "DELETE FROM Assignment WHERE assignmentID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Assignment extractAssignment(ResultSet rs) throws SQLException {
        Assignment a = new Assignment();
        
        // assignmentID
        Object assignmentObj = rs.getObject("assignmentID");
        if (assignmentObj instanceof UUID) {
            a.setAssignmentID((UUID) assignmentObj);
        } else if (assignmentObj != null) {
            a.setAssignmentID(UUID.fromString(String.valueOf(assignmentObj)));
        }
        
        // userID
        Object userObj = rs.getObject("userID");
        if (userObj instanceof UUID) {
            a.setUserID((UUID) userObj);
        } else if (userObj != null) {
            a.setUserID(UUID.fromString(String.valueOf(userObj)));
        }
        
        // name, description, order
        a.setName(rs.getString("name"));
        a.setDescription(rs.getString("description"));
        a.setOrder(rs.getInt("Order"));
        
        // sectionID (có thể null)
        Object sectionObj = rs.getObject("sectionID");
        if (sectionObj instanceof UUID) {
            a.setSectionID((UUID) sectionObj);
        } else if (sectionObj != null) {
            a.setSectionID(UUID.fromString(String.valueOf(sectionObj)));
        } else {
            a.setSectionID(null);
        }
        
        return a;
    }
}
