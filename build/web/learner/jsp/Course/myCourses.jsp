<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="model.Courses" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Khóa học của tôi - E-Learning System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/course.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/learner/common/header.jsp" />
    <jsp:include page="/learner/common/sidebar.jsp" />

    <div class="my-courses-container">
        <div class="page-header">
            <h1><i class="fas fa-graduation-cap"></i> Khóa học của tôi</h1>
            <p>Danh sách các khóa học bạn đã mua và có thể học ngay</p>
        </div>

        <c:if test="${not empty error}">
            <div class="error-message">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty myCourses && myCourses.size() > 0}">
                <div class="stats-bar">
                    <div class="stat-item">
                        <span class="stat-value">${totalCourses}</span>
                        <span class="stat-label">Khóa học đã mua</span>
                    </div>
                </div>

                <div class="courses-grid">
                    <c:forEach var="course" items="${myCourses}">
                        <div class="course-card" data-course-id="${course.courseID}">
                            <img src="${course.imgURL != null && course.imgURL != '' ? course.imgURL : pageContext.request.contextPath + '/learner/images/course-default.jpg'}"
                                 alt="${course.name}"
                                 class="course-image"
                                 loading="lazy"
                                 decoding="async"
                                 onerror="this.src='${pageContext.request.contextPath}/learner/images/course-default.jpg'" />
                            <div class="course-content">
                                <h3 class="course-title">${course.name}</h3>
                                <p class="course-description">${course.description != null ? course.description : 'Không có mô tả'}</p>
                                
                                <div class="course-meta">
                                    <span class="course-level">${course.level != null ? course.level : 'N/A'}</span>
                                    <div class="course-rating">
<i class="fas fa-star"></i>
                                        <span>${course.rating != null ? course.rating : '0'}</span>
                                    </div>
                                </div>
                                
                                <div class="course-price ${course.price == 0 ? 'free' : ''}">
                                    <c:choose>
                                        <c:when test="${course.price == 0}">
                                            Miễn phí
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber value="${course.price}" type="number" groupingUsed="true" /> ₫
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                
                                <button class="btn-learn" type="button">
                                    <i class="fas fa-play"></i> Học ngay
                                </button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <i class="fas fa-book-open"></i>
                    <h2>Bạn chưa có khóa học nào</h2>
                    <p>Hãy khám phá và đăng ký các khóa học thú vị ngay bây giờ!</p>
                    <a href="${pageContext.request.contextPath}/CourseServletController" class="btn-browse">
                        <i class="fas fa-search"></i> Khám phá khóa học
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        // Optimize performance by using event delegation
        document.addEventListener('DOMContentLoaded', function() {
            const coursesGrid = document.querySelector('.courses-grid');
            if (coursesGrid) {
                coursesGrid.addEventListener('click', function(e) {
                    // Check if button was clicked
                    if (e.target.closest('.btn-learn')) {
                        e.stopPropagation();
                        const card = e.target.closest('.course-card');
                        if (card) {
                            const courseID = card.dataset.courseId;
                            if (courseID) {
                                navigateToRoom(courseID);
                            }
                        }
                        return;
                    }
                    
                    // Otherwise, check if card was clicked
                    const card = e.target.closest('.course-card');
                    if (card && !e.target.closest('.btn-learn')) {
                        const courseID = card.dataset.courseId;
                        if (courseID) {
navigateToRoom(courseID);
                        }
                    }
                });
            }
        });

        function navigateToRoom(courseID) {
            if (courseID) {
                window.location.href = '${pageContext.request.contextPath}/room?courseID=' + courseID;
            }
        }
    </script>
    <!-- Added global theme script -->
    <script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
</body>
</html>
