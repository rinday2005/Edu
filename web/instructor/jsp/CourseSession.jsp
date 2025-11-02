<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Chương - InstructorsHome</title>
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
            <!-- Kiểm tra nếu chưa có dữ liệu và không phải từ servlet, redirect về servlet để load -->
            <c:if test="${empty sections and empty fromServlet and empty error}">
                <script>
                    window.location.href = '${pageContext.request.contextPath}/ManageSection';
                </script>
            </c:if>
            
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

            <!-- Course Info -->
            <c:if test="${not empty course}">
                <section class="section">
                    <div style="background-color: var(--bg-light); padding: 20px; border-radius: 8px; border-left: 4px solid var(--primary-color);">
                        <h2><i class="fas fa-book"></i> ${fn:escapeXml(course.name)}</h2>
                        <p style="color: var(--text-muted); margin: 5px 0;">
                            <i class="fas fa-info-circle"></i> ${fn:escapeXml(course.description)}
                        </p>
                        <p style="color: var(--text-muted); margin: 5px 0;">
                            <i class="fas fa-signal"></i> Cấp độ: ${course.level} | 
                            <i class="fas fa-dollar-sign"></i> Giá: ${course.price}
                        </p>
                    </div>
                </section>
            </c:if>

            <!-- Create Section Section -->
            <section class="section">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h2><i class="fas fa-plus-circle"></i> Thêm Chương Mới</h2>
                    <button class="btn btn-secondary" onclick="toggleCreateSectionForm()" id="toggleCreateBtn">
                        <i class="fas fa-chevron-down"></i> Hiển thị Form
                    </button>
                </div>
                
                <!-- Create Section Form -->
                <div id="createSectionForm" style="display: none; background-color: var(--bg-light); padding: 25px; border-radius: 8px; border-left: 4px solid var(--primary-color);">
                    <form id="sectionForm" action="<c:url value='/ManageSection'/>" method="post">
                        <input type="hidden" name="action" value="${not empty section ? 'update' : 'create'}">
                        <c:if test="${not empty section}">
                            <input type="hidden" name="id" value="${section.sectionID}">
                        </c:if>
                        <c:if test="${not empty courseID}">
                            <input type="hidden" name="courseID" value="${courseID}">
                        </c:if>
                        
                        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 20px;">
                            <div>
                                <h4 style="margin: 0 0 10px 0; color: var(--text-dark);">Thông tin Chương</h4>
                                <div class="form-group">
                                    <label for="sectionName">Tên chương *</label>
                                    <input type="text" id="sectionName" name="sectionName" class="form-control" 
                                           placeholder="Ví dụ: Giới thiệu React" 
                                           value="${not empty section ? fn:escapeXml(section.name) : ''}" required>
                                </div>
                                <div class="form-group">
                                    <label for="sectionDescription">Mô tả chương</label>
                                    <textarea id="sectionDescription" name="sectionDescription" class="form-control" rows="4" 
                                              placeholder="Nhập mô tả chương...">${not empty section ? fn:escapeXml(section.description) : ''}</textarea>
                                </div>
                            </div>
                            
                            <div>
                                <h4 style="margin: 0 0 10px 0; color: var(--text-dark);">Cài đặt</h4>
                                <div class="form-group">
                                    <label for="statusSelect">Trạng thái</label>
                                    <select id="statusSelect" name="status" class="form-control">
                                        <option value="true" ${not empty section && section.status ? 'selected' : ''}>Kích hoạt</option>
                                        <option value="false" ${not empty section && !section.status ? 'selected' : ''}>Tắt</option>
                                    </select>
                                </div>
                                <c:if test="${empty courseID}">
                                    <div class="form-group">
                                        <label>Khóa học (nếu tạo từ danh sách tổng)</label>
                                        <p style="color: var(--text-muted); font-size: 14px;">
                                            <i class="fas fa-info-circle"></i> Chương này sẽ được liên kết với khóa học khi tạo từ trang quản lý khóa học
                                        </p>
                                    </div>
                                </c:if>
                                <c:if test="${not empty course}">
                                    <div class="form-group">
                                        <label>Khóa học liên kết</label>
                                        <p style="color: var(--text-dark); font-weight: 600;">
                                            ${fn:escapeXml(course.name)}
                                        </p>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        
                        <!-- Lesson Management Section -->
                        <div style="margin-top: 30px; border-top: 2px solid var(--border-color); padding-top: 20px;">
                            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                                <h4 style="margin: 0; color: var(--text-dark);">
                                    <i class="fas fa-book-open"></i> Quản lý Bài học
                                </h4>
                                <button type="button" class="btn btn-info btn-sm" onclick="addLessonRow()" id="addLessonBtn" style="display: none;">
                                    <i class="fas fa-plus"></i> Thêm bài học
                                </button>
                            </div>
                            
                            <div id="lessonsContainer" style="display: grid; gap: 15px;">
                                <!-- Lessons will be added here dynamically or from existing data -->
                                <c:if test="${not empty section}">
                                    <!-- Load existing lessons for this section -->
                                    <c:set var="sectionLessons" value="${requestScope.sectionLessons}" />
                                    <c:if test="${not empty sectionLessons}">
                                        <c:forEach var="lesson" items="${sectionLessons}" varStatus="loop">
                                            <div class="lesson-row" data-lesson-index="${loop.index}">
                                                <div style="background-color: var(--bg-card); padding: 15px; border-radius: 8px; border: 1px solid var(--border-color);">
                                                    <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 10px;">
                                                        <h5 style="margin: 0; color: var(--text-dark);">Bài học #${loop.index + 1}</h5>
                                                        <button type="button" class="btn btn-danger btn-sm" onclick="removeLessonRow(this)" style="padding: 3px 8px;">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                    </div>
                                                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                                                        <div class="form-group">
                                                            <label>Tên bài học *</label>
                                                            <input type="text" name="lessonNames" class="form-control" 
                                                                   value="${fn:escapeXml(lesson.name)}" placeholder="Nhập tên bài học" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>URL Video *</label>
                                                            <input type="url" name="lessonUrls" class="form-control" 
                                                                   value="${fn:escapeXml(lesson.videoUrl)}" placeholder="https://..." required>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" style="margin-top: 10px;">
                                                        <label>Mô tả</label>
                                                        <textarea name="lessonDescriptions" class="form-control" rows="2" 
                                                                  placeholder="Mô tả bài học...">${fn:escapeXml(lesson.description)}</textarea>
                                                    </div>
                                                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-top: 10px;">
                                                        <div class="form-group">
                                                            <label>Thời lượng (giây)</label>
                                                            <input type="number" name="lessonDurations" class="form-control" 
                                                                   value="${lesson.videoDuration}" min="0" placeholder="3600">
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Trạng thái</label>
                                                            <select name="lessonStatuses" class="form-control">
                                                                <option value="true" ${lesson.status ? 'selected' : ''}>Kích hoạt</option>
                                                                <option value="false" ${!lesson.status ? 'selected' : ''}>Tắt</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <c:if test="${not empty lesson.lessionID}">
                                                        <input type="hidden" name="lessonIds" value="${lesson.lessionID}">
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </div>
                            
                            <div style="margin-top: 15px;">
                                <button type="button" class="btn btn-info" onclick="addLessonRow()">
                                    <i class="fas fa-plus"></i> Thêm bài học mới
                                </button>
                            </div>
                        </div>
                        
                        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px; border-top: 1px solid var(--border-color); padding-top: 15px;">
                            <button type="button" class="btn btn-secondary" onclick="cancelForm()">
                                <i class="fas fa-times"></i> Hủy
                            </button>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-check"></i> ${not empty section ? 'Cập nhật' : 'Tạo'} Chương
                            </button>
                        </div>
                    </form>
                </div>
            </section>

            <!-- Sections List -->
            <section class="section">
                <h2><i class="fas fa-list"></i> Danh sách Chương</h2>
                
                <c:if test="${not empty sections}">
                    <p style="color: green; margin-bottom: 10px;">
                        <i class="fas fa-check-circle"></i> Tìm thấy ${fn:length(sections)} chương
                    </p>
                </c:if>
                <c:if test="${empty sections}">
                    <p style="color: orange; margin-bottom: 10px;">
                        <i class="fas fa-exclamation-triangle"></i> Chưa có chương nào. Hãy tạo chương mới!
                    </p>
                </c:if>

                <div class="card">
                    <div class="card-header">Tất cả chương của bạn</div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${empty sections}">
                                <div style="text-align: center; padding: 40px; color: var(--text-muted);">
                                    <i class="fas fa-inbox" style="font-size: 48px; margin-bottom: 15px; display: block;"></i>
                                    <p style="font-size: 16px; margin: 0;">Chưa có chương nào. Hãy tạo chương mới!</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th><i class="fas fa-heading"></i> Tên chương</th>
                                                <th><i class="fas fa-align-left"></i> Mô tả</th>
                                                <th><i class="fas fa-book"></i> Khóa học</th>
                                                <th><i class="fas fa-toggle-on"></i> Trạng thái</th>
                                                <th><i class="fas fa-cogs"></i> Thao tác</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="section" items="${sections}">
                                                <tr>
                                                    <td>
                                                        <strong>${fn:escapeXml(section.name)}</strong>
                                                        <c:set var="sectionIdStr" value="${section.sectionID.toString()}" />
                                                        <c:set var="sectionLessons" value="${lessonsMap[sectionIdStr]}" />
                                                        <c:if test="${not empty sectionLessons}">
                                                            <div style="margin-top: 10px; padding-left: 20px;">
                                                                <div style="font-size: 12px; color: var(--text-muted); margin-bottom: 5px;">
                                                                    <i class="fas fa-book-open"></i> Bài học:
                                                                </div>
                                                                <c:forEach var="lesson" items="${sectionLessons}">
                                                                    <div style="padding: 5px 0; border-left: 2px solid var(--primary-color); padding-left: 10px; margin-bottom: 5px;">
                                                                        <i class="fas fa-video" style="color: var(--primary-color); margin-right: 5px;"></i>
                                                                        <span style="font-size: 13px;">${fn:escapeXml(lesson.name)}</span>
                                                                        <c:if test="${not empty lesson.videoUrl}">
                                                                            <a href="${fn:escapeXml(lesson.videoUrl)}" target="_blank" style="margin-left: 8px; font-size: 11px; color: var(--primary-color);">
                                                                                <i class="fas fa-external-link-alt"></i>
                                                                            </a>
                                                                        </c:if>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${empty sectionLessons}">
                                                            <div style="margin-top: 8px; padding-left: 20px; font-size: 12px; color: var(--text-muted); font-style: italic;">
                                                                <i class="fas fa-info-circle"></i> Chưa có bài học
                                                            </div>
                                                        </c:if>
                                                    </td>
                                                    <td>${fn:escapeXml(section.description)}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty section.courseID}">
                                                                <c:choose>
                                                                    <c:when test="${not empty course}">
                                                                        <span class="badge badge-info">${fn:escapeXml(course.name)}</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-secondary" style="font-size: 11px;">Có courseID</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span style="color: var(--text-muted);">Chưa gán khóa học</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${section.status}">
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
                                                    <td>
                                                        <div class="table-actions" style="display: flex; gap: 10px;">
                                                            <a href="<c:url value='/ManageLesson?action=list&section=${section.sectionID}'/>" 
                                                               class="btn btn-info" style="padding: 5px 10px; font-size: 12px;">
                                                                <i class="fas fa-book-open"></i> Quản lý bài học
                                                            </a>
                                                            <c:choose>
                                                                <c:when test="${not empty courseID}">
                                                                    <a href="<c:url value='/ManageSection?action=edit&id=${section.sectionID}&course=${courseID}'/>" 
                                                                       class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                                        <i class="fas fa-edit"></i> Sửa
                                                                    </a>
                                                                    <a href="<c:url value='/ManageSection?action=delete&id=${section.sectionID}&course=${courseID}'/>" 
                                                                       class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;"
                                                                       onclick="return confirm('Xóa chương này?');">
                                                                        <i class="fas fa-trash"></i> Xóa
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a href="<c:url value='/ManageSection?action=edit&id=${section.sectionID}'/>" 
                                                                       class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                                        <i class="fas fa-edit"></i> Sửa
                                                                    </a>
                                                                    <a href="<c:url value='/ManageSection?action=delete&id=${section.sectionID}'/>" 
                                                                       class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;"
                                                                       onclick="return confirm('Xóa chương này?');">
                                                                        <i class="fas fa-trash"></i> Xóa
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/instructor/js/instructorsHome.js"></script>
    
    <script>
        // Auto show form if editing
        <c:if test="${not empty section}">
            document.getElementById('createSectionForm').style.display = 'block';
            document.getElementById('toggleCreateBtn').innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
        </c:if>

        function toggleCreateSectionForm() {
            const form = document.getElementById('createSectionForm');
            const toggleBtn = document.getElementById('toggleCreateBtn');
            
            if (form.style.display === 'none') {
                form.style.display = 'block';
                toggleBtn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
            } else {
                form.style.display = 'none';
                toggleBtn.innerHTML = '<i class="fas fa-chevron-down"></i> Hiển thị Form';
            }
        }
        
        function cancelForm() {
            // Reset form and hide
            document.getElementById('sectionForm').reset();
            // Clear lessons container
            document.getElementById('lessonsContainer').innerHTML = '';
            lessonIndex = 0;
            toggleCreateSectionForm();
            // Remove edit mode if exists
            <c:if test="${not empty section}">
                <c:choose>
                    <c:when test="${not empty courseID}">
                        window.location.href = '<c:url value="/ManageSection?course=${courseID}"/>';
                    </c:when>
                    <c:otherwise>
                        window.location.href = '<c:url value="/ManageSection"/>';
                    </c:otherwise>
                </c:choose>
            </c:if>
        }
        
        let lessonIndex = 0;
        
        function addLessonRow() {
            const container = document.getElementById('lessonsContainer');
            const lessonRow = document.createElement('div');
            lessonRow.className = 'lesson-row';
            lessonRow.setAttribute('data-lesson-index', lessonIndex);
            
            lessonRow.innerHTML = `
                <div style="background-color: var(--bg-card); padding: 15px; border-radius: 8px; border: 1px solid var(--border-color);">
                    <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 10px;">
                        <h5 style="margin: 0; color: var(--text-dark);">Bài học mới</h5>
                        <button type="button" class="btn btn-danger btn-sm" onclick="removeLessonRow(this)" style="padding: 3px 8px;">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                        <div class="form-group">
                            <label>Tên bài học *</label>
                            <input type="text" name="lessonNames" class="form-control" 
                                   placeholder="Nhập tên bài học" required>
                        </div>
                        <div class="form-group">
                            <label>URL Video *</label>
                            <input type="url" name="lessonUrls" class="form-control" 
                                   placeholder="https://..." required>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label>Mô tả</label>
                        <textarea name="lessonDescriptions" class="form-control" rows="2" 
                                  placeholder="Mô tả bài học..."></textarea>
                    </div>
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-top: 10px;">
                        <div class="form-group">
                            <label>Thời lượng (giây)</label>
                            <input type="number" name="lessonDurations" class="form-control" 
                                   min="0" placeholder="3600">
                        </div>
                        <div class="form-group">
                            <label>Trạng thái</label>
                            <select name="lessonStatuses" class="form-control">
                                <option value="true" selected>Kích hoạt</option>
                                <option value="false">Tắt</option>
                            </select>
                        </div>
                    </div>
                    <input type="hidden" name="lessonIds" value="">
                </div>
            `;
            
            container.appendChild(lessonRow);
            lessonIndex++;
        }
        
        function removeLessonRow(btn) {
            if (confirm('Bạn có chắc chắn muốn xóa bài học này?')) {
                const row = btn.closest('.lesson-row');
                row.remove();
            }
        }
        
        // Initialize lesson index from existing lessons
        <c:if test="${not empty sectionLessons}">
            lessonIndex = ${fn:length(sectionLessons)};
        </c:if>
    </script>
    </body>
</html>

