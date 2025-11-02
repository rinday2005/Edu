<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.sql.*" %>

<%
    String courseID = request.getParameter("courseID");
    
    // Mock data - Replace with actual database queries
    java.util.Map<String, Object> courseData = new java.util.HashMap<>();
    java.util.List<java.util.Map<String, String>> sections = new java.util.ArrayList<>();
    java.util.Map<String, java.util.List<java.util.Map<String, String>>> lessonsMap = new java.util.HashMap<>();
    
    if (courseID != null && !courseID.isEmpty()) {
        // Sample course data - Replace with database query
        courseData.put("courseID", courseID);
        courseData.put("name", "Kiến Thức Nhập Môn IT");
        courseData.put("description", "Khóa học cơ bản về IT");
        courseData.put("thumbnailUrl", "${pageContext.request.contextPath}/assets/images/course-thumb.jpg");
        courseData.put("level", "Beginner");
        courseData.put("instructorName", "Hoàng Lộc");
        
        // Sample sections
        java.util.Map<String, String> section1 = new java.util.HashMap<>();
        section1.put("sectionID", "1");
        section1.put("name", "Khái niệm kỹ thuật cần biết");
        sections.add(section1);
        
        java.util.Map<String, String> section2 = new java.util.HashMap<>();
        section2.put("sectionID", "2");
        section2.put("name", "Môi trường, con người IT");
        sections.add(section2);
        
        // Sample lessons for section 1
        java.util.List<java.util.Map<String, String>> lessons1 = new java.util.ArrayList<>();
        java.util.Map<String, String> lesson1 = new java.util.HashMap<>();
        lesson1.put("lessonID", "1");
        lesson1.put("name", "Mô hình Client - Server là gì?");
        lesson1.put("videoDuration", "11:35");
        lessons1.add(lesson1);
        
        java.util.Map<String, String> lesson2 = new java.util.HashMap<>();
        lesson2.put("lessonID", "2");
        lesson2.put("name", "Domain là gì? Tên miền là gì?");
        lesson2.put("videoDuration", "10:34");
        lessons1.add(lesson2);
        
        lessonsMap.put("1", lessons1);
        
        // Sample lessons for section 2
        java.util.List<java.util.Map<String, String>> lessons2 = new java.util.ArrayList<>();
        java.util.Map<String, String> lesson3 = new java.util.HashMap<>();
        lesson3.put("lessonID", "3");
        lesson3.put("name", "Lập trình là gì?");
        lesson3.put("videoDuration", "15:20");
        lessons2.add(lesson3);
        
        lessonsMap.put("2", lessons2);
    }
    
    request.setAttribute("course", courseData);
    request.setAttribute("sections", sections);
    request.setAttribute("lessonsMap", lessonsMap);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${course.name} - E-Learning</title>
    <c:if test="${not empty sessionScope.user.avatarUrl}">
        <meta name="user-avatar" content="${sessionScope.user.avatarUrl}">
    </c:if>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/course.css">
</head>
<body>
<jsp:include page="/learner/common/header.jsp" />

<div class="main-container">
    <jsp:include page="/learner/common/sidebar.jsp" />

    <main class="main-content">
        <div class="room-wrapper">
            <!-- LEFT: Video and Content -->
            <div class="room-main">
                
                <!-- Video Container -->
                <div class="video-container">
                    <video id="courseVideo" class="video-player" controls controlsList="nodownload">
                        <source src="https://www.w3schools.com/html/mov_bbb.mp4" type="video/mp4">
                       
                    </video>
                </div>

                <!-- Video Title and Meta -->
                <div class="video-info">
                    <h1 class="video-title">${course.name}</h1>
                    <p class="video-meta">Cập nhật tháng 11 năm 2022</p>
                </div>

                <!-- Q&A Section - Compact Button -->
                <div class="qa-section" onclick="openQAModal()">
                    <div class="qa-header">
                        <svg class="qa-icon" viewBox="0 0 24 24" width="18" height="18" fill="currentColor">
                            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm3.5-9c.83 0 1.5-.67 1.5-1.5S16.33 8 15.5 8 14 8.67 14 9.5s.67 1.5 1.5 1.5zm-7 0c.83 0 1.5-.67 1.5-1.5S9.33 8 8.5 8 7 8.67 7 9.5 7.67 11 8.5 11zm3.5 6.5c2.33 0 4.31-1.46 5.11-3.5H6.89c.8 2.04 2.78 3.5 5.11 3.5z"/>
                        </svg>
                        <span class="qa-text">Hỏi đáp</span>
                        <span class="qa-count-badge">4</span>
                    </div>
                </div>
            </div>

            <!-- RIGHT: Course Content Sidebar -->
            <aside class="room-sidebar">
                <div class="sidebar-header">
                    <h3 class="sidebar-title">Nội dung khóa học</h3>
                </div>

                <!-- Chapters List -->
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
                                        <c:set var="lessons" value="${lessonsMap[section.sectionID]}"/>
                                        <c:choose>
                                            <c:when test="${not empty lessons}">
                                                <c:forEach var="lesson" items="${lessons}" varStatus="lessonStatus">
                                                    <div class="lesson-item">
                                                        <span class="lesson-number">${lessonStatus.count}</span>
                                                        <span class="lesson-dot">●</span>
                                                        <span class="lesson-name">${lesson.name}</span>
                                                        <span class="lesson-time">${lesson.videoDuration}</span>
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

