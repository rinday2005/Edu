/* ===== IUser.java ===== */
package UserDAO;

import java.util.*;
import model.User;

public interface IUserDAO {
    User checkLogin(String username, String password);
    User getUserById(String userID);
    boolean createUser(User user);
    boolean updateProfile(User user);
    boolean softDeleteUser(String userID, String lastModifiedID);
    List<User> getAllUsers();
    User getUserByEmail(String email);

    boolean updateGoogleUser(User user);
    boolean updatePassword(String email, String newPassword);
}
