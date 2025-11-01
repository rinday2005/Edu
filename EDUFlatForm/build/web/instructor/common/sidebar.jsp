<!-- File: /common/sidebar.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="sidebar">
    <a href="InstructorDashboard.jsp" 
       class="nav-item <%= request.getRequestURI().contains("InstructorDashboard.jsp") ? "active" : "" %>">
        <i class="fas fa-home"></i> Trang chủ
    </a>

    <a href="CourseManagement.jsp" 
       class="nav-item <%= request.getRequestURI().contains("CourseManagement.jsp") ? "active" : "" %>">
        <i class="fas fa-laptop-code"></i> Khóa học
    </a>

    <a href="FinanceReport.jsp" 
       class="nav-item <%= request.getRequestURI().contains("FinanceReport.jsp") ? "active" : "" %>">
        <i class="fas fa-chart-line"></i> Báo cáo
    </a>

    <a href="ArticleManage.jsp" 
       class="nav-item <%= request.getRequestURI().contains("ArticleManage.jsp") ? "active" : "" %>">
        <i class="fas fa-blog"></i> Bài viết
    </a>

    <a href="CourseContentManager.jsp" 
       class="nav-item <%= request.getRequestURI().contains("CourseContentManager.jsp") ? "active" : "" %>">
        <i class="fas fa-video"></i> Nội dung
    </a>

    <a href="AssignmentDetails.jsp" 
       class="nav-item <%= request.getRequestURI().contains("AssignmentDetails.jsp") ? "active" : "" %>">
        <i class="fas fa-tasks"></i> Bài tập
    </a>
</nav>