<!-- Q&A Modal - Hidden by default, shown when clicking "Hỏi đáp" -->
<div class="qa-modal-overlay" id="qaModalOverlay">
    <div class="qa-modal-container">
        <div class="qa-modal-header">
            <input type="text" class="qa-input" id="qaInput" placeholder="Nhập bình luận mới của bạn" />
            <button class="qa-send-btn" id="qaSendBtn" title="Gửi bình luận">
                <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
                    <path d="M16.6915026,12.4744748 L3.50612381,13.2599618 C3.19218622,13.2599618 3.03521743,13.4170592 3.03521743,13.5741566 L1.15159189,20.0151496 C0.8376543,20.8006365 0.99,21.89 1.77946707,22.52 C2.41,22.99 3.50612381,23.1 4.13399899,22.8429026 L21.714504,14.0454487 C22.6563168,13.5741566 23.1272231,12.6315722 22.9702544,11.6889879 L4.13399899,1.16346272 C3.34915502,0.9 2.40734225,1.00636533 1.77946707,1.4776575 C0.994623095,2.10604706 0.837654326,3.0486314 1.15159189,3.99701575 L3.03521743,10.4380088 C3.03521743,10.5951061 3.19218622,10.7522035 3.50612381,10.7522035 L16.6915026,11.5376905 C16.6915026,11.5376905 17.1624089,11.5376905 17.1624089,12.0089827 C17.1624089,12.4744748 16.6915026,12.4744748 16.6915026,12.4744748 Z"/>
                </svg>
            </button>
            <button class="qa-close-btn" onclick="closeQAModal()">×</button>
        </div>

        <div class="qa-stats">
            <span class="qa-count">340 bình luận</span>
            <span class="qa-warning">Nếu thấy bình luận spam, các bạn bấm report giúp admin nhé</span>
        </div>

        <div class="qa-comments-list" id="qaCommentsList">
            <!-- Hidden input for user avatar -->
            <c:if test="${not empty sessionScope.user.avatarUrl}">
                <input type="hidden" id="userAvatarUrl" value="${sessionScope.user.avatarUrl}">
            </c:if>
            
            <!-- Comment 1 -->
            <div class="qa-comment">
                <div class="qa-comment-avatar" style="background-image: url('${pageContext.request.contextPath}/assets/images/avatar1.jpg')"></div>
                <div class="qa-comment-content">
                    <div class="qa-comment-header">
                        <span class="qa-comment-author">Dũy Khang Trần</span>
                        <span class="qa-comment-time">12 ngày trước</span>
                    </div>
                    <div class="qa-comment-text">&lt;h1&gt; heelo&lt;/h1&gt;</div>
                    <div class="qa-comment-actions">
                        <button class="qa-action-btn qa-like-btn" onclick="toggleLike(this)">Thích</button>
                        <button class="qa-action-btn qa-reply-btn" onclick="toggleReply(this)">Phản hồi</button>
                        <button class="qa-action-menu">...</button>
                    </div>
                </div>
            </div>

            <!-- Comment 2 -->
            <div class="qa-comment">
                <div class="qa-comment-avatar" style="background-image: url('${pageContext.request.contextPath}/assets/images/avatar2.jpg')"></div>
                <div class="qa-comment-content">
                    <div class="qa-comment-header">
                        <span class="qa-comment-author">Dũy Khang Trần</span>
                        <span class="qa-comment-time">12 ngày trước</span>
                    </div>
                    <div class="qa-comment-text">
                        <div>1. hello-</div>
                        <div>2. xin chào</div>
                    </div>
                    <div class="qa-comment-actions">
                        <button class="qa-action-btn qa-like-btn" onclick="toggleLike(this)">Thích</button>
                        <button class="qa-action-btn qa-reply-btn" onclick="toggleReply(this)">Phản hồi</button>
                        <button class="qa-action-menu">...</button>
                    </div>
                </div>
            </div>

            <!-- Comment 3 -->
            <div class="qa-comment">
                <div class="qa-comment-avatar" style="background-image: url('${pageContext.request.contextPath}/assets/images/avatar3.jpg')"></div>
                <div class="qa-comment-content">
                    <div class="qa-comment-header">
                        <span class="qa-comment-author">Mobi TV Mrx</span>
                        <span class="qa-comment-time">13 ngày trước</span>
                    </div>
                    <div class="qa-comment-text">Em yêu</div>
                    <div class="qa-comment-actions">
                        <button class="qa-action-btn qa-like-btn liked" onclick="toggleLike(this)">Thích</button>
                        <button class="qa-action-btn qa-reply-btn" onclick="toggleReply(this)">Phản hồi</button>
                        <button class="qa-action-menu">...</button>
                        <span class="qa-like-count">👍 1</span>
                    </div>
                </div>
            </div>

            <!-- Comment 4 -->
            <div class="qa-comment">
                <div class="qa-comment-avatar" style="background-image: url('${pageContext.request.contextPath}/assets/images/avatar1.jpg')"></div>
                <div class="qa-comment-content">
                    <div class="qa-comment-header">
                        <span class="qa-comment-author">le Duong</span>
                        <span class="qa-comment-time">22 ngày trước</span>
                    </div>
                    <div class="qa-comment-text">tuyệt vời a</div>
                    <div class="qa-comment-actions">
                        <button class="qa-action-btn qa-like-btn" onclick="toggleLike(this)">Thích</button>
                        <button class="qa-action-btn qa-reply-btn" onclick="toggleReply(this)">Phản hồi</button>
                        <button class="qa-action-menu">...</button>
                        <span class="qa-edited">Đã chỉnh sửa</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
<script src="${pageContext.request.contextPath}/learner/js/course.js"></script>
</body>
</html>
