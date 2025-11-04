<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Kiểm tra nếu chưa có dữ liệu và không phải từ servlet, redirect về servlet để load -->
<c:if test="${empty assignment and empty fromServlet and empty error}">
    <c:redirect url="${pageContext.request.contextPath}/ManageQuestion"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Câu hỏi Trắc nghiệm - InstructorsHome</title>
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

            <!-- Assignment Info -->
            <c:if test="${not empty assignment}">
                <section class="section">
                    <div style="background-color: var(--bg-light); padding: 20px; border-radius: 8px; border-left: 4px solid var(--primary-color); margin-bottom: 20px;">
                        <h2><i class="fas fa-tasks"></i> ${fn:escapeXml(assignment.name)}</h2>
                        <p style="color: var(--text-muted); margin: 5px 0;">
                            <c:if test="${not empty assignment.description}">
                                ${fn:escapeXml(assignment.description)}
                            </c:if>
                            <c:if test="${empty assignment.description}">
                                Chưa có mô tả
                            </c:if>
                        </p>
                        <a href="<c:url value='/ManageAssignment'/>" class="btn btn-secondary" style="margin-top: 10px;">
                            <i class="fas fa-arrow-left"></i> Quay lại quản lý bài tập
                        </a>
                    </div>
                </section>
            </c:if>

            <!-- Create Question Section -->
            <section class="section">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h2><i class="fas fa-plus-circle"></i> Thêm Câu hỏi Trắc nghiệm Mới</h2>
                    <button class="btn btn-secondary" onclick="toggleCreateQuestionForm()" id="toggleCreateBtn">
                        <i class="fas fa-chevron-down"></i> Hiển thị Form
                    </button>
                </div>
                
                <!-- Create Question Form -->
                <div id="createQuestionForm" style="display: none; background-color: var(--bg-light); padding: 25px; border-radius: 8px; border-left: 4px solid var(--primary-color);">
                    <form id="questionForm" action="<c:url value='/ManageQuestion'/>" method="post">
                        <input type="hidden" name="action" value="${not empty question ? 'update' : 'create'}">
                        <c:if test="${not empty question}">
                            <input type="hidden" name="questionId" value="${question.id}">
                        </c:if>
                        <input type="hidden" name="assignmentId" value="${assignment.assignmentID}">
                        
                        <div class="form-group">
                            <label for="questionContent">Nội dung câu hỏi *</label>
                            <textarea id="questionContent" name="questionContent" class="form-control" rows="3" 
                                      placeholder="Nhập nội dung câu hỏi trắc nghiệm..." required>${not empty question ? fn:escapeXml(question.content) : ''}</textarea>
                        </div>
                        
                        <div class="form-group" style="margin-top: 20px;">
                            <label><i class="fas fa-list-ul"></i> Các lựa chọn trả lời *</label>
                            <div id="choicesContainer">
                                <c:choose>
                                    <c:when test="${not empty question && not empty question.mcqChoicesCollection}">
                                        <c:forEach var="choice" items="${question.mcqChoicesCollection}" varStatus="status">
                                            <div class="choice-item" style="display: flex; gap: 10px; margin-bottom: 10px; align-items: center;">
                                                <input type="text" name="choice${status.count}" class="form-control" 
                                                       placeholder="Nhập lựa chọn ${status.count}..." 
                                                       value="${fn:escapeXml(choice.content)}" required>
                                                <label style="margin: 0; white-space: nowrap;">
                                                    <input type="checkbox" name="isCorrect${status.count}" value="true" 
                                                           ${choice.isCorrect ? 'checked' : ''}>
                                                    Đáp án đúng
                                                </label>
                                                <button type="button" class="btn btn-danger btn-sm" onclick="removeChoice(this)">
                                                    <i class="fas fa-times"></i>
                                                </button>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="choice-item" style="display: flex; gap: 10px; margin-bottom: 10px; align-items: center;">
                                            <input type="text" name="choice1" class="form-control" placeholder="Nhập lựa chọn 1..." required>
                                            <label style="margin: 0; white-space: nowrap;">
                                                <input type="checkbox" name="isCorrect1" value="true">
                                                Đáp án đúng
                                            </label>
                                            <button type="button" class="btn btn-danger btn-sm" onclick="removeChoice(this)">
                                                <i class="fas fa-times"></i>
                                            </button>
                                        </div>
                                        <div class="choice-item" style="display: flex; gap: 10px; margin-bottom: 10px; align-items: center;">
                                            <input type="text" name="choice2" class="form-control" placeholder="Nhập lựa chọn 2..." required>
                                            <label style="margin: 0; white-space: nowrap;">
                                                <input type="checkbox" name="isCorrect2" value="true">
                                                Đáp án đúng
                                            </label>
                                            <button type="button" class="btn btn-danger btn-sm" onclick="removeChoice(this)">
                                                <i class="fas fa-times"></i>
                                            </button>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <button type="button" class="btn btn-secondary btn-sm" onclick="addChoice()" style="margin-top: 10px;">
                                <i class="fas fa-plus"></i> Thêm lựa chọn
                            </button>
                        </div>
                        
                        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px; border-top: 1px solid var(--border-color); padding-top: 15px;">
                            <c:if test="${not empty question}">
                                <button type="button" class="btn btn-secondary" onclick="cancelEdit()">Hủy</button>
                            </c:if>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save"></i> ${not empty question ? 'Cập nhật' : 'Tạo'} Câu hỏi
                            </button>
                        </div>
                    </form>
                </div>
            </section>

            <!-- Questions List -->
            <section class="section">
                <h2><i class="fas fa-list"></i> Danh sách Câu hỏi</h2>
                <c:choose>
                    <c:when test="${empty questions or fn:length(questions) == 0}">
                        <div style="background-color: var(--bg-light); padding: 40px; text-align: center; border-radius: 8px;">
                            <i class="fas fa-question-circle" style="font-size: 48px; color: var(--text-muted); margin-bottom: 20px;"></i>
                            <p style="color: var(--text-muted); font-size: 16px;">Chưa có câu hỏi nào trong bài tập này</p>
                            <button class="btn btn-primary" onclick="toggleCreateQuestionForm()" style="margin-top: 15px;">
                                <i class="fas fa-plus"></i> Thêm câu hỏi đầu tiên
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="q" items="${questions}" varStatus="qStatus">
                            <div class="card" style="margin-bottom: 20px; border-left: 4px solid var(--primary-color);">
                                <div class="card-body">
                                    <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                        <div style="flex: 1;">
                                            <h4 style="margin: 0 0 10px 0;">
                                                <span style="background-color: var(--primary-color); color: white; padding: 5px 10px; border-radius: 4px; font-size: 14px; margin-right: 10px;">
                                                    Câu ${qStatus.count}
                                                </span>
                                                ${fn:escapeXml(q.content)}
                                            </h4>
                                        </div>
                                        <div style="display: flex; gap: 5px;">
                                            <a href="<c:url value='/ManageQuestion?action=edit&id=${q.id}&assignment=${assignment.assignmentID}'/>" 
                                               class="btn btn-primary btn-sm" style="padding: 5px 10px; font-size: 12px;">
                                                <i class="fas fa-edit"></i> Sửa
                                            </a>
                                            <a href="<c:url value='/ManageQuestion?action=delete&id=${q.id}&assignment=${assignment.assignmentID}'/>" 
                                               class="btn btn-danger btn-sm" style="padding: 5px 10px; font-size: 12px;"
                                               onclick="return confirm('Xóa câu hỏi này?');">
                                                <i class="fas fa-trash"></i> Xóa
                                            </a>
                                        </div>
                                    </div>
                                    
                                    <div style="padding-left: 20px;">
                                        <p style="font-weight: 600; margin-bottom: 10px; color: var(--text-dark);">Các lựa chọn:</p>
                                        <c:choose>
                                            <c:when test="${not empty q.mcqChoicesCollection and fn:length(q.mcqChoicesCollection) > 0}">
                                                <ul style="list-style: none; padding: 0; margin: 0;">
                                                    <c:forEach var="choice" items="${q.mcqChoicesCollection}" varStatus="cStatus">
                                                        <li style="padding: 8px; margin-bottom: 5px; background-color: ${choice.isCorrect ? '#d4edda' : '#f8f9fa'}; border-left: 3px solid ${choice.isCorrect ? '#28a745' : '#dee2e6'}; border-radius: 4px;">
                                                            <span style="font-weight: 600; margin-right: 10px;">${cStatus.index + 1}.</span>
                                                            ${fn:escapeXml(choice.content)}
                                                            <c:if test="${choice.isCorrect}">
                                                                <span class="badge badge-success" style="margin-left: 10px; padding: 3px 8px; font-size: 11px;">
                                                                    <i class="fas fa-check"></i> Đáp án đúng
                                                                </span>
                                                            </c:if>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </c:when>
                                            <c:otherwise>
                                                <p style="color: var(--text-muted); font-style: italic;">Chưa có lựa chọn</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/instructor/js/instructorsHome.js"></script>
    
    <script>
        let choiceCount = ${not empty question && not empty question.mcqChoicesCollection ? fn:length(question.mcqChoicesCollection) : 2};
        
        function toggleCreateQuestionForm() {
            const form = document.getElementById('createQuestionForm');
            const btn = document.getElementById('toggleCreateBtn');
            if (form.style.display === 'none') {
                form.style.display = 'block';
                btn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
            } else {
                form.style.display = 'none';
                btn.innerHTML = '<i class="fas fa-chevron-down"></i> Hiển thị Form';
            }
        }
        
        function addChoice() {
            choiceCount++;
            const container = document.getElementById('choicesContainer');
            const choiceDiv = document.createElement('div');
            choiceDiv.className = 'choice-item';
            choiceDiv.style.cssText = 'display: flex; gap: 10px; margin-bottom: 10px; align-items: center;';
            choiceDiv.innerHTML = `
                <input type="text" name="choice${choiceCount}" class="form-control" placeholder="Nhập lựa chọn ${choiceCount}..." required>
                <label style="margin: 0; white-space: nowrap;">
                    <input type="checkbox" name="isCorrect${choiceCount}" value="true">
                    Đáp án đúng
                </label>
                <button type="button" class="btn btn-danger btn-sm" onclick="removeChoice(this)">
                    <i class="fas fa-times"></i>
                </button>
            `;
            container.appendChild(choiceDiv);
        }
        
        function removeChoice(btn) {
            const container = document.getElementById('choicesContainer');
            const items = container.querySelectorAll('.choice-item');
            if (items.length > 1) {
                btn.closest('.choice-item').remove();
            } else {
                alert('Cần ít nhất 1 lựa chọn!');
            }
        }
        
        function cancelEdit() {
            window.location.href = '${pageContext.request.contextPath}/ManageQuestion?action=list&assignment=${assignment.assignmentID}';
        }
        
        // Tự động hiển thị form khi có question để edit
        <c:if test="${not empty question and isEdit}">
            document.addEventListener('DOMContentLoaded', function() {
                toggleCreateQuestionForm();
                document.getElementById('questionContent').focus();
            });
        </c:if>
        
        // Tự động hiển thị form khi isCreate = true
        <c:if test="${isCreate}">
            document.addEventListener('DOMContentLoaded', function() {
                toggleCreateQuestionForm();
                document.getElementById('questionContent').focus();
            });
        </c:if>
    </script>
</body>
</html>

