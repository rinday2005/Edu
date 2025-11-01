/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author ADMIN
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    private static final String PU_NAME = "PRJ_AssignmentPU"; // <-- đổi tên cho đúng
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory(PU_NAME);

    private JpaUtil() {}

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }
}
