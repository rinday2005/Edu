<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- Kiểm tra đăng nhập - nếu chưa đăng nhập thì chuyển về trang chủ -->
<c:if test="${empty sessionScope.user}">
    <c:redirect url="/eduHome/eduHome.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ & Cài đặt - E-Learning System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/setting.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <!-- Header -->
    <jsp:include page="/learner/common/header.jsp" />
    
    <!-- Sidebar -->
    <jsp:include page="/learner/common/header.jsp" />
    /common/sidebar.jsp" />
    <!-- Main Content -->
    <main class="main-content">
        <section class="settings-section">
            <div class="container">
                <div class="settings-container">
                    <!-- Thông tin cá nhân -->
                    <div class="settings-card">
                        <div class="card-header">
                            <h2><i class="fas fa-user-circle"></i> Thông tin cá nhân</h2>
                            <p>Cập nhật thông tin cá nhân của bạn</p>
                        </div>
                            <c:if test="${not empty message}">
                             <p style="color:green;text-align:center;">${message}</p>
                            </c:if>
                            <c:if test="${not empty error}">
                             <p style="color:red;text-align:center;">${error}</p>
                            </c:if>
                             <form id="profile-form" method="post" action="${pageContext.request.contextPath}/learner?action=editinfo" enctype="multipart/form-data">
                        <div class="profile-info">
                            <div class="profile-avatar-section">
                                <div class="profile-avatar">
                                    <c:set var="avatarUrl" value="${sessionScope.user.avatarUrl}" />
                                    <img src="${not empty avatarUrl ? avatarUrl : defaultAvatar}" alt="Profile Avatar" id="avatar-preview">
                                    <div class="avatar-overlay">
                                        <i class="fas fa-camera"></i>
                                    </div>
                                </div>
                                <input type="file" id="avatar-upload" name="avatar" accept="image/*" style="display: none;">
                                <button type="button" class="change-avatar-btn"
                                        onclick="document.getElementById('avatar-upload').click()">
                                    <i class="fas fa-upload"></i> Đổi ảnh đại diện
                                </button>
                            </div>
                            <div class="profile-form">
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="fullName">Họ và tên *</label>
                                            <input type="text" id="fullName" name="fullName" value="${sessionScope.user.fullName}" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email *</label>
                                            <input type="email" id="email" name="email" value="${sessionScope.user.email != null ? sessionScope.user.email : ''}" required>
                                        </div>
                                    </div>                               
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="phone">Số điện thoại</label>
                                            <input type="tel" id="phone" name="phone" value="${sessionScope.user.phoneNumber != null ? sessionScope.user.phoneNumber : ''}">
                                        </div>
                                        <div class="form-group">
                                            <label for="birthday">Ngày sinh</label>
                                            <c:choose>
                                                <c:when test="${not empty sessionScope.user.dateofbirth}">
                                                    <fmt:formatDate value="${sessionScope.user.dateofbirth}" pattern="yyyy-MM-dd" var="formattedDob" />
                                                    <input type="date" id="birthday" name="birthday" value="${formattedDob}">
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="date" id="birthday" name="birthday" value="">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="bio">Giới thiệu bản thân</label>
                                        <textarea id="bio" name="bio" rows="4" placeholder="Viết vài dòng giới thiệu về bản thân...">${sessionScope.user.bio != null ? sessionScope.user.bio : ''}</textarea>
                                    </div>                                   
                                    <div class="form-actions">
                                        <button type="button" class="cancel-btn" onclick="resetForm()">
                                            <i class="fas fa-times"></i> Hủy bỏ
                                        </button>
                                        <button type="submit" class="save-btn">
                                            <i class="fas fa-save"></i> Lưu thay đổi
                                        </button>
                                    </div>   
                                </div>                                     
                            </div>
                        </form>
                    </div>

                    <!-- Đổi mật khẩu -->
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

                </div>
            </div>
        </section>
    </main>

 <script src="${pageContext.request.contextPath}/learner/js/setting.js"></script>

</body>
</html>
