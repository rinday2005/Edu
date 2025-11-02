const CHAT_ENDPOINT = `${window.CHAT_AI_CONTEXT_PATH}/api/chat-ai`;

let chatOpen = false, chatMinimized = false;
const chatBtn = document.getElementById("chatAIButton");
const chatWin = document.getElementById("chatAIWindow");
const chatHdr = document.getElementById("chatHeader");
const chatMsgs = document.getElementById("chatMessages");
const chatInput = document.getElementById("chatInput");
const badge = document.getElementById("chatNotificationBadge");

function toggleChatAI() {
    chatOpen = !chatOpen;
    chatWin.style.display = chatOpen ? "flex" : "none";
    if (chatOpen) {
        badge.style.display = "none";
        chatInput.focus();
    }
}
function minimizeChat() {
    chatMinimized = !chatMinimized;
    chatWin.classList.toggle("minimized", chatMinimized);
}
function handleChatKeyPress(e) {
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
    }
}

// drag
(function () {
    let down = false, offX = 0, offY = 0;
    chatHdr.style.cursor = "move";
    chatHdr.addEventListener("mousedown", e => {
        down = true;
        const r = chatWin.getBoundingClientRect();
        offX = e.clientX - r.left;
        offY = e.clientY - r.top;
        chatHdr.style.userSelect = "none";
    });
    document.addEventListener("mouseup", () => {
        down = false;
        chatHdr.style.userSelect = "";
    });
    document.addEventListener("mousemove", e => {
        if (!down)
            return;
        chatWin.style.left = (e.clientX - offX) + "px";
        chatWin.style.top = (e.clientY - offY) + "px";
        chatWin.style.right = "auto";
        chatWin.style.bottom = "auto";
        chatWin.style.position = "fixed";
    });
})();

function escapeHtml(s) {
    return s.replace(/[&<>"']/g, m => ({'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'}[m]));
}
function appendMsg(text, who = "user") {
    const d = document.createElement("div");
    d.className = `chat-message ${who === "user" ? "user-message" : "ai-message"}`;
    d.innerHTML = `<div class="message-content"><p>${escapeHtml(text)}</p></div>`;
    chatMsgs.appendChild(d);
    chatMsgs.scrollTop = chatMsgs.scrollHeight;
}
function setTyping(on) {
    let t = document.getElementById("aiTyping");
    if (on) {
        if (!t) {
            t = document.createElement("div");
            t.id = "aiTyping";
            t.className = "chat-message ai-message typing";
            t.innerHTML = `<div class="message-content"><em>AI đang trả lời…</em></div>`;
            chatMsgs.appendChild(t);
        }
    } else {
        t && t.remove();
    }
    chatMsgs.scrollTop = chatMsgs.scrollHeight;
}

async function sendMessage() {
    const text = chatInput.value.trim();
    if (!text)
        return;
    appendMsg(text, "user");
    chatInput.value = "";
    setTyping(true);
    try {
        const res = await fetch(CHAT_ENDPOINT, {method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify({message: text})});
        if (!res.ok) {
            const tx = await res.text().catch(() => "");
            throw new Error(`HTTP ${res.status} ${tx}`);
        }
        const data = await res.json();
        setTyping(false);
        appendMsg(data.answer || "(Không có nội dung)", "ai");
    } catch (err) {
        setTyping(false);
        appendMsg("Xin lỗi, có lỗi kết nối. Vui lòng thử lại.", "ai");
        console.error(err);
        if (!chatOpen)
            badge.style.display = "inline-block";
    }
}
window.toggleChatAI = toggleChatAI;
window.minimizeChat = minimizeChat;
window.handleChatKeyPress = handleChatKeyPress;
window.sendMessage = sendMessage;
