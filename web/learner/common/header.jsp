<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header">
  <div class="header-container">
    <!-- LOGO -->
    <div class="logo" onclick="window.location.href='${pageContext.request.contextPath}/CourseServletController'">
      <img src="${pageContext.request.contextPath}/learner/images/logo.jpg"
           alt="Logo" class="logo-img" decoding="async" />
      <span class="logo-text">E-Learning System</span>
    </div>

    <!-- SEARCH BAR -->
    <div class="search-bar">
      <input type="text" placeholder="Tìm kiếm khóa học, bài viết, video, ..." />
      <button class="search-btn" aria-label="Search">🔍</button>
    </div>
    
    <!-- ACTIONS -->
    <div class="header-actions">
      <button class="theme-toggle" id="themeToggle" onclick="toggleTheme()">🌗</button>

      <!-- 🔹 NẾU CHƯA ĐĂNG NHẬP -->
      <c:if test="${empty sessionScope.user}">
        <button class="btn-login"
                onclick="window.location.href='${pageContext.request.contextPath}/login'">
          Đăng nhập
        </button>
        <button class="btn-signup"
                onclick="window.location.href='${pageContext.request.contextPath}/register'">
          Đăng ký
        </button>
      </c:if>

      <!-- 🔹 NẾU ĐÃ ĐĂNG NHẬP -->
      <c:if test="${not empty sessionScope.user}">
        <%
          // Safe access to user object and avatarUrl
          model.User user = (model.User) session.getAttribute("user");
          String avatarUrl = null;
          String userName = "";
          String userEmail = "";
          
          if (user != null) {
            try {
              avatarUrl = user.getAvatarUrl();
              userName = user.getUserName() != null ? user.getUserName() : "";
              userEmail = user.getEmail() != null ? user.getEmail() : "";
            } catch (Exception e) {
              // Ignore errors
            }
          }
          
          // Default avatar if null or empty
          if (avatarUrl == null || avatarUrl.trim().isEmpty()) {
            avatarUrl = request.getContextPath() + "/learner/images/default-avatar.png";
          }
        %>
        <div class="user-menu">
          <img src="<%= avatarUrl %>"
               alt="User Avatar"
               class="user-avatar"
               onclick="toggleUserMenu()" />

          <!-- Dropdown -->
          <div id="dropdownMenu" class="dropdown-menu">
            <div class="menu-header">
              <img src="<%= avatarUrl %>"
                   class="menu-avatar" alt="User"/>
              <div class="menu-info">
                <span class="user-name"><%= userName %></span>
                <span class="user-email"><%= userEmail %></span>
              </div>
            </div>
<div class="menu-divider"></div>

            <a href="${pageContext.request.contextPath}/CartServlett">🛒 Giỏ hàng</a>
            <a href="${pageContext.request.contextPath}/learner/mycourses">📚 Khóa học của tôi</a>
            <a href="${pageContext.request.contextPath}/learner/jsp/Cart/Cart.jsp">📝 Bài viết của tôi</a>
            <a href="${pageContext.request.contextPath}/learner/jsp/Setting/setting.jsp">⚙️ Cài đặt</a>
            

            <div class="menu-divider"></div>

            <a href="${pageContext.request.contextPath}/logout" class="logout">🚪 Đăng xuất</a>
          </div>
          
        </div>
      </c:if>
</div>
  </div>
</header>

<!-- ========== SCRIPT ========== -->
<script>
function toggleUserMenu() {
  const menu = document.getElementById("dropdownMenu");
  menu.classList.toggle("show");
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

<!-- ========== CHAT AI COMPONENT ========== -->
<jsp:include page="/learner/components/chatAI.jsp" />