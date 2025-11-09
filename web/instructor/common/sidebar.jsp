<!-- File: /common/sidebar.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="sidebar">
    <a href="${pageContext.request.contextPath}/instructor/dashboard" 
       class="nav-item <%= request.getRequestURI().contains("InstructorDashboard") || request.getRequestURI().contains("/instructor/dashboard") ? "active" : "" %>">
        <i class="fas fa-home"></i> Trang chủ
    </a>

    <a href="${pageContext.request.contextPath}/ManageCourse" 
       class="nav-item <%= request.getRequestURI().contains("CourseManagement") ? "active" : "" %>">
        <i class="fas fa-laptop-code"></i> Khóa học
    </a>

    <a href="${pageContext.request.contextPath}/FinanceReportServlet" 
       class="nav-item <%= request.getRequestURI().contains("FinanceReport.jsp") ? "active" : "" %>">
        <i class="fas fa-chart-line"></i> Ví tiền
    </a>

    <a href="${pageContext.request.contextPath}/ManageArtical" 
       class="nav-item <%= request.getRequestURI().contains("ArticleManage") ? "active" : "" %>">
        <i class="fas fa-blog"></i> Bài viết
    </a>

   

    <a href="${pageContext.request.contextPath}/ManageAssignment" 
       class="nav-item <%= request.getRequestURI().contains("Assignment") ? "active" : "" %>">
        <i class="fas fa-tasks"></i> Bài tập
    </a>

    <!-- Back to Learner - Instructor cũng có vai trò learner -->
    <a href="#" id="backToLearnerBtn" class="nav-item back-nav-item" style="margin-top:auto; border-top:1px solid rgba(255,255,255,0.1); padding-top:12px;">
        <i class="fas fa-arrow-left"></i> Back to Learner
    </a>
    
    <!-- Back to Admin - FE only, only show for Admin accounts -->
    <a href="#" id="backToAdminBtn" class="nav-item back-nav-item" style="display:none; border-top:1px solid rgba(255,255,255,0.1); padding-top:12px;">
        <i class="fas fa-arrow-left"></i> Back to Admin
    </a>
</nav>

<script>
(function(){
    // Check if user is Admin
    var isAdmin = false;
    var userRole = '<%= session.getAttribute("role") != null ? session.getAttribute("role") : (session.getAttribute("user") != null ? ((model.User)session.getAttribute("user")).getRole() : "") %>';
    if (userRole && userRole.toLowerCase().indexOf('admin') !== -1) {
        isAdmin = true;
    }
    
    // Show Back to Admin button if user is Admin
    var backToAdminBtn = document.getElementById('backToAdminBtn');
    if (backToAdminBtn && isAdmin) {
        backToAdminBtn.style.display = 'block';
        backToAdminBtn.addEventListener('click', function(e){
            e.preventDefault();
            var contextPath = '<%= request.getContextPath() %>';
            window.location.href = contextPath + '/admin/dashboard';
        });
    }
    
    var backBtn = document.getElementById('backToLearnerBtn');
    if (backBtn) {
        backBtn.addEventListener('click', function(e){
            e.preventDefault();
// FE-only: Redirect to learner home page (CourseServletController)
            // Instructor đóng 2 vai trò nên có thể xem trang learner
            // Get context path from current location
            var contextPath = '<%= request.getContextPath() %>';
            if (!contextPath || contextPath === '') {
                // Fallback: try to extract from current path
                var path = location.pathname;
                var idx = path.indexOf('/instructor/');
                contextPath = idx >= 0 ? path.substring(0, idx) : '';
            }
            // Redirect to learner home page (CourseServletController)
            window.location.href = contextPath + '/CourseServletController';
        });
    }
})();
</script>
