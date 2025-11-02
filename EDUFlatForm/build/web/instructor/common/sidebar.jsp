<!-- File: /common/sidebar.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="sidebar">
    <a href="${pageContext.request.contextPath}/instructor/jsp/InstructorDashboard.jsp" 
       class="nav-item <%= request.getRequestURI().contains("InstructorDashboard.jsp") ? "active" : "" %>">
        <i class="fas fa-home"></i> Trang chủ
    </a>

    <a href="${pageContext.request.contextPath}/ManageCourse" 
       class="nav-item <%= request.getRequestURI().contains("CourseManagement.jsp") ? "active" : "" %>">
        <i class="fas fa-laptop-code"></i> Khóa học
    </a>

    <a href="${pageContext.request.contextPath}/instructor/jsp/FinanceReport.jsp" 
       class="nav-item <%= request.getRequestURI().contains("FinanceReport.jsp") ? "active" : "" %>">
        <i class="fas fa-chart-line"></i> Ví tiền
    </a>

    <a href="${pageContext.request.contextPath}/ManageArtical?action=listartical" 
       class="nav-item <%= request.getRequestURI().contains("ArticleManage.jsp") ? "active" : "" %>">
        <i class="fas fa-blog"></i> Bài viết
    </a>

    

    <a href="${pageContext.request.contextPath}/instructor/jsp/AssignmentDetails.jsp" 
       class="nav-item <%= request.getRequestURI().contains("AssignmentDetails.jsp") ? "active" : "" %>">
        <i class="fas fa-tasks"></i> Bài tập
    </a>
</nav>
