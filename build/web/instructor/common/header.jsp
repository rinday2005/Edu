<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <button class="btn-login"
                        onclick="window.location.href='${pageContext.request.contextPath}/login/jsp/login.jsp'">
                    Đăng nhập
                </button>
            </c:when>
            <c:otherwise>
                <div class="user-profile-wrapper">
                    <div class="user-menu">
                        <img src="${sessionScope.user.avatarUrl != null ? sessionScope.user.avatarUrl : (pageContext.request.contextPath + '/learner/images/default-avatar.png')}"
                             alt="User Avatar"
                             class="user-avatar"
                             onclick="toggleUserMenu()" />

                        <div id="dropdownMenu" class="dropdown-menu">
                            <div class="dropdown-header">
                                <img src="${sessionScope.user.avatarUrl != null ? sessionScope.user.avatarUrl : (pageContext.request.contextPath + '/learner/images/default-avatar.png')}"
                                     class="menu-avatar" alt="User"/>
                                <div class="menu-info">
                                    <h4 class="user-name">${sessionScope.user.userName}</h4>
                                    <p class="user-email">${sessionScope.user.email}</p>
                                    <span class="user-role">Giảng viên</span>
                                </div>
                            </div>

                            <div class="dropdown-divider"></div>

                         
                            <a href="${pageContext.request.contextPath}/instructor/jsp/Setting.jsp" class="dropdown-item">
                                <i class="fas fa-cog"></i> Cài đặt
                            </a>
                            <a href="${pageContext.request.contextPath}/instructorHome/Help.jsp" class="dropdown-item">
                                <i class="fas fa-question-circle"></i> Trợ giúp
                            </a>

                            <div class="dropdown-divider"></div>

                            <a href="${pageContext.request.contextPath}/logout" class="dropdown-item logout">
                                <i class="fas fa-sign-out-alt"></i> Đăng xuất
                            </a>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<script>
function toggleUserMenu() {
    const menu = document.getElementById("dropdownMenu");
    if (menu) {
        menu.classList.toggle("show");
    }
}

// Ẩn menu khi click ra ngoài
window.addEventListener("click", function (event) {
    const menu = document.getElementById("dropdownMenu");
    const avatar = document.querySelector(".user-avatar");
    if (menu && avatar && !menu.contains(event.target) && !avatar.contains(event.target)) {
        menu.classList.remove("show");
    }
});
</script>
