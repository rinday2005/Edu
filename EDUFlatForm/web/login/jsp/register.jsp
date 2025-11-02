<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đăng ký</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/register.css">
</head>
<body>
  <div class="modal-content">
    <button class="modal-close" onclick="window.location.href='${pageContext.request.contextPath}/MainServlet'" aria-label="Đóng">✕</button>

    <div class="modal-header">
      <img src="${pageContext.request.contextPath}/login/images/logo.jpg" alt="Logo" width="64" height="64">
      <h2>Tạo tài khoản E-Learning</h2>
      <p>Vui lòng nhập đầy đủ thông tin để đăng ký.</p>
    </div>

    <div class="modal-body"> 
      <!-- 🔹 Form đăng ký -->
      <form action="${pageContext.request.contextPath}/register" method="post" class="auth-form">
        <div class="form-group">
          <input type="text" name="username" placeholder="Tên tài khoản" required class="form-input">
        </div>

        <div class="form-group">
          <input type="email" name="email" placeholder="Địa chỉ email" required class="form-input">
        </div>

        <div class="form-group">
          <input type="text" name="phone" placeholder="Số điện thoại" pattern="[0-9]{10,11}" required class="form-input">
        </div>

        <div class="form-group">
          <div class="password-input-wrapper">
            <input type="password" name="password" placeholder="Mật khẩu" minlength="6" required class="form-input">
            <button type="button" class="password-toggle" onclick="togglePasswordVisibility()">👁</button>
          </div>
        </div>

        <div class="form-group">
          <div class="password-input-wrapper">
            <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu" minlength="6" required class="form-input">
            <button type="button" class="password-toggle" onclick="toggleConfirmPasswordVisibility()">👁</button>
          </div>
        </div>

        <button type="submit" class="form-submit">Đăng ký</button>

        <c:if test="${not empty errorMessage}">
          <p class="error-message" style="color:red;text-align:center;">${errorMessage}</p>
        </c:if>
      </form>

      <div class="auth-footer">
        <p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/login/jsp/login.jsp">Đăng nhập</a></p>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/login/js/register.js"></script>
</body>
</html>
