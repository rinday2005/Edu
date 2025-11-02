<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Kiểm tra nếu chưa có dữ liệu và không phải từ servlet, redirect về servlet để load -->
<c:if test="${empty section and empty fromServlet and empty error}">
    <c:redirect url="${pageContext.request.contextPath}/ManageLesson"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Bài học - InstructorsHome</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
    <div class="main-container">
         <!-- ===== HEADER ===== -->
    <jsp:include page="/instructor/common/header.jsp" />

    <!-- ===== SIDEBAR ===== -->
   <jsp:include page="/instructor/common/sidebar.jsp" />

        <main class="content-area">
            <!-- Messages -->
            <c:if test="${not empty message}">
                <div style="background-color: #d4edda; color: #155724; padding: 15px; border-radius: 5px; margin: 20px; border: 1px solid #c3e6cb;">
                    ${message}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div style="background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin: 20px; border: 1px solid #f5c6cb;">
                    ${error}
                </div>
            </c:if>

            <!-- Section Info -->
            <c:if test="${not empty section}">
                <section class="section">
                    <div style="background-color: var(--bg-light); padding: 20px; border-radius: 8px; border-left: 4px solid var(--primary-color); margin-bottom: 20px;">
                        <h2><i class="fas fa-book"></i> ${fn:escapeXml(section.name)}</h2>
                        <p style="color: var(--text-muted); margin: 5px 0;">
                            <c:if test="${not empty section.description}">
                                ${fn:escapeXml(section.description)}
                            </c:if>
                            <c:if test="${empty section.description}">
                                Chưa có mô tả
                            </c:if>
                        </p>
                        <a href="<c:url value='/ManageSection?course=${section.courseID}'/>" class="btn btn-secondary" style="margin-top: 10px;">
                            <i class="fas fa-arrow-left"></i> Quay lại quản lý chương
                        </a>
                    </div>
                </section>
            </c:if>

            <!-- Create Lesson Section -->
            <section class="section">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h2><i class="fas fa-plus-circle"></i> Thêm Bài học Mới</h2>
                    <button class="btn btn-secondary" onclick="toggleCreateLessonForm()" id="toggleCreateBtn">
                        <i class="fas fa-chevron-down"></i> Hiển thị Form
                    </button>
                </div>
                
                <!-- Create Lesson Form -->
                <div id="createLessonForm" style="display: none; background-color: var(--bg-light); padding: 25px; border-radius: 8px; border-left: 4px solid var(--primary-color);">
                    <form id="lessonForm" action="<c:url value='/ManageLesson'/>" method="post">
                        <input type="hidden" name="action" value="${not empty lesson ? 'update' : 'create'}">
                        <c:if test="${not empty lesson}">
                            <input type="hidden" name="lessonId" value="${lesson.lessionID}">
                        </c:if>
                        <input type="hidden" name="sectionId" value="${section.sectionID}">
                        
                        <div class="form-group">
                            <label for="lessonName">Tên bài học *</label>
                            <input type="text" id="lessonName" name="lessonName" class="form-control" 
                                   placeholder="Ví dụ: Giới thiệu về JavaScript" 
                                   value="${not empty lesson ? fn:escapeXml(lesson.name) : ''}" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="lessonDescription">Mô tả bài học</label>
                            <textarea id="lessonDescription" name="lessonDescription" class="form-control" rows="3" 
                                      placeholder="Mô tả chi tiết về bài học">${not empty lesson ? fn:escapeXml(lesson.description) : ''}</textarea>
                        </div>
                        
                        <div class="form-group">
                            <label for="videoUrl">URL Video *</label>
                            <input type="url" id="videoUrl" name="videoUrl" class="form-control" 
                                   placeholder="https://www.youtube.com/watch?v=..." 
                                   value="${not empty lesson ? fn:escapeXml(lesson.videoUrl) : ''}" required>
                        </div>
                        
                        <div style="display: flex; gap: 20px;">
                            <div class="form-group" style="flex: 1;">
                                <label for="videoDuration">Thời lượng video (giây)</label>
                                <input type="number" id="videoDuration" name="videoDuration" class="form-control" 
                                       min="0" step="1" 
                                       value="${not empty lesson ? lesson.videoDuration : 0}" 
                                       placeholder="Ví dụ: 3600 (1 giờ)">
                            </div>
                            
                            <div class="form-group" style="flex: 1;">
                                <label for="status">Trạng thái</label>
                                <select id="status" name="status" class="form-control">
                                    <option value="true" ${not empty lesson && lesson.status ? 'selected' : ''}>Kích hoạt</option>
                                    <option value="false" ${not empty lesson && !lesson.status ? 'selected' : ''}>Tắt</option>
                                </select>
                            </div>
                        </div>
                        
                        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px;">
                            <c:if test="${not empty lesson}">
                                <button type="button" class="btn btn-secondary" onclick="cancelEdit()">Hủy</button>
                            </c:if>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save"></i> ${not empty lesson ? 'Cập nhật' : 'Tạo'} Bài học
                            </button>
                        </div>
                    </form>
                </div>
            </section>

            <!-- Lessons List -->
            <section class="section">
                <h2><i class="fas fa-list"></i> Danh sách Bài học</h2>
                <c:choose>
                    <c:when test="${empty lessons or fn:length(lessons) == 0}">
                        <div style="background-color: var(--bg-light); padding: 40px; text-align: center; border-radius: 8px;">
                            <i class="fas fa-book-open" style="font-size: 48px; color: var(--text-muted); margin-bottom: 20px;"></i>
                            <p style="color: var(--text-muted); font-size: 16px;">Chưa có bài học nào trong chương này</p>
                            <button class="btn btn-primary" onclick="toggleCreateLessonForm()" style="margin-top: 15px;">
                                <i class="fas fa-plus"></i> Thêm bài học đầu tiên
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table" style="width: 100%; border-collapse: collapse; background-color: var(--bg-card); border-radius: 8px; overflow: hidden;">
                                <thead>
                                    <tr style="background-color: var(--bg-light);">
                                        <th style="padding: 15px; text-align: left; border-bottom: 2px solid var(--border-color);">
                                            <i class="fas fa-book"></i> Tên bài học
                                        </th>
                                        <th style="padding: 15px; text-align: left; border-bottom: 2px solid var(--border-color);">
                                            <i class="fas fa-video"></i> Video URL
                                        </th>
                                        <th style="padding: 15px; text-align: center; border-bottom: 2px solid var(--border-color);">
                                            <i class="fas fa-clock"></i> Thời lượng
                                        </th>
                                        <th style="padding: 15px; text-align: center; border-bottom: 2px solid var(--border-color);">
                                            <i class="fas fa-toggle-on"></i> Trạng thái
                                        </th>
                                        <th style="padding: 15px; text-align: center; border-bottom: 2px solid var(--border-color);">
                                            <i class="fas fa-cogs"></i> Thao tác
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="lesson" items="${lessons}">
                                        <tr style="border-bottom: 1px solid var(--border-color);">
                                            <td style="padding: 15px;">
                                                <strong>${fn:escapeXml(lesson.name)}</strong>
                                                <c:if test="${not empty lesson.description}">
                                                    <br><small style="color: var(--text-muted);">${fn:escapeXml(lesson.description)}</small>
                                                </c:if>
                                            </td>
                                            <td style="padding: 15px;">
                                                <a href="${fn:escapeXml(lesson.videoUrl)}" target="_blank" style="color: var(--primary-color); text-decoration: none;">
                                                    <i class="fas fa-external-link-alt"></i> Xem video
                                                </a>
                                            </td>
                                            <td style="padding: 15px; text-align: center;">
                                                <c:choose>
                                                    <c:when test="${lesson.videoDuration > 0}">
                                                        ${lesson.videoDuration / 60} phút
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span style="color: var(--text-muted);">Chưa có</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td style="padding: 15px; text-align: center;">
                                                <c:choose>
                                                    <c:when test="${lesson.status}">
                                                        <span class="btn btn-success" style="padding: 3px 8px; font-size: 11px;">
                                                            <i class="fas fa-check"></i> Kích hoạt
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="btn btn-secondary" style="padding: 3px 8px; font-size: 11px;">
                                                            <i class="fas fa-times"></i> Tắt
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td style="padding: 15px; text-align: center;">
                                                <div class="table-actions" style="display: flex; gap: 10px; justify-content: center;">
                                                    <a href="<c:url value='/ManageLesson?action=edit&id=${lesson.lessionID}&section=${section.sectionID}'/>" 
                                                       class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                        <i class="fas fa-edit"></i> Sửa
                                                    </a>
                                                    <a href="<c:url value='/ManageLesson?action=delete&id=${lesson.lessionID}&section=${section.sectionID}'/>" 
                                                       class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;"
                                                       onclick="return confirm('Xóa bài học này?');">
                                                        <i class="fas fa-trash"></i> Xóa
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/instructor/js/instructorsHome.js"></script>
    
    <script>
        function toggleCreateLessonForm() {
            const form = document.getElementById('createLessonForm');
            const btn = document.getElementById('toggleCreateBtn');
            if (form.style.display === 'none') {
                form.style.display = 'block';
                btn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
            } else {
                form.style.display = 'none';
                btn.innerHTML = '<i class="fas fa-chevron-down"></i> Hiển thị Form';
            }
        }
        
        function cancelEdit() {
            window.location.href = '${pageContext.request.contextPath}/ManageLesson?action=list&section=${section.sectionID}';
        }
        
        // Tự động hiển thị form khi có lesson để edit
        <c:if test="${not empty lesson and isEdit}">
            document.addEventListener('DOMContentLoaded', function() {
                toggleCreateLessonForm();
                document.getElementById('lessonName').focus();
            });
        </c:if>
        
        // Tự động hiển thị form khi isCreate = true
        <c:if test="${isCreate}">
            document.addEventListener('DOMContentLoaded', function() {
                toggleCreateLessonForm();
                document.getElementById('lessonName').focus();
            });
        </c:if>
    </script>
</body>
</html>

