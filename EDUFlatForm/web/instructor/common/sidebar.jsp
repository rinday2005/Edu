<!-- File: /common/sidebar.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="sidebar">
    <a href="${pageContext.request.contextPath}/instructor/jsp/InstructorDashboard.jsp" 
       class="nav-item <%= request.getRequestURI().contains("InstructorDashboard.jsp") ? "active" : "" %>">
        <i class="fas fa-home"></i> Trang chủ
    </a>

    <a href="${pageContext.request.contextPath}/instructor/jsp/CourseManagement.jsp" 
       class="nav-item <%= request.getRequestURI().contains("CourseManagement.jsp") ? "active" : "" %>">
        <i class="fas fa-laptop-code"></i> Khóa học
    </a>

    <a href="${pageContext.request.contextPath}/instructor/jsp/FinanceReport.jsp" 
       class="nav-item <%= request.getRequestURI().contains("FinanceReport.jsp") ? "active" : "" %>">
        <i class="fas fa-chart-line"></i> Báo cáo
    </a>

    <a href="${pageContext.request.contextPath}/instructor/jsp/ArticleManage.jsp" 
       class="nav-item <%= request.getRequestURI().contains("ArticleManage.jsp") ? "active" : "" %>">
        <i class="fas fa-blog"></i> Bài viết
    </a>

    <a href="${pageContext.request.contextPath}/instructor/jsp/CourseContentManager.jsp" 
       class="nav-item <%= request.getRequestURI().contains("CourseContentManager.jsp") ? "active" : "" %>">
        <i class="fas fa-video"></i> Nội dung
    </a>

    <a href="${pageContext.request.contextPath}/instructor/jsp/AssignmentDetails.jsp" 
       class="nav-item <%= request.getRequestURI().contains("AssignmentDetails.jsp") ? "active" : "" %>">
        <i class="fas fa-tasks"></i> Bài tập
    </a>
</nav>
