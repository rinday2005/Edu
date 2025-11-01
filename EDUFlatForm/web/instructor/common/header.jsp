<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- HEADER COMPONENT -->
<header class="navbar">
    <!-- Phần 1: Logo (trái) -->
    <div class="logo">
        <img src="${pageContext.request.contextPath}/instructor/images/logo.jpg"
             alt="Logo"
             class="logo-img"
             width="32" height="32" decoding="async" />
        <span class="logo-text">E-Learning System</span>
    </div>

    <!-- Phần 2: Thanh tìm kiếm (giữa) -->
    <div class="navbar-center">
        <div class="search-bar">
            <input type="text" placeholder="Tìm kiếm khóa học, bài viết, video...">
            <button><i class="fas fa-search"></i></button>
        </div>
    </div>

    <!-- Phần 3: Hồ sơ người dùng (phải) -->
    <div class="navbar-right">
        <div class="user-profile-wrapper">
            <div class="user-profile" id="userProfileToggle">
                <span class="username">Nguyễn Văn A</span>
                <i class="fas fa-user-circle user-avatar"></i>
            </div>

            <!-- Menu Dropdown -->
            <div class="dropdown-menu" id="profileDropdown">
                <div class="dropdown-header">
                    <div class="avatar-container large">
                        <i class="fas fa-user avatar-icon"></i>
                    </div>
                    <div>
                        <h4>Nguyễn Văn A</h4>
                        <p>nguyenvana@example.com</p>
                        <span class="user-role">Giảng viên</span>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/instructorHome/Profile.jsp" class="dropdown-item">
                    <i class="fas fa-user"></i> Hồ sơ cá nhân
                </a>
                <a href="${pageContext.request.contextPath}/instructorHome/Setting.jsp" class="dropdown-item">
                    <i class="fas fa-cog"></i> Cài đặt
                </a>
                <a href="${pageContext.request.contextPath}/instructorHome/Help.jsp" class="dropdown-item">
                    <i class="fas fa-question-circle"></i> Trợ giúp
                </a>
                <div class="dropdown-divider"></div>
                <a href="${pageContext.request.contextPath}/LogoutServlet" class="dropdown-item logout">
                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                </a>
            </div>
        </div>
    </div>
</header>
