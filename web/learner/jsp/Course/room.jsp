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
                        <span class="qa-count-badge" id="qaCountBadge">0</span>
                    </div>
                </div>
            </div>
            
            <!-- Q&A Modal -->
            <div id="qaModalOverlay" class="qa-modal-overlay" style="display: none;">
                <div class="qa-modal-container">
                    <div class="qa-modal-header">
                        <h3 class="qa-modal-title">Hỏi đáp</h3>
                        <span class="qa-count" id="qaCount">0 bình luận</span>
                        <button class="qa-close-btn" onclick="closeQAModal()">×</button>
                    </div>
                    <div class="qa-modal-body">
                        <div class="qa-input-section">
                            <input type="text" id="qaInput" class="qa-input" placeholder="Nhập câu hỏi hoặc bình luận của bạn..." />
                            <button id="qaSendBtn" class="qa-send-btn" onclick="submitComment()">
                                <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                                    <path d="M16.6915026,12.4744748 L3.50612381,13.2599618 C3.19218622,13.2599618 3.03521743,13.4170592 3.03521743,13.5741566 L1.15159189,20.0151496 C0.8376543,20.8006365 0.99,21.89 1.77946707,22.52 C2.41,22.99 3.50612381,23.1 4.13399899,22.8429026 L21.714504,14.0454487 C22.6563168,13.5741566 23.1272231,12.6315722 22.9702544,11.6889879 L4.13399899,1.16346272 C3.34915502,0.9 2.40734225,1.00636533 1.77946707,1.4776575 C0.994623095,2.10604706 0.837654326,3.0486314 1.15159189,3.99701575 L3.03521743,10.4380088 C3.03521743,10.5951061 3.19218622,10.7522035 3.50612381,10.7522035 L16.6915026,11.5376905 C16.6915026,11.5376905 17.1624089,11.5376905 17.1624089,12.0089827 C17.1624089,12.4744748 16.6915026,12.4744748 16.6915026,12.4744748 Z"/>
                                </svg>
                            </button>
                        </div>
                        <div id="qaCommentsList" class="qa-comments-list"></div>
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
<script>
    // Set courseID for JavaScript
    window.currentCourseID = "${currentCourseID}";
    document.body.setAttribute('data-context', '${pageContext.request.contextPath}');
    <c:if test="${not empty sessionScope.user}">
    document.body.setAttribute('data-user-id', '${sessionScope.user.userID}');
    document.body.setAttribute('data-user-role', '${sessionScope.user.role}');
    // Also set as meta tags for compatibility
    if (!document.querySelector('meta[name="user-id"]')) {
        const metaId = document.createElement('meta');
        metaId.name = 'user-id';
        metaId.content = '${sessionScope.user.userID}';
        document.head.appendChild(metaId);
    }
    if (!document.querySelector('meta[name="user-role"]')) {
        const metaRole = document.createElement('meta');
        metaRole.name = 'user-role';
        metaRole.content = '${sessionScope.user.role}';
        document.head.appendChild(metaRole);
    }
    <c:if test="${not empty sessionScope.user.avatarUrl}">
    document.getElementById('userAvatarUrl') || document.body.appendChild(Object.assign(document.createElement('input'), {id: 'userAvatarUrl', type: 'hidden', value: '${sessionScope.user.avatarUrl}'}));
    </c:if>
    </c:if>
</script>
<script src="${pageContext.request.contextPath}/learner/js/course.js"></script>
</body>
</html>
