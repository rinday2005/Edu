<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Kiểm tra nếu chưa có dữ liệu và không phải từ servlet, redirect về servlet để load -->
<c:if test="${empty assignments and empty fromServlet and empty error}">
    <c:redirect url="${pageContext.request.contextPath}/ManageAssignment"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Bài tập - InstructorsHome</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
    <div class="main-container" data-context-path="${pageContext.request.contextPath}" data-has-assignment="${not empty assignment ? 'true' : 'false'}">
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

            <!-- Create Assignment Section -->
            <section class="section">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h2><i class="fas fa-plus-circle"></i> Thêm Bài tập Mới</h2>
                    <button class="btn btn-secondary" onclick="toggleCreateAssignmentForm()" id="toggleCreateBtn">
                        <i class="fas fa-chevron-down"></i> Hiển thị Form
                    </button>
                </div>
                
                <!-- Create Assignment Form -->
                <div id="createAssignmentForm" style="display: none; background-color: var(--bg-light); padding: 25px; border-radius: 8px; border-left: 4px solid var(--primary-color);">
                    <form id="assignmentForm" action="<c:url value='/ManageAssignment'/>" method="post">
                        <input type="hidden" name="action" value="${not empty assignment ? 'update' : 'create'}">
                        <c:if test="${not empty assignment}">
                            <input type="hidden" name="id" value="${assignment.assignmentID}">
                        </c:if>
                        
                        <div style="display: flex; gap: 20px; margin-bottom: 20px;">
                            <div style="flex: 1;">
                                <span class="step-badge" style="display: inline-block; width: 40px; height: 40px; background-color: var(--primary-color); color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold; margin-bottom: 10px;">1</span>
                                <h4 style="margin: 0 0 10px 0; color: var(--text-dark);">Chọn Chương và Phần</h4>
                                <div class="form-group" style="margin-bottom:12px;">
                                    <label for="courseSelect">Chương (Course)</label>
                                    <select id="courseSelect" class="form-control">
                                        <option value="">-- Chọn khóa học --</option>
                                        <c:forEach var="course" items="${courses}">
                                            <option value="${course.courseID}">${fn:escapeXml(course.name)}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group" style="margin-bottom:12px;">
                                    <label for="sectionSelect">Phần (Section) *</label>
                                    <select id="sectionSelect" name="sectionID" class="form-control" required>
                                        <option value="">-- Chọn phần --</option>
                                        <c:forEach var="section" items="${sections}">
                                            <option value="${section.sectionID}" data-course-id="${section.courseID}"
                                                    ${not empty assignment && assignment.sectionID != null && assignment.sectionID.equals(section.sectionID) ? 'selected' : ''}>
                                                ${section.name != null ? section.name : 'Section ' + section.sectionID}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="lessionSelect">Bài học (Lesson) - Tùy chọn</label>
                                    <select id="lessionSelect" name="lessionID" class="form-control">
                                        <option value="">-- Chọn bài học (để test hiển thị trong lesson) --</option>
                                        <c:forEach var="lesson" items="${lessons}">
                                            <option value="${lesson.lessionID}" data-section-id="${lesson.sectionID}"
                                                    ${not empty assignment && assignment.lessionID != null && assignment.lessionID.equals(lesson.lessionID) ? 'selected' : ''}>
                                                ${lesson.name != null ? lesson.name : 'Lesson ' + lesson.lessionID}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <small style="color: #666; font-size: 12px;">Nếu chọn bài học, test sẽ hiển thị trong lesson đó</small>
                                </div>
                            </div>
                            
                            <div style="flex: 1;">
                                <span class="step-badge" style="display: inline-block; width: 40px; height: 40px; background-color: var(--primary-color); color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold; margin-bottom: 10px;">2</span>
                                <h4 style="margin: 0 0 10px 0; color: var(--text-dark);">Thông tin Bài tập</h4>
                                <div class="form-group">
                                    <label for="assignmentTitle">Tên bài tập *</label>
                                    <input type="text" id="assignmentTitle" name="assignmentName" class="form-control" 
                                           placeholder="Ví dụ: Tạo Component đầu tiên" 
                                           value="${not empty assignment ? fn:escapeXml(assignment.name) : ''}" required>
                                </div>
                                <div class="form-group">
                                    <label for="assignmentDesc">Mô tả bài tập</label>
                                    <textarea id="assignmentDesc" name="assignmentDescription" class="form-control" rows="3" 
                                              placeholder="Nhập mô tả bài tập..." style="resize: vertical;">${not empty assignment ? fn:escapeXml(assignment.description) : ''}</textarea>
                                </div>
                                <div class="form-group">
                                    <label for="assignmentOrder">Thứ tự bài tập</label>
                                    <input type="number" id="assignmentOrder" name="assignmentOrder" class="form-control" 
                                           placeholder="Ví dụ: 1" min="1" 
                                           value="${not empty assignment ? assignment.order : 1}">
                                </div>
                            </div>
                        </div>
                        
                        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px; border-top: 1px solid var(--border-color); padding-top: 15px;">
                            <button type="button" class="btn btn-secondary" onclick="cancelForm()">
                                <i class="fas fa-times"></i> Hủy
                            </button>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-check"></i> ${not empty assignment ? 'Cập nhật' : 'Tạo'} Bài tập
                            </button>
                        </div>
                    </form>
                </div>
            </section>

            <!-- Assignment List -->
            <section class="section">
                <h2><i class="fas fa-tasks"></i> Danh sách Bài tập</h2>
                
                <c:if test="${not empty assignments}">
                    <p style="color: green; margin-bottom: 10px;">
                        <i class="fas fa-check-circle"></i> Tìm thấy ${fn:length(assignments)} bài tập
                    </p>
                </c:if>
                <c:if test="${empty assignments}">
                    <p style="color: orange; margin-bottom: 10px;">
                        <i class="fas fa-exclamation-triangle"></i> Chưa có bài tập nào. Hãy tạo bài tập mới!
                    </p>
                </c:if>

                <div class="card">
                    <div class="card-header">
                        Tất cả bài tập của bạn
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${empty assignments}">
                                <div style="text-align: center; padding: 40px; color: var(--text-muted);">
                                    <i class="fas fa-inbox" style="font-size: 48px; margin-bottom: 15px; display: block;"></i>
                                    <p style="font-size: 16px; margin: 0;">Chưa có bài tập nào. Hãy tạo bài tập mới!</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th><i class="fas fa-heading"></i> Tên bài tập</th>
                                                <th><i class="fas fa-align-left"></i> Mô tả</th>
                                                <th><i class="fas fa-sort-numeric-down"></i> Thứ tự</th>
                                                <th><i class="fas fa-layer-group"></i> Section ID</th>
                                                <th><i class="fas fa-cogs"></i> Thao tác</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="assignment" items="${assignments}">
                                                <tr>
                                                    <td><strong>${fn:escapeXml(assignment.name)}</strong></td>
                                                    <td>${fn:escapeXml(assignment.description)}</td>
                                                    <td>${assignment.order}</td>
                                                    <td>${assignment.sectionID}</td>
                                                    <td>
                                                        <div class="table-actions" style="display: flex; gap: 10px; flex-wrap: wrap;">
                                                            <a href="<c:url value='/ManageQuestion?action=list&assignment=${assignment.assignmentID}'/>" 
                                                               class="btn btn-info" style="padding: 5px 10px; font-size: 12px;">
                                                                <i class="fas fa-question-circle"></i> Quản lý câu hỏi
                                                            </a>
                                                            <a href="<c:url value='/ManageAssignment?action=edit&id=${assignment.assignmentID}'/>" 
                                                               class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                                                <i class="fas fa-edit"></i> Sửa
                                                            </a>
                                                            <a href="<c:url value='/ManageAssignment?action=delete&id=${assignment.assignmentID}'/>" 
                                                               class="btn btn-danger" style="padding: 5px 10px; font-size: 12px;"
                                                               onclick="return confirm('Xóa bài tập này?');">
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
                    </div>
                </div>
            </section>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/instructor/js/instructorsHome.js"></script>
    <script src="${pageContext.request.contextPath}/instructor/js/AssignmentDetails.js"></script>
    </body>
</html>
