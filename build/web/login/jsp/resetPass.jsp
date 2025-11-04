<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đặt lại mật khẩu</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/login.css">
</head>
<body>
  <div class="modal-content">
    <h2>Đặt lại mật khẩu</h2>
    <form action="${pageContext.request.contextPath}/forgot?action=resetPassword" method="post" class="auth-form">
      <input type="hidden" name="token" value="${param.token}">
      <input type="password" name="newPassword" placeholder="Mật khẩu mới" required class="form-input">
      <button type="submit" class="form-submit">Cập nhật</button>
    </form>
  </div>
</body>
</html>
