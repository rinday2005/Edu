<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Chat AI Component - chỉ hiển thị khi đã đăng nhập -->
<c:if test="${not empty sessionScope.user}">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/chatAI.css">
    
    <!-- Chat Icon Button (Floating) -->
    <div id="chatAIButton" class="chat-ai-button" onclick="toggleChatAI()">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M20 2H4C2.9 2 2 2.9 2 4V22L6 18H20C21.1 18 22 17.1 22 16V4C22 2.9 21.1 2 20 2ZM20 16H6L4 18V4H20V16Z" fill="currentColor"/>
            <path d="M7 9H17V11H7V9ZM7 12H14V14H7V12Z" fill="currentColor"/>
        </svg>
        <span class="chat-notification-badge" id="chatNotificationBadge" style="display: none;">1</span>
    </div>

    <!-- Chat Window -->
    <div id="chatAIWindow" class="chat-ai-window">
        <!-- Chat Header (Draggable Area) -->
        <div class="chat-header" id="chatHeader">
            <div class="chat-header-info">
                <div class="chat-avatar">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM13 17H11V15H13V17ZM13 13H11V7H13V13Z" fill="currentColor"/>
                    </svg>
                </div>
                <div class="chat-header-text">
                    <h3>Trợ lý AI</h3>
                    <span class="chat-status">Đang trực tuyến</span>
                </div>
            </div>
            <div class="chat-header-actions">
                <button class="chat-action-btn" onclick="minimizeChat()" title="Thu nhỏ">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M19 13H5V11H19V13Z" fill="currentColor"/>
                    </svg>
                </button>
                <button class="chat-action-btn" onclick="toggleChatAI()" title="Đóng">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M19 6.41L17.59 5L12 10.59L6.41 5L5 6.41L10.59 12L5 17.59L6.41 19L12 13.41L17.59 19L19 17.59L13.41 12L19 6.41Z" fill="currentColor"/>
                    </svg>
                </button>
            </div>
        </div>

        <!-- Chat Messages Area -->
        <div class="chat-messages" id="chatMessages">
            <div class="chat-message ai-message">
                <div class="message-avatar">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM13 17H11V15H13V17ZM13 13H11V7H13V13Z" fill="currentColor"/>
                    </svg>
                </div>
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
        </div>

        <!-- Chat Input Area -->
        <div class="chat-input-area">
            <div class="chat-input-wrapper">
                <input 
                    type="text" 
                    id="chatInput" 
                    class="chat-input" 
                    placeholder="Nhập câu hỏi của bạn..." 
                    onkeypress="handleChatKeyPress(event)"
                />
                <button class="chat-send-btn" onclick="sendMessage()" title="Gửi tin nhắn">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                        <path d="M2.01 21L23 12L2.01 3L2 10L17 12L2 14L2.01 21Z" fill="currentColor"/>
                    </svg>
                </button>
            </div>
        </div>
    </div>

    <script>
        // Set context path for JavaScript
        window.CHAT_AI_CONTEXT_PATH = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/learner/js/chatAI.js"></script>
</c:if>
