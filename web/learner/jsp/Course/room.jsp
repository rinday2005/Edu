<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${course.name} - E-Learning</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/course.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<jsp:include page="/learner/common/header.jsp" />

<div class="main-container">
    <jsp:include page="/learner/common/sidebar.jsp" />

    <main class="main-content">
        <div class="room-wrapper">
            <!-- LEFT -->
            <div class="room-main">

                <!-- VIDEO -->
                <div class="video-container" id="videoContainer"
                     style="display:${empty quizAssignment ? 'block' : 'none'};">
                    <video id="courseVideo" class="video-player" controls controlsList="nodownload">
                        <source src="https://www.w3schools.com/html/mov_bbb.mp4" type="video/mp4">
                    </video>
                </div>

                <!-- QUIZ -->
                <c:if test="${not empty quizAssignment}">
                    <jsp:include page="/learner/jsp/Course/quiz.jsp" />
                </c:if>

                <div class="video-info">
                    <h1 class="video-title">${course.name}</h1>
                    <p class="video-meta">Cập nhật tháng 11 năm 2022</p>
                </div>

                <!-- Q&A -->
                <div class="qa-section" onclick="openQAModal()">
                    <div class="qa-header">
                        <i class="fa fa-comments"></i>
                        <span class="qa-text">Hỏi đáp</span>
                        <span class="qa-count-badge">4</span>
                    </div>
                </div>
            </div>

            <!-- RIGHT -->
            <aside class="room-sidebar">
                <div class="sidebar-header">
                    <h3 class="sidebar-title">Nội dung khóa học</h3>
                </div>

                <div class="chapters-list">
                    <c:choose>
                        <c:when test="${not empty sections}">
                            <c:forEach var="section" items="${sections}" varStatus="status">
                                <div class="chapter">
                                    <div class="chapter-header" onclick="toggleChapter(this)">
                                        <span class="chapter-icon">▼</span>
                                        <h4 class="chapter-title">${status.count}. ${section.name}</h4>
                                    </div>
                                    <div class="chapter-content" style="display:block;">
                                        <c:set var="lessons" value="${lessonsMap[section.sectionID]}" />
                                        <c:choose>
                                            <c:when test="${not empty lessons}">
                                                <c:forEach var="lesson" items="${lessons}" varStatus="lessonStatus">
                                                    <div class="lesson-wrapper-item">
                                                        <div class="lesson-item"
                                                             data-lesson-id="${lesson.lessionID}"
                                                             data-section-id="${section.sectionID}"
                                                             data-video-url="${lesson.videoUrl}"
                                                             data-lesson-name="${lesson.name}"
                                                             onclick="loadLessonVideo(this)">
                                                            <span class="lesson-number">${lessonStatus.count}</span>
                                                            <span class="lesson-dot">●</span>
                                                            <span class="lesson-name">${lesson.name}</span>
                                                            <span class="lesson-time">${lesson.videoDuration}s</span>
                                                        </div>

                                                        <!-- Nút làm bài test -->
                                                        <form action="${pageContext.request.contextPath}/assignment"
                                                              method="get" style="display:inline;">
                                                            <input type="hidden" name="sectionID" value="${section.sectionID}" />
                                                            <input type="hidden" name="courseID" value="${currentCourseID}" />
                                                            <button type="submit" class="btn-test">
                                                                <i class="fa fa-file-alt"></i> Bài test
                                                            </button>
                                                        </form>
                                                    </div>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="lesson-item">
                                                    <span class="lesson-empty">Chưa có bài học</span>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <p>Không tìm thấy khóa học</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </aside>
        </div>
    </main>
</div>

<jsp:include page="/learner/common/footer.jsp" />
<script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
<script src="${pageContext.request.contextPath}/learner/js/course.js"></script>
</body>
</html>
