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
        <a class="quick-link" href="#" id="headerBackToInstructorBtn">Back to Instructor</a>
        <a class="quick-link" href="#" id="headerBackToLearnerBtn">Back to Learner</a>
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
<script>
(function(){
    function initHeaderButtons() {
        // Handle Back to Instructor button in header - giống như sidebar instructor
        var headerBackToInstructor = document.getElementById('headerBackToInstructorBtn');
        if (headerBackToInstructor) {
            // Remove any existing event listeners by replacing the element
            var newBtn = headerBackToInstructor.cloneNode(true);
            headerBackToInstructor.parentNode.replaceChild(newBtn, headerBackToInstructor);
            
            newBtn.addEventListener('click', function(e){
                e.preventDefault();
                e.stopPropagation();
                e.stopImmediatePropagation(); // Prevent other listeners
                // Admin chuyển đến instructor page - giống như từ sidebar instructor
                var contextPath = '<%= request.getContextPath() %>';
                if (!contextPath || contextPath === '') {
                    var path = location.pathname;
                    var idx = path.indexOf('/admin/');
                    contextPath = idx >= 0 ? path.substring(0, idx) : '';
                }
                window.location.href = contextPath + '/instructor/dashboard';
                return false;
            }, true); // Use capture phase to run before other listeners
        }
        
        // Handle Back to Learner button in header - giống như sidebar learner
        var headerBackToLearner = document.getElementById('headerBackToLearnerBtn');
        if (headerBackToLearner) {
            // Remove any existing event listeners by replacing the element
            var newBtn2 = headerBackToLearner.cloneNode(true);
            headerBackToLearner.parentNode.replaceChild(newBtn2, headerBackToLearner);
            
            newBtn2.addEventListener('click', function(e){
                e.preventDefault();
                e.stopPropagation();
                e.stopImmediatePropagation(); // Prevent other listeners
                // Admin chuyển đến learner page - giống như từ sidebar learner (CourseServletController)
                var contextPath = '<%= request.getContextPath() %>';
                if (!contextPath || contextPath === '') {
                    var path = location.pathname;
                    var idx = path.indexOf('/admin/');
                    contextPath = idx >= 0 ? path.substring(0, idx) : '';
                }
                window.location.href = contextPath + '/CourseServletController';
return false;
            }, true); // Use capture phase to run before other listeners
        }
    }
    
    // Run after admin.js has loaded - use setTimeout to ensure it runs after other scripts
    function runAfterAdminJS() {
        if (window.EDUAdmin && typeof window.EDUAdmin.init === 'function') {
            // Wait a bit more to ensure admin.js has finished
            setTimeout(initHeaderButtons, 100);
        } else {
            // If admin.js not loaded yet, wait and retry
            setTimeout(runAfterAdminJS, 50);
        }
    }
    
    // Run when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', function() {
            setTimeout(runAfterAdminJS, 100);
        });
    } else {
        // DOM already loaded, run after admin.js
        setTimeout(runAfterAdminJS, 100);
    }
})();
</script>
<div class="admin-container">
