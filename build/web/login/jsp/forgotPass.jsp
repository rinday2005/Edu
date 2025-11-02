<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Quên mật khẩu</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/login.css">
</head>
<body>
  <div class="modal-content">
    <button class="modal-close" onclick="window.location.href='${pageContext.request.contextPath}/authen/loginAuthen.jsp'" aria-label="Đóng">✕</button>
    
    <div class="modal-header">
      <img src="${pageContext.request.contextPath}/assets/images/authen/logo.jpg" alt="Logo" width="64" height="64">
      <h2>Khôi phục mật khẩu</h2>
      <p>Nhập email bạn đã dùng để đăng ký, hệ thống sẽ gửi liên kết đặt lại mật khẩu.</p>
    </div>
    <c:if test="${not empty message}">
      <p style="color:green;text-align:center;">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
      <p style="color:red;text-align:center;">${error}</p>
    </c:if>
    <form action="${pageContext.request.contextPath}/forgot?action=sendOtp" method="post" class="auth-form">
      <div class="form-group">
        <input type="email" name="email" placeholder="Nhập địa chỉ email" required class="form-input">
      </div>
      <button type="submit" class="form-submit">Gửi liên kết đặt lại</button>
    </form>

   
  </div>
</body>
</html>
