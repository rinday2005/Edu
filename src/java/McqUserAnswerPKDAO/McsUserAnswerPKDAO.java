// src/McqUserAnswerPKDAO/McqUserAnswerPKMapDAO.java
package McqUserAnswerPKDAO;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import model.McqUserAnswerPK;


public class McsUserAnswerPKDAO implements IMcsUserAnswerPKDAO {//done

    // Lưu chính: mỗi cặp khóa có mặt nghĩa là tồn tại (value không dùng)
    private final ConcurrentMap<McqUserAnswerPK, Boolean> store = new ConcurrentHashMap<>();

    // Chỉ mục phụ để support findBySubmission / findByChoice nhanh O(k) thay vì O(n)
    private final ConcurrentMap<UUID, Set<UUID>> submissionIndex = new ConcurrentHashMap<>();
    private final ConcurrentMap<UUID, Set<UUID>> choiceIndex     = new ConcurrentHashMap<>();

    // ===== Helpers =====
    private static Set<UUID> newKeySet() {
        // Tập hợp thread-safe dùng cho index
        return ConcurrentHashMap.newKeySet();
    }

    private void indexAdd(McqUserAnswerPK k) {
        submissionIndex.computeIfAbsent(k.getSubmissionId(), id -> newKeySet())
                       .add(k.getMcqChoiceId());
        choiceIndex.computeIfAbsent(k.getMcqChoiceId(), id -> newKeySet())
                   .add(k.getSubmissionId());
    }

    private void indexRemove(McqUserAnswerPK k) {
        Set<UUID> choices = submissionIndex.get(k.getSubmissionId());
        if (choices != null) {
            choices.remove(k.getMcqChoiceId());
            if (choices.isEmpty()) submissionIndex.remove(k.getSubmissionId(), Collections.emptySet());
        }
        Set<UUID> subs = choiceIndex.get(k.getMcqChoiceId());
        if (subs != null) {
            subs.remove(k.getSubmissionId());
            if (subs.isEmpty()) choiceIndex.remove(k.getMcqChoiceId(), Collections.emptySet());
        }
    }

    // ===== CRUD & Queries =====

    @Override
    public void insert(McqUserAnswerPK key) throws SQLException {
        if (key == null || key.getSubmissionId() == null || key.getMcqChoiceId() == null) {
            throw new IllegalArgumentException("Key/submissionId/mcqChoiceId must not be null");
        }
        // putIfAbsent để không overwrite im lặng
        Boolean prev = store.putIfAbsent(key, Boolean.TRUE);
        if (prev == null) {
            indexAdd(key);
        } // else: đã tồn tại, coi như insert idempotent
    }

    @Override
    public boolean delete(McqUserAnswerPK key) throws SQLException {
        if (key == null) return false;
        Boolean removed = store.remove(key);
        if (removed != null) {
            indexRemove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean exists(McqUserAnswerPK key) throws SQLException {
        return key != null && store.containsKey(key);
    }

    @Override
    public McqUserAnswerPK findById(McqUserAnswerPK key) throws SQLException {
        return exists(key) ? key : null;
    }

    @Override
    public List<McqUserAnswerPK> findAll() throws SQLException {
        return new ArrayList<>(store.keySet());
    }

    @Override
    public List<McqUserAnswerPK> findBySubmission(UUID submissionId) throws SQLException {
        if (submissionId == null) return Collections.emptyList();
        Set<UUID> choiceIds = submissionIndex.getOrDefault(submissionId, Collections.emptySet());
        if (choiceIds.isEmpty()) return Collections.emptyList();

        List<McqUserAnswerPK> out = new ArrayList<>(choiceIds.size());
        for (UUID choiceId : choiceIds) {
            McqUserAnswerPK k = new McqUserAnswerPK(submissionId, choiceId);
            if (store.containsKey(k)) out.add(k);
        }
        return out;
    }

    @Override
    public List<McqUserAnswerPK> findByChoice(UUID mcqChoiceId) throws SQLException {
        if (mcqChoiceId == null) return Collections.emptyList();
        Set<UUID> submissionIds = choiceIndex.getOrDefault(mcqChoiceId, Collections.emptySet());
        if (submissionIds.isEmpty()) return Collections.emptyList();

        List<McqUserAnswerPK> out = new ArrayList<>(submissionIds.size());
        for (UUID subId : submissionIds) {
            McqUserAnswerPK k = new McqUserAnswerPK(subId, mcqChoiceId);
            if (store.containsKey(k)) out.add(k);
        }
        return out;
    }

    @Override
    public boolean updateKey(McqUserAnswerPK oldKey, McqUserAnswerPK newKey) throws SQLException {
        if (oldKey == null || newKey == null) return false;

        // “Transactional-ish”: cố gắng đảm bảo hoặc đổi thành công, hoặc phục hồi
        if (!store.containsKey(oldKey)) return false;

        // Nếu newKey đã tồn tại, coi như "đổi" thành công (không tạo duplicate)
        if (store.containsKey(newKey)) {
            // Xóa bản ghi cũ để tránh trùng lặp
            delete(oldKey);
            return true;
        }

        // Insert mới → nếu insert ok thì xóa cũ; nếu insert fail (hiếm), giữ nguyên
        store.put(newKey, Boolean.TRUE);
        indexAdd(newKey);

        Boolean removed = store.remove(oldKey);
        if (removed != null) {
            indexRemove(oldKey);
            return true;
        } else {
            // rollback insert newKey nếu không xóa được oldKey (race condition)
            store.remove(newKey);
            indexRemove(newKey);
            return false;
        }
    }
}
