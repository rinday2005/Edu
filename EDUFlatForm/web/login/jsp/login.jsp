<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đăng nhập</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/login.css">
  <!-- Google Identity Services for Sign-In -->
  <script src="https://accounts.google.com/gsi/client" async defer></script>
</head>
<body>
  <div class="modal-content">
    <button class="modal-close" onclick="window.location.href='${pageContext.request.contextPath}/course'" aria-label="Đóng">✕</button>

    <div class="modal-header">
      <img src="${pageContext.request.contextPath}/images/logo.jpg" alt="Logo" width="64" height="64">
      <h2>Đăng nhập vào E-Learning System</h2>
    </div>

    <div class="modal-body">
      <!-- 🔹 Đăng nhập bằng Google -->
        <!-- Nút đăng nhập Google -->
    <div id="g_id_onload"
         data-client_id="241470665821-3vbsh1pbsp17rd1vb6dctv2qlh5c721g.apps.googleusercontent.com"
         data-login_uri="http://localhost:9999/PRJ_Assginment_EDUCATION/google-login"
         data-auto_prompt="false">
    </div>

    <div class="g_id_signin"
         data-type="standard"
         data-shape="rectangular"
         data-theme="outline"
         data-text="signin_with"
         data-size="large"
         data-logo_alignment="left">
    </div>
      <!-- 🔹 Form đăng nhập -->
      <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
        <div class="form-group">
          <input type="text" name="input" placeholder="Tên Đăng Nhập hoặc Email" class="form-input" required>
        </div>

        <div class="form-group">
          <div class="password-input-wrapper">
            <input type="password" name="password" placeholder="Mật khẩu" class="form-input" required>
            <button type="button" class="password-toggle" onclick="togglePasswordVisibility()">👁</button>
          </div>
        </div>

        <div class="form-group checkbox-group">
          <label><input type="checkbox" name="rememberMe"> Ghi nhớ đăng nhập</label>
        </div>
        
        <button type="submit" class="form-submit">Đăng nhập</button>

        <c:if test="${not empty errorMessage}">
          <p class="error-message" style="color:red;text-align:center;">${errorMessage}</p>
        </c:if>
      </form>

      <div class="auth-footer">
        <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></p>
        <p><a href="${pageContext.request.contextPath}/forgot">Quên mật khẩu?</a></p>
      </div>

      <p class="auth-disclaimer">
        Khi tiếp tục, bạn đồng ý với <a href="#">Điều khoản sử dụng</a> và <a href="#">Chính sách bảo mật</a>.
      </p>
    </div>
  </div>
  <script src="${pageContext.request.contextPath}/js/login.js?v=2.1"></script>
</body>
</html>
