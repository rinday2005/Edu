<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Guard: only Admin can access
    Object roleAttr = session.getAttribute("role");
    boolean isAdmin = false;
    if (roleAttr != null) {
        isAdmin = "Admin".equals(String.valueOf(roleAttr));
    }
    if (!isAdmin) {
        Object u = session.getAttribute("user");
        try {
            // Try to reflectively call getRole if available without import
            if (u != null) {
                java.lang.reflect.Method m = u.getClass().getMethod("getRole");
                Object r = m.invoke(u);
                isAdmin = r != null && "Admin".equals(String.valueOf(r));
            }
        } catch (Exception ignore) { }
    }
    if (!isAdmin) {
        response.sendRedirect(request.getContextPath() + "/login/jsp/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - EDUFlatForm</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/admin/css/admin.css">
</head>
<body>
<header class="admin-header">
    <div class="brand">
        <img src="<%=request.getContextPath()%>/uploads/logo.png" alt="logo" onerror="this.style.display='none'" />
        <a href="<%=request.getContextPath()%>/admin/dashboard" style="text-decoration:none; color:inherit;"><span>EDUFlatForm Admin</span></a>
    </div>
    <div class="admin-search">
        <form method="get" action="<%=request.getContextPath()%>/CourseServletController">
            <input type="text" name="q" placeholder="Tìm kiếm khóa học, người dùng..." />
            <button type="submit">Tìm</button>
        </form>
    </div>
    <div class="admin-actions">
        <a class="quick-link" href="<%=request.getContextPath()%>/instructor/jsp/InstructorDashboard.jsp">Back to Instructor</a>
        <a class="quick-link" href="<%=request.getContextPath()%>/CourseServletController">Back to Learner</a>
        <div class="menu" id="avatarMenu">
            <%
                String avatarSrc = null;
                Object userObj = session.getAttribute("user");
                if (userObj != null) {
                    try {
                        java.lang.reflect.Method m = userObj.getClass().getMethod("getAvatarUrl");
                        Object r = m.invoke(userObj);
                        if (r != null) avatarSrc = String.valueOf(r);
                    } catch (Exception ignore) {}
                }
                if (avatarSrc == null || avatarSrc.isEmpty()) {
                    Object av = session.getAttribute("avatarUrl");
                    if (av != null) avatarSrc = String.valueOf(av);
                }
                if (avatarSrc == null || avatarSrc.isEmpty()) {
                    avatarSrc = request.getContextPath()+"/uploads/avatar/default.png";
                }
            %>
            <img class="avatar" loading="lazy" decoding="async" src="<%= avatarSrc %>?v=<%= System.currentTimeMillis() %>" alt="avatar" onerror="this.src='<%=request.getContextPath()%>/uploads/avatar/default.png'" />
            <div class="dropdown">
                <a href="<%=request.getContextPath()%>/admin/jsp/settings.jsp">Cài đặt</a>
                <a href="<%=request.getContextPath()%>/logout">Đăng xuất</a>
            </div>
        </div>
    </div>
</header>
<div class="admin-container">

