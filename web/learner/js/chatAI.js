// Chat AI JavaScript
let chatWindow = null;
let chatButton = null;
let chatHeader = null;
let chatMessages = null;
let chatInput = null;
let isDragging = false;
let currentX = 0;
let currentY = 0;
let initialX = 0;
let initialY = 0;
let xOffset = 0;
let yOffset = 0;

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    chatWindow = document.getElementById('chatAIWindow');
    chatButton = document.getElementById('chatAIButton');
    chatHeader = document.getElementById('chatHeader');
    chatMessages = document.getElementById('chatMessages');
    chatInput = document.getElementById('chatInput');
    
    if (chatHeader) {
        // Make chat window draggable
        chatHeader.addEventListener('mousedown', dragStart);
        document.addEventListener('mousemove', drag);
        document.addEventListener('mouseup', dragEnd);
        
        // Touch events for mobile
        chatHeader.addEventListener('touchstart', dragStartTouch);
        document.addEventListener('touchmove', dragTouch);
        document.addEventListener('touchend', dragEnd);
    }
    
    // Initialize chat position from localStorage
    loadChatPosition();
});

// Toggle chat window
function toggleChatAI() {
    if (!chatWindow) return;
    
    if (chatWindow.classList.contains('active')) {
        chatWindow.classList.remove('active');
        if (chatButton) {
            chatButton.style.display = 'flex';
        }
    } else {
        chatWindow.classList.add('active');
        if (chatButton) {
            chatButton.style.display = 'none';
        }
        // Focus on input when opening
        if (chatInput) {
            setTimeout(() => chatInput.focus(), 100);
        }
        // Hide notification badge
        const badge = document.getElementById('chatNotificationBadge');
        if (badge) {
            badge.style.display = 'none';
        }
    }
}

// Minimize chat window
function minimizeChat() {
    if (!chatWindow) return;
    chatWindow.classList.toggle('minimized');
}

// Drag functionality (Mouse)
function dragStart(e) {
    if (!chatWindow) return;
    
    // Only allow dragging by the header
    if (e.target.closest('.chat-action-btn')) {
        return;
    }
    
    initialX = e.clientX - xOffset;
    initialY = e.clientY - yOffset;
    
    if (e.target === chatHeader || chatHeader.contains(e.target)) {
        isDragging = true;
        chatHeader.style.cursor = 'grabbing';
    }
}

function drag(e) {
    if (isDragging && chatWindow) {
        e.preventDefault();
        
        currentX = e.clientX - initialX;
        currentY = e.clientY - initialY;
        
        xOffset = currentX;
        yOffset = currentY;
        
        setTranslate(currentX, currentY, chatWindow);
        
        // Save position to localStorage
        saveChatPosition(currentX, currentY);
    }
}

function dragEnd(e) {
    if (chatHeader) {
        chatHeader.style.cursor = 'move';
    }
    
    initialX = currentX;
    initialY = currentY;
    
    isDragging = false;
}

// Drag functionality (Touch)
function dragStartTouch(e) {
    if (!chatWindow) return;
    
    if (e.target.closest('.chat-action-btn')) {
        return;
    }
    
    const touch = e.touches[0];
    initialX = touch.clientX - xOffset;
    initialY = touch.clientY - yOffset;
    
    if (e.target === chatHeader || chatHeader.contains(e.target)) {
        isDragging = true;
    }
}

function dragTouch(e) {
    if (isDragging && chatWindow) {
        e.preventDefault();
        
        const touch = e.touches[0];
        currentX = touch.clientX - initialX;
        currentY = touch.clientY - initialY;
        
        xOffset = currentX;
        yOffset = currentY;
        
        setTranslate(currentX, currentY, chatWindow);
        
        // Save position to localStorage
        saveChatPosition(currentX, currentY);
    }
}

// Set translate position
function setTranslate(xPos, yPos, el) {
    el.style.transform = `translate(${xPos}px, ${yPos}px)`;
    el.style.bottom = 'auto';
    el.style.right = 'auto';
    el.style.left = 'auto';
    el.style.top = 'auto';
}

// Save chat position to localStorage
function saveChatPosition(x, y) {
    try {
        localStorage.setItem('chatAI_position', JSON.stringify({ x: x, y: y }));
    } catch (e) {
        console.error('Error saving chat position:', e);
    }
}

// Load chat position from localStorage
function loadChatPosition() {
    if (!chatWindow) return;
    
    try {
        const savedPosition = localStorage.getItem('chatAI_position');
        if (savedPosition) {
            const position = JSON.parse(savedPosition);
            xOffset = position.x;
            yOffset = position.y;
            setTranslate(position.x, position.y, chatWindow);
        }
    } catch (e) {
        console.error('Error loading chat position:', e);
    }
}

// Handle Enter key press in chat input
function handleChatKeyPress(event) {
    if (event.key === 'Enter' && !event.shiftKey) {
        event.preventDefault();
        sendMessage();
    }
}

// Send message
function sendMessage() {
    if (!chatInput || !chatMessages) return;
    
    const message = chatInput.value.trim();
    if (!message) return;
    
    // Add user message
    addMessage(message, 'user');
    
    // Clear input
    chatInput.value = '';
    
    // Show typing indicator
    showTypingIndicator();
    
    // Send to AI (simulate API call)
    setTimeout(() => {
        hideTypingIndicator();
        getAIResponse(message);
    }, 1000);
}

