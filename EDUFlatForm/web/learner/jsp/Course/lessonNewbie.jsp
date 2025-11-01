<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${course.name} - E-Learning</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/course.css">
    
</head>
<body>
<jsp:include page="/learner/common/header.jsp" />

<div class="main-container">
    <jsp:include page="/learner/common/sidebar.jsp" />

    <main class="main-content">
        <div class="lesson-wrapper">
            <!-- LEFT -->
            <div class="lesson-detail">
                <div class="lesson-header">
                    <h1>${course.name}</h1>
                    <p>${course.description}</p>
                </div>

                <section class="lesson-section">
                    <h2 class="section-title">Bạn sẽ học được gì?</h2>
                    <div class="learn-grid">
                        <div class="learn-item"><span class="learn-icon">✓</span><p>Nắm vững kiến thức nền tảng</p></div>
                        <div class="learn-item"><span class="learn-icon">✓</span><p>Hiểu cấu trúc & tư duy lập trình</p></div>
                        <div class="learn-item"><span class="learn-icon">✓</span><p>Ứng dụng vào dự án thực tế</p></div>
                    </div>
                </section>

                <section class="lesson-section">
                    <h2 class="section-title">Nội dung khóa học</h2>
                    <p class="content-summary">
                        ${fn:length(sections)} chương • ${totalLessons} bài học • Thời lượng: ${totalMinutes} phút
                    </p>

                    <c:forEach var="section" items="${sections}">
                        <div class="chapter">
                          <div class="chapter-header" onclick="toggleChapter(this)">
                            <span class="chapter-icon">−</span>
                            <h3 class="chapter-title">${section.name}</h3>
                          </div>
                          <div class="chapter-content" style="display:block;">
                            <c:set var="ls" value="${lessonsMap[section.sectionID]}"/>
                            <c:choose>
                              <c:when test="${not empty ls}">
                                <c:forEach var="lesson" items="${ls}">
                                  <div class="lesson-item-row">
                                    <span class="lesson-icon">●</span>
                                    <span class="lesson-title">${lesson.name}</span>
                                    <span class="lesson-duration">${lesson.videoDuration} phút</span>
                                  </div>
                                </c:forEach>
                              </c:when>
                              <c:otherwise>
                                <div class="lesson-item-row">Chưa có bài học</div>
                              </c:otherwise>
                            </c:choose>
                          </div>
                        </div>
                    </c:forEach>
                </section>
            </div>

            <!-- RIGHT -->
            <aside class="lesson-sidebar">
                <div class="video-preview-card">
                    <div class="video-preview-content">
                        <div class="video-play-button">
                            <svg viewBox="0 0 24 24" width="48" height="48" fill="white"><path d="M8 5v14l11-7z"/></svg>
                        </div>
                        <div class="video-preview-text">
                            <h4>${course.name}</h4>
                            <p>${course.level}</p>
                        </div>
                    </div>
                    <p class="video-preview-label"><a href="#">Xem giới thiệu khóa học</a></p>
                </div>

                <div class="course-info-card">
<!--                    <div class="course-price"><span class="price-label">Miễn phí</span></div>-->
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <!-- Đã đăng nhập: chuyển đến room.jsp -->
                            <a href="${pageContext.request.contextPath}/course/room.jsp?courseID=${course.courseID}" class="btn-enroll">ĐĂNG KÝ HỌC</a>
                        </c:when>
                        <c:otherwise>
                            <!-- Chưa đăng nhập: chuyển đến trang đăng nhập với redirect về room.jsp -->
                            <a href="${pageContext.request.contextPath}/login?redirect=${pageContext.request.contextPath}/course/room.jsp?courseID=${course.courseID}" class="btn-enroll">ĐĂNG KÝ HỌC</a>
                        </c:otherwise>
                    </c:choose>

                    <div class="course-info-list">
                        <div class="info-item">Trình độ: ${course.level}</div>
                        <div class="info-item">Tổng số bài: ${totalLessons}</div>
                        <div class="info-item">Thời lượng: ${totalMinutes} phút</div>
                        <div class="info-item">Giảng viên: ${course.instructorName}</div>
                    </div>
                </div>
            </aside>
        </div>
    </main>
</div>

<jsp:include page="/learner/common/footer.jsp" />
<script src="${pageContext.request.contextPath}/learne/js/course.js"></script>

<!-- Added script to handle room navigation -->
<script>
    function navigateToRoom(courseId) {
        window.location.href = '${pageContext.request.contextPath}/course/room.jsp?id=' + courseId;
    }
</script>
</body>
</html>




