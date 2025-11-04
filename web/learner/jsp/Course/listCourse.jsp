<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Học Lập Trình Để Đi Làm - EduPlatform</title>
    <!-- Updated CSS path to src/css/course.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/course.css">
    
</head>
<body>
    <!-- Header -->
    <jsp:include page="/learner/common/header.jsp" />

    <div class="main-container">
        <!-- Sidebar -->
        <jsp:include page="/learner/common/sidebar.jsp" />
        <!-- Main Content -->
        <main class="main-content">
            <section class="pro-courses-section">
                <div class="section-header">
                  <h2>Khóa học Pro <span class="badge-pro">PRO</span></h2>
                  <a class="view-all-btn" href="${pageContext.request.contextPath}/course">Trở về trang chủ</a>
                </div>

                <div class="pro-courses-grid">
                  <c:if test="${empty courses}">
                    <p>Chưa có khóa học nào được duyệt.</p>
                  </c:if>

                  <c:forEach var="course" items="${courses}">
                    <div class="pro-course-card"
                         onclick="window.location.href='${pageContext.request.contextPath}/CourseServletController?action=detail&id=${course.courseID}'">
                      <div class="course-image">
                        <img src="${course.imgURL}" alt="${course.name}" class="course-image-img"/>
                      </div>
                      <div class="course-content">
                        <h3>${course.name}</h3>
                        <p class="course-description">${course.description}</p>
                        <div class="course-pricing">
                          <span class="discount-price">Giá tiền: ${course.price}đ</span>
                        </div>
                        <div class="course-stats-row">
                          <span class="stat-item">⭐ <span class="stat-label">${course.rating}/5</span></span>
                          <span class="stat-item">💰 <span class="stat-label">${course.level}</span></span>
                        </div>
                      </div>
                    </div>
                  </c:forEach>
                </div>
            </section>
        </main>
    </div>

    <!-- Footer -->
    <jsp:include page="/learner/common/footer.jsp" />

    <!-- Added global theme script before page-specific JS -->
    <script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
    <!-- Updated JS path to src/js/course.js -->
    <script src="${pageContext.request.contextPath}/learner/js/course.js"></script>
</body>
</html>
