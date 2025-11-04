
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty sessionScope.user}">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/chatAI.css">

    <!-- Nút mở chat -->
    <div id="chatAIButton" class="chat-ai-button" onclick="toggleChatAI()">
        ...
    </div>

    <!-- Cửa sổ chat -->
    <div id="chatAIWindow" class="chat-ai-window">
        <!-- Header -->
        <div class="chat-header" id="chatHeader">
            <div class="chat-header-info">
                <div class="chat-avatar">
                    <!-- icon -->
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                    <path d="M12 2a10 10 0 100 20 10 10 0 000-20z" fill="currentColor"/>
                    </svg>
                </div>
                <div class="chat-header-text">
                    <h3>Trợ lý AI</h3>
                    <span class="chat-status">Đang trực tuyến</span>
                </div>
            </div>

            <div class="chat-header-actions">
                <!-- Thu nhỏ -->
                <button class="chat-action-btn" type="button" title="Thu nhỏ" onclick="minimizeChat()">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                    <path d="M19 13H5V11H19V13Z" fill="currentColor"/>
                    </svg>
                </button>

                <!-- Đóng (tắt) -->
                <button class="chat-action-btn" type="button" title="Đóng" onclick="closeChatAI()">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                    <path d="M19 6.41L17.59 5L12 10.59L6.41 5L5 6.41L10.59 12L5 17.59L6.41 19L12 13.41L17.59 19L19 17.59L13.41 12L19 6.41Z" fill="currentColor"/>
                    </svg>
                </button>
            </div>
        </div>

        <!-- Vùng tin nhắn -->
        <div class="chat-messages" id="chatMessages">
            <!-- Lời chào hệ thống -->
            <div class="chat-message ai-message">
                <div class="message-avatar"> ... </div>
                <div class="message-content">
                    <p>Xin chào! Tôi là trợ lý AI của E-Learning System. Tôi có thể giúp bạn:</p>
                    <ul>
                        <li>Trả lời câu hỏi về khóa học</li>
                        <li>Giải thích khái niệm lập trình</li>
                        <li>Hỗ trợ học tập và làm bài tập</li>
                        <li>Gợi ý tài liệu học tập</li>
                    </ul>
                    <p>Bạn muốn hỏi gì hôm nay?</p>
                </div>
            </div>

            <!-- Nếu có câu hỏi vừa gửi, hiển thị bubble của người dùng -->
            <c:if test="${not empty param.q}">
                <div class="chat-message user-message">
<div class="message-content">
                        <pre style="white-space:pre-wrap; margin:0;"><c:out value="${param.q}"/></pre>
                    </div>
                </div>
            </c:if>

            <!-- Nếu servlet đặt kết quả, hiển thị bubble trả lời của AI -->
            <c:if test="${not empty ok}">
                <c:choose>
                    <c:when test="${ok}">
                        <div class="chat-message ai-message">
                            <div class="message-avatar"> ... </div>
                            <div class="message-content">
                                <!-- dùng <pre> để giữ xuống dòng; c:out để an toàn -->
                                <pre style="white-space:pre-wrap; margin:0;"><c:out value="${answer}"/></pre>
                                <details style="margin-top:8px;">
                                    <summary>Raw JSON</summary>
                                    <pre style="white-space:pre-wrap; margin:0;"><c:out value="${rawJson}"/></pre>
                                </details>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="chat-message ai-message">
                            <div class="message-avatar"> ... </div>
                            <div class="message-content" style="color:#b00;">
                                <pre style="white-space:pre-wrap; margin:0;"><c:out value="${error}"/></pre>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div><!-- /chat-messages -->

        <!-- Ô nhập + nút gửi (POST) -->
        <form action="${pageContext.request.contextPath}/predict" method="post" accept-charset="UTF-8" autocomplete="off">
            <div class="chat-input-area">
                <div class="chat-input-wrapper">
                    <input type="text" id="chatInput" name="q" class="chat-input"
                           placeholder="Nhập câu hỏi của bạn..." />
                    <button class="chat-send-btn" type="submit" title="Gửi tin nhắn" aria-label="Gửi tin nhắn">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                        <path d="M2.01 21L23 12L2.01 3L2 10L17 12L2 14L2.01 21Z" fill="currentColor"/>
                        </svg>
                    </button>
                </div>
            </div>
        </form>
    </div>

    <script>
    window.CHAT_AI_CONTEXT_PATH = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/learner/js/chatAI.js"></script>
</c:if>