// UserService.java
package service;
import model.User;
import UserDAO.IUserDAO;
import UserDAO.UserDAO;
import java.util.List;


public class UserServiceImpl {

    private final IUserDAO userDAO;

    // Constructor mặc định
    public UserServiceImpl() {
        this.userDAO = new UserDAO();
    }

    // Constructor DI (nếu dùng framework)
    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User checkLoginUser(String usernameOrEmail, String password) {
        if (usernameOrEmail == null || usernameOrEmail.isBlank() ||
            password == null || password.isBlank()) {
            return null;
        }

        User user = userDAO.checkLogin(usernameOrEmail, password);
        if (user == null) {
            return null;
        }
        if (!user.getStatus()) {  // nếu bị khóa
            return null;
        }
        return user;
    }

    public User getUserById(String userID) {
        if (userID == null || userID.isBlank()) {
            return null;
        }
        return userDAO.getUserById(userID);
    }

    public boolean createUser(User user) {
        if (user == null) return false;

        if (user.getUserName() == null || user.getUserName().isBlank()) return false;
        if (user.getEmail() == null || user.getEmail().isBlank()) return false;

        // Có thể bỏ kiểm tra password nếu là tài khoản Google
        if (user.getLoginProvider() == null || !user.getLoginProvider().equalsIgnoreCase("Google")) {
            if (user.getPassword() == null || user.getPassword().isBlank()) return false;
        }

        user.setStatus(true);
        return userDAO.createUser(user);
    }

    public boolean softDeleteUser(String userID, String lastModifiedID) {
        if (userID == null || userID.isBlank() || lastModifiedID == null || lastModifiedID.isBlank()) {
            return false;
        }
        return userDAO.softDeleteUser(userID, lastModifiedID);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    // ==================== 🔥 GOOGLE LOGIN SUPPORT 🔥 ====================

    
     //Lấy user theo email (phục vụ đăng nhập Google)

    public User getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        return userDAO.getUserByEmail(email);
    }

    // Cập nhật thông tin user Google (avatar, provider)

    public boolean updateGoogleUser(User user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }
        return userDAO.updateGoogleUser(user);
    }
    public boolean updatePassword(String email, String newPassword) {
        return userDAO.updatePassword(email, newPassword);
    }

    public boolean updateProfile(User sessionUser) {
        return userDAO.updateProfile(sessionUser);
    }
}

