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
}
