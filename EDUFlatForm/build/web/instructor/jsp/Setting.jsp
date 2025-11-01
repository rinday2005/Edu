<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ & Cài đặt - EduPlatform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="main-container">
         <!-- ===== HEADER ===== -->
    <jsp:include page="/instructor/common/header.jsp" />

    <!-- ===== SIDEBAR ===== -->
   <jsp:include page="/instructor/common/sidebar.jsp" />
        <main class="content-area">
            <section class="settings-section">
                <div class="settings-container">
                    <!-- Profile Information -->
                    <div class="settings-card">
                        <div class="card-header">
                            <h2><i class="fas fa-user"></i> Thông tin cá nhân</h2>
                            <p>Cập nhật thông tin cá nhân của bạn</p>
                        </div>
                        
                        <div class="profile-info">
                            <div class="profile-avatar-section">
                                <div class="profile-avatar">
                                    <img src="${pageContext.request.contextPath}/assets/images/avatar.jpg" alt="Profile Avatar" id="avatar-preview">
                                    <div class="avatar-overlay">
                                        <i class="fas fa-camera"></i>
                                        <span>Thay đổi ảnh</span>
                                    </div>
                                </div>
                                <input type="file" id="avatar-upload" accept="image/*" style="display: none;">
                                <button class="change-avatar-btn" onclick="document.getElementById('avatar-upload').click()">
                                    <i class="fas fa-upload"></i> Đổi ảnh đại diện
                                </button>
                            </div>
                            
                            <div class="profile-form">
                                <form id="profile-form">
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="fullName">Họ và tên *</label>
                                            <input type="text" id="fullName" name="fullName" value="Nguyễn Văn A" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email *</label>
                                            <input type="email" id="email" name="email" value="nguyenvana@example.com" required>
                                        </div>
                                    </div>
                                    
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="phone">Số điện thoại</label>
                                            <input type="tel" id="phone" name="phone" value="0123 456 789">
                                        </div>
                                        <div class="form-group">
                                            <label for="birthday">Ngày sinh</label>
                                            <input type="date" id="birthday" name="birthday" value="1995-06-15">
                                        </div>
                                    </div>
                                    
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="gender">Giới tính</label>
                                            <select id="gender" name="gender">
                                                <option value="male" selected>Nam</option>
                                                <option value="female">Nữ</option>
                                                <option value="other">Khác</option>
                                            </select>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="bio">Giới thiệu bản thân</label>
                                        <textarea id="bio" name="bio" rows="4" placeholder="Viết vài dòng giới thiệu về bản thân...">Tôi là giáo viên đam mê học hỏi và phát triển kỹ năng mới. Tôi thích khám phá các công nghệ mới và chia sẻ kiến thức với cộng đồng.</textarea>
                                    </div>
                                    
                                    <div class="form-actions">
                                        <button type="button" class="cancel-btn" onclick="resetForm()">
                                            <i class="fas fa-times"></i> Hủy bỏ
                                        </button>
                                        <button type="submit" class="save-btn">
                                            <i class="fas fa-save"></i> Lưu thay đổi
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Change Password -->
                    <div class="settings-card">
                        <div class="card-header">
                            <h2><i class="fas fa-lock"></i> Đổi mật khẩu</h2>
                            <p>Cập nhật mật khẩu để bảo mật tài khoản</p>
                        </div>
                        
                        <form id="password-form" class="password-form">
                            <div class="form-group">
                                <label for="currentPassword">Mật khẩu hiện tại *</label>
                                <div class="password-input">
                                    <input type="password" id="currentPassword" name="currentPassword" required>
                                    <button type="button" class="toggle-password" onclick="togglePassword('currentPassword')">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="newPassword">Mật khẩu mới *</label>
                                <div class="password-input">
                                    <input type="password" id="newPassword" name="newPassword" required>
                                    <button type="button" class="toggle-password" onclick="togglePassword('newPassword')">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <div class="password-strength">
                                    <div class="strength-bar">
                                        <div class="strength-fill"></div>
                                    </div>
                                    <span class="strength-text">Mật khẩu yếu</span>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="confirmPassword">Xác nhận mật khẩu mới *</label>
                                <div class="password-input">
                                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                                    <button type="button" class="toggle-password" onclick="togglePassword('confirmPassword')">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                            </div>
                            
                            <div class="form-actions">
                                <button type="button" class="cancel-btn" onclick="resetPasswordForm()">
                                    <i class="fas fa-times"></i> Hủy bỏ
                                </button>
                                <button type="submit" class="save-btn">
                                    <i class="fas fa-key"></i> Đổi mật khẩu
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- Account Settings -->
                    <div class="settings-card">
                        <div class="card-header">
                            <h2><i class="fas fa-cog"></i> Cài đặt tài khoản</h2>
                            <p>Quản lý các cài đặt tài khoản và quyền riêng tư</p>
                        </div>
                        
                        <div class="account-settings">
                            <div class="account-item">
                                <div class="account-info">
                                    <h4>Hiển thị hồ sơ công khai</h4>
                                    <p>Cho phép người khác xem hồ sơ và thành tích của bạn</p>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" checked>
                                    <span class="slider"></span>
                                </label>
                            </div>
                            
                            <div class="account-item">
                                <div class="account-info">
                                    <h4>Chế độ tối</h4>
                                    <p>Sử dụng giao diện tối để giảm mỏi mắt</p>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox">
                                    <span class="slider"></span>
                                </label>
                            </div>
                            
                            <div class="account-item">
                                <div class="account-info">
                                    <h4>Tự động lưu tiến độ</h4>
                                    <p>Tự động lưu tiến độ học tập và bài làm</p>
                                </div>
                                <label class="toggle-switch">
                                    <input type="checkbox" checked>
                                    <span class="slider"></span>
                                </label>
                            </div>
                            
                            <div class="account-item danger">
                                <div class="account-info">
                                    <h4>Xóa tài khoản</h4>
                                    <p>Xóa vĩnh viễn tài khoản và tất cả dữ liệu</p>
                                </div>
                                <button class="danger-btn" onclick="deleteAccount()">
                                    <i class="fas fa-trash"></i> Xóa tài khoản
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/instructorsHome.js"></script>
    <script>
        // Avatar upload functionality
        document.getElementById('avatar-upload').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('avatar-preview').src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        });

        // Password strength checker
        document.getElementById('newPassword').addEventListener('input', function(e) {
            const password = e.target.value;
            const strengthBar = document.querySelector('.strength-fill');
            const strengthText = document.querySelector('.strength-text');
            
            let strength = 0;
            if (password.length >= 8) strength++;
            if (/[A-Z]/.test(password)) strength++;
            if (/[a-z]/.test(password)) strength++;
            if (/[0-9]/.test(password)) strength++;
            if (/[^A-Za-z0-9]/.test(password)) strength++;
            
            const strengthLevels = ['Mật khẩu yếu', 'Mật khẩu trung bình', 'Mật khẩu tốt', 'Mật khẩu rất tốt', 'Mật khẩu mạnh'];
            const colors = ['#ef4444', '#f59e0b', '#eab308', '#22c55e', '#10b981'];
            
            strengthBar.style.width = `${(strength / 5) * 100}%`;
            strengthBar.style.backgroundColor = colors[strength - 1] || '#ef4444';
            strengthText.textContent = strengthLevels[strength - 1] || 'Mật khẩu yếu';
        });

        // Toggle password visibility
        function togglePassword(inputId) {
            const input = document.getElementById(inputId);
            const button = input.nextElementSibling;
            const icon = button.querySelector('i');
            
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        }

        // Form submission handlers
        document.getElementById('profile-form').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Thông tin cá nhân đã được cập nhật thành công!');
        });

        document.getElementById('password-form').addEventListener('submit', function(e) {
            e.preventDefault();
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (newPassword !== confirmPassword) {
                alert('Mật khẩu xác nhận không khớp!');
                return;
            }
            
            alert('Mật khẩu đã được thay đổi thành công!');
            resetPasswordForm();
        });

        // Reset forms
        function resetForm() {
            document.getElementById('profile-form').reset();
        }

        function resetPasswordForm() {
            document.getElementById('password-form').reset();
            document.querySelector('.strength-fill').style.width = '0%';
            document.querySelector('.strength-text').textContent = 'Mật khẩu yếu';
        }

        // Delete account
        function deleteAccount() {
            if (confirm('Bạn có chắc chắn muốn xóa tài khoản? Hành động này không thể hoàn tác!')) {
                alert('Tài khoản sẽ được xóa trong 7 ngày. Bạn có thể hủy bỏ trong thời gian này.');
            }
        }
    </script>
</body>
</html>