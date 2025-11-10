/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AssignmentDAO;


import model.Assignment;
import DAO.DBConnection;
import McqQuestionDAO.IMcqQuestionDAO;
import McqQuestionDAO.McqQuestionDAO;

import java.sql.*;
import java.util.*;
import model.McqQuestions;

public class AssignmentDAO implements IAssignment {

    @Override
    public void create(Assignment a) {
        // 先尝试包含lessionID的插入
        String sql = "INSERT INTO Assignment (userID, name, description, [Order], sectionID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, a.getUserID());
            ps.setString(2, a.getName());
            ps.setString(3, a.getDescription());
            ps.setInt(4, a.getOrder());
            ps.setObject(5, a.getSectionID());
            ps.executeUpdate();
            System.out.println("[AssignmentDAO] Successfully created assignment: " + a.getAssignmentID());
        } catch (SQLException e) {
            // 如果包含lessionID失败，尝试不包含lessionID（兼容旧数据库）
            if (e.getMessage() != null && (e.getMessage().contains("lessionID") || e.getMessage().contains("Invalid column"))) {
                System.out.println("[AssignmentDAO] lessionID column not found, trying without lessionID...");
                try {
                    String sqlOld = "INSERT INTO Assignment (userID, name, description, [Order], sectionID) VALUES (?, ?, ?, ?, ?)";
                    try (Connection con2 = DBConnection.getConnection();
                         PreparedStatement ps2 = con2.prepareStatement(sqlOld)) {
                        ps2.setObject(1, a.getUserID());
                        ps2.setString(2, a.getName());
                        ps2.setString(3, a.getDescription());
                        ps2.setInt(4, a.getOrder());
                        ps2.setObject(5, a.getSectionID());
                        ps2.executeUpdate();
                        System.out.println("[AssignmentDAO] Successfully created assignment (without lessionID): " + a.getAssignmentID());
                    }
                } catch (SQLException e2) {
                    System.err.println("[AssignmentDAO] Error creating assignment: " + e2.getMessage());
                    e2.printStackTrace();
                    throw new RuntimeException("Failed to create assignment: " + e2.getMessage(), e2);
                }
            } else {
                System.err.println("[AssignmentDAO] Error creating assignment: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to create assignment: " + e.getMessage(), e);
            }
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
            int count = 0;
            while (rs.next()) {
                Assignment a = extractAssignment(rs);
                list.add(a);
                count++;
            }
            System.out.println("[AssignmentDAO] findAll() found " + count + " assignments");
        } catch (SQLException e) {
            System.err.println("[AssignmentDAO] Error in findAll(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Assignment> findBySectionsID(UUID sectionID) {
        List<Assignment> list = new ArrayList<>();
        // 先检查lessionID字段是否存在
        try {
            String sql = "SELECT * FROM Assignment WHERE sectionID = ? ORDER BY [Order] ASC";
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, sectionID);
                
                try (ResultSet rs = ps.executeQuery()) {
                    int count = 0;
                    while (rs.next()) {
                        Assignment a = extractAssignment(rs);
                        list.add(a);
                        count++;
                        System.out.println("[AssignmentDAO] Found assignment: " + a.getAssignmentID() + ", name: " + a.getName());
                    }
                    System.out.println("[AssignmentDAO] Total assignments found: " + count);
                }
            }
        } catch (SQLException e) {
            // 如果lessionID字段不存在，返回空列表
            if (e.getMessage() != null && (e.getMessage().contains("lessionID") || e.getMessage().contains("Invalid column"))) {
                System.out.println("[AssignmentDAO] lessionID column not found, returning empty list");
                return list;
            } else {
                System.err.println("[AssignmentDAO] Error finding assignments by lessionID: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public int update(Assignment a) {
        // 先尝试包含lessionID的更新
        String sql = "UPDATE Assignment SET userID = ?, name = ?, description = ?, [Order] = ?, sectionID = ? WHERE assignmentID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, a.getUserID());
            ps.setString(2, a.getName());
            ps.setString(3, a.getDescription());
            ps.setInt(4, a.getOrder());
            ps.setObject(5, a.getSectionID());
            ps.setObject(6, a.getAssignmentID());
            int result = ps.executeUpdate();
            System.out.println("[AssignmentDAO] Successfully updated assignment: " + a.getAssignmentID());
            return result;
        } catch (SQLException e) {
            // 如果包含lessionID失败，尝试不包含lessionID（兼容旧数据库）
            if (e.getMessage() != null && (e.getMessage().contains("lessionID") || e.getMessage().contains("Invalid column"))) {
                System.out.println("[AssignmentDAO] lessionID column not found, trying update without lessionID...");
                try {
                    String sqlOld = "UPDATE Assignment SET userID = ?, name = ?, description = ?, [Order] = ?, sectionID = ? WHERE assignmentID = ?";
                    try (Connection con2 = DBConnection.getConnection();
                         PreparedStatement ps2 = con2.prepareStatement(sqlOld)) {
                        ps2.setObject(1, a.getUserID());
                        ps2.setString(2, a.getName());
                        ps2.setString(3, a.getDescription());
                        ps2.setInt(4, a.getOrder());
                        ps2.setObject(5, a.getSectionID());
                        ps2.setObject(6, a.getAssignmentID());
                        int result = ps2.executeUpdate();
                        System.out.println("[AssignmentDAO] Successfully updated assignment (without lessionID): " + a.getAssignmentID());
                        return result;
                    }
                } catch (SQLException e2) {
                    System.err.println("[AssignmentDAO] Error updating assignment: " + e2.getMessage());
                    e2.printStackTrace();
                    return 0;
                }
            } else {
                System.err.println("[AssignmentDAO] Error updating assignment: " + e.getMessage());
                e.printStackTrace();
                return 0;
            }
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

    @Override
    public Assignment getBySectionId(UUID sectionID) throws SQLException {
    String sql = "SELECT assignmentID, userID, name, description, [Order], sectionID "
               + "FROM Assignment WHERE sectionID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setObject(1, sectionID);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Assignment a = new Assignment();

                String assignmentIdStr = rs.getString("assignmentID");
                UUID assignmentID = UUID.fromString(assignmentIdStr);
                a.setAssignmentID(assignmentID);

                String userIdStr = rs.getString("userID");
                UUID userID = UUID.fromString(userIdStr);
                a.setUserID(userID);

                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                a.setOrder(rs.getInt("Order"));

                String sectionIdStr = rs.getString("sectionID");
                UUID sectionIdFromDb = UUID.fromString(sectionIdStr);
                a.setSectionID(sectionIdFromDb);

                // Lấy các câu hỏi
                IMcqQuestionDAO qDao = new McqQuestionDAO();
                List<McqQuestions> questions = qDao.getQuestionsByAssignment(a.getAssignmentID());
                a.setQuestions(questions);

                return a;
            }
        }
    }
    return null;
}

    @Override
    public Assignment getByAssignmentId(UUID assignmentID) throws SQLException {
        String sql = "SELECT assignmentID, userID, name, description, [Order], sectionID "
                   + "FROM Assignment WHERE assignmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, assignmentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Assignment a = new Assignment();
                    a.setAssignmentID(UUID.fromString(rs.getString("assignmentID")));
                    a.setUserID(UUID.fromString(rs.getString("userID")));
                    a.setName(rs.getString("name"));
                    a.setDescription(rs.getString("description"));
                    a.setOrder(rs.getInt("Order"));
                    a.setSectionID(UUID.fromString(rs.getString("sectionID")));

                    // Lấy danh sách câu hỏi
                    McqQuestionDAO qDao = new McqQuestionDAO();
                    List<McqQuestions> questions = qDao.getQuestionsByAssignment(assignmentID);
                    a.setQuestions(questions);

                    return a;
                }
            }
        }
        return null;
    }
}
