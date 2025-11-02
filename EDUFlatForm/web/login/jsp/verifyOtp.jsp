<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Xác minh OTP</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/login.css">
</head>
<body>
  <div class="modal-content">
    <!-- Nút đóng -->
    <button class="modal-close" 
            onclick="window.location.href='${pageContext.request.contextPath}/login/jsp/login.jsp'" 
            aria-label="Đóng">✕</button>
    
    <!-- Header -->
    <div class="modal-header">
      <img src="${pageContext.request.contextPath}/assets/images/authen/logo.jpg" 
           alt="Logo" width="64" height="64">
      <h2>Xác minh mã OTP</h2>
      <p>Nhập mã OTP đã được gửi đến email của bạn để tiếp tục đặt lại mật khẩu.</p>
    </div>

    <!-- Hiển thị thông báo -->
    <c:if test="${not empty message}">
      <p style="color:green; text-align:center;">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
      <p style="color:red; text-align:center;">${error}</p>
    </c:if>

    <!-- Form xác minh -->
    <form action="${pageContext.request.contextPath}/forgot" method="post" class="auth-form">
      <input type="hidden" name="action" value="verifyOtp">

      <div class="form-group">
        <input type="text" name="otp" placeholder="Nhập mã OTP (6 chữ số)" maxlength="6" required class="form-input">
      </div>

      <button type="submit" class="form-submit">Xác minh</button>
    </form>

    <!-- Footer -->
    <div class="auth-footer">
      <p>Không nhận được mã? 
        <a href="${pageContext.request.contextPath}/login/jsp/forgotPass.jsp">Gửi lại OTP</a>
      </p>
    </div>
  </div>
</body>
</html>
