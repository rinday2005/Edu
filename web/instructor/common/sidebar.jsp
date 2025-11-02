<!-- File: /common/sidebar.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="sidebar">
    <a href="${pageContext.request.contextPath}/instructor/jsp/InstructorDashboard.jsp" 
       class="nav-item <%= request.getRequestURI().contains("InstructorDashboard.jsp") ? "active" : "" %>">
        <i class="fas fa-home"></i> Trang chủ
    </a>

    <a href="${pageContext.request.contextPath}/ManageCourse" 
       class="nav-item <%= request.getRequestURI().contains("CourseManagement") ? "active" : "" %>">
        <i class="fas fa-laptop-code"></i> Khóa học
    </a>

    <a href="${pageContext.request.contextPath}/instructor/jsp/FinanceReport.jsp" 
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
</nav>
