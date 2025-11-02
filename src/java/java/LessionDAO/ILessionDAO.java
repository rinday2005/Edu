/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LessionDAO;

import java.util.List;
import java.util.UUID;
import model.Lession;

/**
 *
 * @author ADMIN
 */
public interface ILessionDAO {
    List<Lession> findBySectionIds(List<UUID> sectionIds);
    boolean insert(Lession lesson);
    Lession findById(UUID lessonId);
    List<Lession> findAll();
    List<Lession> findBySectionId(UUID sectionId);
    boolean update(Lession lesson);
    boolean deleteById(UUID lessonId);
}
