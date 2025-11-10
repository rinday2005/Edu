package McqUserAnswerDAO;

import java.sql.*;
import java.util.*;
import model.McqUserAnswer;
import model.McqUserAnswerPK;
import DAO.DBConnection;
import java.lang.System.Logger.Level;
import org.jboss.logging.Logger;

public class McqUserAnswerDAO implements IMcqUserAnswerDAO {

    @Override
    public void insert(McqUserAnswerPK key) throws SQLException {
        String sql = "INSERT INTO McqUserAnswer (SubmissionId, McqChoiceId) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, key.getSubmissionId().toString());
            ps.setString(2, key.getMcqChoiceId().toString());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(McqUserAnswerPK key) throws SQLException {
        String sql = "DELETE FROM McqUserAnswer WHERE SubmissionId = ? AND McqChoiceId = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, key.getSubmissionId().toString());
            ps.setString(2, key.getMcqChoiceId().toString());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean exists(McqUserAnswerPK key) throws SQLException {
        String sql = "SELECT COUNT(*) FROM McqUserAnswer WHERE SubmissionId = ? AND McqChoiceId = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, key.getSubmissionId().toString());
            ps.setString(2, key.getMcqChoiceId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public McqUserAnswer findById(McqUserAnswerPK key) throws SQLException {
        String sql = "SELECT * FROM McqUserAnswer WHERE SubmissionId = ? AND McqChoiceId = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, key.getSubmissionId().toString());
            ps.setString(2, key.getMcqChoiceId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new McqUserAnswer(
                            UUID.fromString(rs.getString("SubmissionId")),
                            UUID.fromString(rs.getString("McqChoiceId"))
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<McqUserAnswer> findAll() throws SQLException {
        String sql = "SELECT * FROM McqUserAnswer";
        List<McqUserAnswer> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new McqUserAnswer(
                        UUID.fromString(rs.getString("SubmissionId")),
                        UUID.fromString(rs.getString("McqChoiceId"))
                ));
            }
        }
        return list;
    }

    @Override
    public List<McqUserAnswer> findBySubmission(UUID submissionId) throws SQLException {
        String sql = "SELECT * FROM McqUserAnswer WHERE SubmissionId = ?";
        List<McqUserAnswer> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, submissionId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new McqUserAnswer(
                            UUID.fromString(rs.getString("SubmissionId")),
                            UUID.fromString(rs.getString("McqChoiceId"))
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public List<McqUserAnswer> findByChoice(UUID mcqChoiceId) throws SQLException {
        String sql = "SELECT * FROM McqUserAnswer WHERE McqChoiceId = ?";
        List<McqUserAnswer> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mcqChoiceId.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new McqUserAnswer(
                            UUID.fromString(rs.getString("SubmissionId")),
                            UUID.fromString(rs.getString("McqChoiceId"))
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateKey(McqUserAnswerPK oldKey, McqUserAnswerPK newKey) throws SQLException {
        if (!exists(oldKey)) return false;
        delete(oldKey);
        insert(newKey);
        return true;
    }

    @Override
    public int[] bulkInsert(List<McqUserAnswerPK> keys) throws SQLException {
        String sql = "INSERT INTO McqUserAnswer (SubmissionId, McqChoiceId) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (McqUserAnswerPK key : keys) {
                ps.setString(1, key.getSubmissionId().toString());
                ps.setString(2, key.getMcqChoiceId().toString());
                ps.addBatch();
            }
            return ps.executeBatch();
        }
    }
    @Override
     public void saveUserAnswers(UUID submissionId, List<UUID> selectedChoiceIds) throws SQLException {
        String sql = "INSERT INTO McqUserAnswer (SubmissionId, McqChoiceId) VALUES (?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        for (UUID choiceId : selectedChoiceIds) {
            ps.setString(1, submissionId.toString());
            ps.setString(2, choiceId.toString());
            ps.addBatch();
        }

        ps.executeBatch();
    }
    }

    @Override
     public void deleteBySubmissionID(UUID submissionId) throws SQLException {
        String sql = "DELETE FROM McqUserAnswer WHERE SubmissionId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, submissionId.toString());
            ps.executeUpdate();
        }
    }
    @Override
    public List<UUID> findChoicesBySubmissionID(UUID submissionID) throws SQLException {
        List<UUID> choiceIds = new ArrayList<>();
    String sql = "SELECT McqChoiceId FROM McqUserAnswer WHERE SubmissionId = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, submissionID.toString());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                choiceIds.add(UUID.fromString(rs.getString("McqChoiceId")));
            }
        }
    }   
    return choiceIds;
    }
}
