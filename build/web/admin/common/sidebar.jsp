<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<aside class="admin-sidebar">
    <ul class="nav">
        <li><a href="<%=request.getContextPath()%>/admin/dashboard">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 12h7V3H3zM14 21h7v-9h-7zM14 3h7v5h-7zM3 21h7v-5H3z"/></svg>
            Tổng quan</a></li>
        <li class="nav-section">Người dùng</li>
        <li><a href="<%=request.getContextPath()%>/admin/users">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-3-3.87M4 21v-2a4 4 0 0 1 3-3.87"/><circle cx="12" cy="7" r="4"/></svg>
            Tài khoản</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/jsp/bans.jsp">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="9"/><path d="M4.9 4.9l14.2 14.2"/></svg>
            Ban / Gỡ ban</a></li>
        <li class="nav-section">Khóa học</li>
        <li><a href="<%=request.getContextPath()%>/admin/courses">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M21 10l-9 4-9-4"/></svg>
            Quản lý khóa học</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/approvals">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M9 17l2 2 4-4"/></svg>
            Duyệt khóa học</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/sections">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>
            Lesson</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/assignments">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><rect x="4" y="4" width="16" height="16" rx="2"/><path d="M9 12h6M9 16h6"/></svg>
            Assignment</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/jsp/mcq-questions.jsp">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M8 12h8M12 8v8"/></svg>
            MCQ</a></li>
        <li class="nav-section">Nội dung & Tương tác</li>
        <li><a href="<%=request.getContextPath()%>/admin/articles">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16v16H4z"/><path d="M8 8h8M8 12h8M8 16h5"/></svg>
            Bài viết</a></li>
<li><a href="<%=request.getContextPath()%>/admin/comments">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15a4 4 0 0 1-4 4H7l-4 4V7a4 4 0 0 1 4-4h10a4 4 0 0 1 4 4z"/></svg>
            Bình luận</a></li>
        <li class="nav-section">Giao dịch</li>
        <li><a href="<%=request.getContextPath()%>/admin/wallets">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><rect x="2" y="7" width="20" height="14" rx="2"/><circle cx="16" cy="14" r="2"/></svg>
            Ví người dùng</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/jsp/carts.jsp">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M6 6h15l-1.5 9H7.5L6 6z"/><circle cx="9" cy="20" r="1"/><circle cx="18" cy="20" r="1"/></svg>
            Giỏ hàng</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/jsp/submissions.jsp">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2v20M5 9l7-7 7 7"/></svg>
            Bài nộp</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/jsp/user-courses.jsp">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="8.5" cy="7" r="4"/><path d="M20 8v6M23 11h-6"/></svg>
            Đăng ký khóa học</a></li>
        <li class="nav-section">Khác</li>
        <li><a href="<%=request.getContextPath()%>/admin/transactions">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 3v18h18"/><path d="M7 13l3 3 7-7"/></svg>
            Giao dịch</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/jsp/reports.jsp">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 3h18v18H3z"/><path d="M7 17V7M12 17V4M17 17v-6"/></svg>
            Báo cáo</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/settings">
<svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 1 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 1 1-4 0v-.09a1.65 1.65 0 0 0-1-1.51 1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 1 1-2.83-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 1 1 0-4h.09a1.65 1.65 0 0 0 1.51-1 1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 1 1 2.83-2.83l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 1 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 1 1 2.83 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9c0 .66.26 1.3.73 1.77.47.47 1.11.73 1.77.73H21a2 2 0 1 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/></svg>
            Cài đặt</a></li>
        <li><a href="<%=request.getContextPath()%>/admin/logs">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 4h18M3 8h18M7 12h14M7 16h14M7 20h14"/></svg>
            Log hoạt động</a></li>
    </ul>
    
    <!-- Back to Instructor and Learner - Admin toàn quyền đóng 2 vai trò -->
    <div style="margin-top:auto; padding-top:12px; border-top:1px solid rgba(255,255,255,0.1);">
        <a href="#" id="backToInstructorBtn" class="nav-item" style="display:block; padding:8px 16px; color:rgba(255,255,255,0.8); text-decoration:none; border-radius:4px; margin-bottom:4px;">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" style="display:inline-block; vertical-align:middle; margin-right:8px;"><path d="M20 24L12 16L20 8"/></svg>
            Back to Instructor
        </a>
        <a href="#" id="backToLearnerBtn" class="nav-item" style="display:block; padding:8px 16px; color:rgba(255,255,255,0.8); text-decoration:none; border-radius:4px;">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" style="display:inline-block; vertical-align:middle; margin-right:8px;"><path d="M20 24L12 16L20 8"/></svg>
            Back to Learner
        </a>
    </div>
</aside>

<script>
(function(){
    var backToInstructor = document.getElementById('backToInstructorBtn');
    var backToLearner = document.getElementById('backToLearnerBtn');
    
    if (backToInstructor) {
        backToInstructor.addEventListener('click', function(e){
            e.preventDefault();
            // Admin chuyển đến instructor page riêng của admin
            var contextPath = '<%= request.getContextPath() %>';
            window.location.href = contextPath + '/instructor/dashboard';
        });
    }
    
    if (backToLearner) {
        backToLearner.addEventListener('click', function(e){
            e.preventDefault();
            // Admin chuyển đến learner page riêng của admin
            var contextPath = '<%= request.getContextPath() %>';
window.location.href = contextPath + '/CourseServletController';
        });
    }
})();
</script>
