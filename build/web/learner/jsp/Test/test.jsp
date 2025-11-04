<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${empty assignment and empty fromServlet and empty error}">
    <c:redirect url="${pageContext.request.contextPath}/learner/jsp/Course/listCourse.jsp"/>
</c:if>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${assignment != null ? assignment.name : 'Bài Test'} - Learner</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/test.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <div class="main-container">
            <!-- Header -->
            <jsp:include page="/learner/common/header.jsp" />
            
            <!-- Sidebar -->
            <jsp:include page="/learner/common/sidebar.jsp" />

            <main class="main-content">
                <div class="test-container">
                    <!-- Messages -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-error">
                            <i class="fas fa-exclamation-circle"></i> ${error}
                        </div>
                    </c:if>

                    <c:if test="${not empty assignment}">
                        <!-- Test Header -->
                        <div class="test-header">
                            <h1 class="test-title">${fn:escapeXml(assignment.name)}</h1>
                            <c:if test="${not empty assignment.description}">
                                <p class="test-description">${fn:escapeXml(assignment.description)}</p>
                            </c:if>
                            <c:if test="${showResults}">
                                <div class="test-results">
                                    <div class="score-box">
                                        <div class="score-value">${score}</div>
                                        <div class="score-label">Điểm</div>
                                    </div>
                                    <div class="result-details">
                                        <p><strong>${correctCount}</strong> / <strong>${totalQuestions}</strong> câu đúng</p>
                                    </div>
                                </div>
                            </c:if>
                        </div>

                        <!-- Questions -->
                        <c:choose>
                            <c:when test="${not empty questions}">
                                <form id="testForm" method="post" action="${pageContext.request.contextPath}/learner/test">
                                    <input type="hidden" name="assignment" value="${assignment.assignmentID}">
                                    
                                    <div class="questions-list">
                                        <c:forEach var="question" items="${questions}" varStatus="status">
                                            <div class="question-item">
                                                <div class="question-header">
                                                    <span class="question-number">Câu ${status.count}:</span>
                                                    <span class="question-text">${fn:escapeXml(question.getContent())}</span>
                                                </div>
                                                
                                                <div class="choices-list">
                                                    <c:forEach var="choice" items="${question.mcqChoicesCollection}">
                                                        <c:set var="isCorrect" value="${choice.isIsCorrect()}" />
                                                        <label class="choice-item ${showResults && userAnswers[question.id] == choice.id ? (isCorrect ? 'correct' : 'incorrect') : ''} ${showResults && isCorrect && userAnswers[question.id] != choice.id ? 'should-be-selected' : ''}">
                                                            <input type="radio" 
                                                                   name="question_${question.id}" 
                                                                   value="${choice.id}"
                                                                   ${showResults && userAnswers[question.id] == choice.id ? 'checked' : ''}
                                                                   ${showResults ? 'disabled' : ''}>
                                                            <span class="choice-text">${fn:escapeXml(choice.content)}</span>
                                                            <c:if test="${showResults && isCorrect}">
                                                                <span class="correct-badge">✓ Đáp án đúng</span>
                                                            </c:if>
                                                            <c:if test="${showResults && userAnswers[question.id] == choice.id && !isCorrect}">
                                                                <span class="incorrect-badge">✗ Sai</span>
                                                            </c:if>
                                                        </label>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <div class="test-actions">
                                        <c:if test="${!showResults}">
                                            <button type="submit" class="btn-submit">
                                                <i class="fas fa-paper-plane"></i> Nộp bài
                                            </button>
                                        </c:if>
                                        <c:if test="${showResults}">
                                            <a href="${pageContext.request.contextPath}/learner/jsp/Course/listCourse.jsp" class="btn-back">
                                                <i class="fas fa-arrow-left"></i> Quay lại
                                            </a>
                                        </c:if>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="empty-state">
                                    <i class="fas fa-inbox"></i>
                                    <p>Chưa có câu hỏi nào trong bài test này.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </main>
        </div>

        <script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
    </body>
</html>