// Add message to chat
function addMessage(text, sender) {
    if (!chatMessages) return;
    
    const messageDiv = document.createElement('div');
    messageDiv.className = `chat-message ${sender}-message`;
    
    const avatarDiv = document.createElement('div');
    avatarDiv.className = 'message-avatar';
    
    if (sender === 'ai') {
        avatarDiv.innerHTML = `
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM13 17H11V15H13V17ZM13 13H11V7H13V13Z" fill="currentColor"/>
            </svg>
        `;
    } else {
        avatarDiv.innerHTML = `
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                <path d="M12 12C14.21 12 16 10.21 16 8C16 5.79 14.21 4 12 4C9.79 4 8 5.79 8 8C8 10.21 9.79 12 12 12ZM12 14C9.33 14 4 15.34 4 18V20H20V18C20 15.34 14.67 14 12 14Z" fill="currentColor"/>
            </svg>
        `;
    }
    
    const contentDiv = document.createElement('div');
    contentDiv.className = 'message-content';
    
    // Parse message text (support basic formatting)
    contentDiv.innerHTML = formatMessage(text);
    
    messageDiv.appendChild(avatarDiv);
    messageDiv.appendChild(contentDiv);
    
    chatMessages.appendChild(messageDiv);
    
    // Scroll to bottom
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

// Format message text (basic formatting)
function formatMessage(text) {
    // Escape HTML
    text = text.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    
    // Convert line breaks
    text = text.replace(/\n/g, '<br>');
    
    // Convert URLs to links
    const urlRegex = /(https?:\/\/[^\s]+)/g;
    text = text.replace(urlRegex, '<a href="$1" target="_blank" style="color: inherit; text-decoration: underline;">$1</a>');
    
    return text;
}

// Show typing indicator
function showTypingIndicator() {
    if (!chatMessages) return;
    
    const typingDiv = document.createElement('div');
    typingDiv.className = 'chat-message ai-message';
    typingDiv.id = 'typingIndicator';
    
    const avatarDiv = document.createElement('div');
    avatarDiv.className = 'message-avatar';
    avatarDiv.innerHTML = `
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
            <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM13 17H11V15H13V17ZM13 13H11V7H13V13Z" fill="currentColor"/>
        </svg>
    `;
    
    const contentDiv = document.createElement('div');
    contentDiv.className = 'message-content typing-indicator';
    contentDiv.innerHTML = '<span></span><span></span><span></span>';
    
    typingDiv.appendChild(avatarDiv);
    typingDiv.appendChild(contentDiv);
    
    chatMessages.appendChild(typingDiv);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

// Hide typing indicator
function hideTypingIndicator() {
    const typingIndicator = document.getElementById('typingIndicator');
    if (typingIndicator) {
        typingIndicator.remove();
    }
}

// Get AI response
function getAIResponse(userMessage) {
    // Get context path from global variable set by JSP, or use current path
    const contextPath = window.CHAT_AI_CONTEXT_PATH || '';
    
    // Prepare request
    fetch(contextPath + '/ai/chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            message: userMessage
        }),
        credentials: 'same-origin' // Include session cookies
    })
    .then(response => {
        if (response.status === 401) {
            // User not logged in
            addMessage('Bạn cần đăng nhập để sử dụng tính năng này.', 'ai');
            return null;
        }
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (!data) return; // Skip if 401 error
        
        // Display AI response
        if (data.response) {
            addMessage(data.response, 'ai');
        } else if (data.error) {
            addMessage(data.error, 'ai');
        } else {
            addMessage('Xin lỗi, tôi không thể xử lý yêu cầu này lúc này. Vui lòng thử lại sau.', 'ai');
        }
    })
    .catch(error => {
        console.error('Error calling AI API:', error);
        // Fallback response
        addMessage(getFallbackResponse(userMessage), 'ai');
    });
}

// Fallback response when API fails
function getFallbackResponse(message) {
    const lowerMessage = message.toLowerCase();
    
    // Simple keyword matching for demo
    if (lowerMessage.includes('khóa học') || lowerMessage.includes('course')) {
        return 'Bạn có thể tìm kiếm khóa học trong mục "Khóa học Pro" trên trang chủ. Tôi có thể giúp bạn tìm hiểu thêm về bất kỳ khóa học nào.';
    } else if (lowerMessage.includes('bài viết') || lowerMessage.includes('article')) {
        return 'Bạn có thể xem các bài viết nổi bật trong mục "Bài viết nổi bật". Có điều gì về bài viết mà bạn muốn tìm hiểu không?';
    } else if (lowerMessage.includes('giúp') || lowerMessage.includes('help')) {
        return 'Tôi có thể giúp bạn:\n- Tìm hiểu về khóa học\n- Giải thích khái niệm lập trình\n- Hỗ trợ học tập\nBạn muốn hỏi gì cụ thể?';
    } else {
        return 'Cảm ơn bạn đã hỏi! Hiện tại tôi đang học hỏi để trả lời tốt hơn. Bạn có thể thử hỏi về khóa học, bài viết, hoặc các khái niệm lập trình.';
    }
}

// Reset chat position (utility function)
function resetChatPosition() {
    if (!chatWindow) return;
    
    try {
        localStorage.removeItem('chatAI_position');
        xOffset = 0;
        yOffset = 0;
        chatWindow.style.transform = '';
        chatWindow.style.bottom = '100px';
        chatWindow.style.right = '30px';
        chatWindow.style.left = 'auto';
        chatWindow.style.top = 'auto';
    } catch (e) {
        console.error('Error resetting chat position:', e);
    }
}

