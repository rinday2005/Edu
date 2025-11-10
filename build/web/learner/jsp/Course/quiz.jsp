<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>${quizAssignment.name}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/course.css">
</head>
<body>
  <div class="quiz-area">
    <!-- Hiển thị thông báo lỗi -->
    <c:if test="${not empty error}">
      <div style="background: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #f5c6cb;">
        <strong>⚠️ Lỗi:</strong> ${error}
      </div>
      <div style="text-align:center; margin-top: 20px;">
        <a class="btn-back" href="${pageContext.request.contextPath}/room?courseID=${courseID}">
          ← Quay lại khóa học
        </a>
      </div>
    </c:if>
    
    <!-- Chỉ hiển thị nội dung quiz khi không có lỗi -->
    <c:if test="${empty error and not empty quizAssignment}">
    <h3 class="quiz-title">${quizAssignment.name}</h3>
    <p class="quiz-description">${quizAssignment.description}</p>

    <!-- FORM LÀM BÀI -->
    <c:if test="${!isReview}">
      <form action="${pageContext.request.contextPath}/submitQuiz" method="post">
        <input type="hidden" name="assignmentID" value="${quizAssignment.assignmentID}" />
        <input type="hidden" name="sectionID" value="${quizAssignment.sectionID}" />
        <input type="hidden" name="courseID" value="${courseID}" />
        <input type="hidden" name="userID" value="${sessionScope.user.userID}" />

        <c:forEach var="q" items="${quizAssignment.questions}" varStatus="st">
          <div class="question-item">
            <p>${st.index + 1}. ${q.content}</p>
            <ul class="choice-list">
              <c:forEach var="c" items="${q.mcqChoicesCollection}">
                <li>
                  <label>
                    <input type="radio" name="q_${q.id}" value="${c.id}" />
                    ${c.content}
                  </label>
                </li>
              </c:forEach>
            </ul>
          </div>
        </c:forEach>

        <button type="submit" class="btn-submit">🚀 Nộp bài</button>
      </form>
    </c:if>

    <!-- FORM XEM KẾT QUẢ -->
    <c:if test="${isReview}">
      <c:forEach var="q" items="${quizAssignment.questions}" varStatus="st">
        <div class="question-item">
          <p>${st.index + 1}. ${q.content}</p>
          <ul class="choice-list">
            <c:forEach var="c" items="${q.mcqChoicesCollection}">
              <li>
                <label>
                  <input type="radio" disabled="disabled"
                         <c:if test="${selectedChoiceIds.contains(c.id)}">checked</c:if> />
                  <span class="<c:choose>
                                 <c:when test='${selectedChoiceIds.contains(c.id) && c.isCorrect}'>correct</c:when>
                                 <c:when test='${selectedChoiceIds.contains(c.id) && !c.isCorrect}'>wrong</c:when>
                                 <c:when test='${c.isCorrect}'>correct</c:when>
                               </c:choose>">
                    ${c.content}
                  </span>
                </label>
              </li>
            </c:forEach>
          </ul>
        </div>
      </c:forEach>

      <div class="score-box">
        ✅ Bạn trả lời đúng <b>${correctCount}</b>/<b>${totalQuestions}</b> câu.<br/>
        Điểm số: <b>${percentScore}%</b>
      </div>
      <div class="progress">
        <div class="progress-bar" style="width: ${percentScore}%;"></div>
      </div>

      <!-- FORM RESET RIÊNG BIỆT -->
      <form action="${pageContext.request.contextPath}/resetQuiz" method="post" style="text-align:center;">
        <input type="hidden" name="assignmentID" value="${quizAssignment.assignmentID}" />
        <input type="hidden" name="sectionID" value="${quizAssignment.sectionID}" />
        <input type="hidden" name="courseID" value="${courseID}" />
        <input type="hidden" name="userID" value="${sessionScope.user.userID}" />
        <button type="submit" class="btn-reset">🔁 Làm lại bài kiểm tra</button>
      </form>

      <div style="text-align:center;">
        <a class="btn-back" href="${pageContext.request.contextPath}/room?courseID=${courseID}">
          ← Quay lại khóa học
        </a>
      </div>
    </c:if>
    </c:if>
  </div>
</body>
</html>
