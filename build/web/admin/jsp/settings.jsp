<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Cài đặt hệ thống</h1>
    <div class="card">
        <form method="post" action="<%=request.getContextPath()%>/admin/settings">
            <div style="display:grid;grid-template-columns:1fr 1fr;gap:12px;">
                <label>Full name
                    <input style="width:100%;height:40px;padding:0 10px;border:1px solid var(--border);border-radius:10px;" name="fullName" value="<%= ((model.User)session.getAttribute("user")).getFullName() %>"/>
                </label>
                <label>Email
                    <input style="width:100%;height:40px;padding:0 10px;border:1px solid var(--border);border-radius:10px;" name="email" value="<%= ((model.User)session.getAttribute("user")).getEmail() %>"/>
                </label>
                <label>Phone
                    <input style="width:100%;height:40px;padding:0 10px;border:1px solid var(--border);border-radius:10px;" name="phoneNumber" value="<%= ((model.User)session.getAttribute("user")).getPhoneNumber() %>"/>
                </label>
                <label>Avatar URL
                    <input id="avatarUrlInput" style="width:100%;height:40px;padding:0 10px;border:1px solid var(--border);border-radius:10px;" name="avatarUrl" value="<%= ((model.User)session.getAttribute("user")).getAvatarUrl() %>"/>
                </label>
            </div>
            <div style="margin-top:12px; display:flex; align-items:center; gap:16px;">
                <div style="flex-shrink:0;">
                    <label style="display:block; margin-bottom:6px; font-weight:600;">Preview Avatar:</label>
                    <img id="avatarPreview" src="<%= ((model.User)session.getAttribute("user")).getAvatarUrl() != null ? ((model.User)session.getAttribute("user")).getAvatarUrl() : request.getContextPath() + "/uploads/avatar/default.png" %>" 
                         alt="Avatar Preview" 
                         style="width:120px; height:120px; border-radius:50%; object-fit:cover; border:2px solid var(--border); background:#f0f0f0;"
                         onerror="this.src='<%=request.getContextPath()%>/uploads/avatar/default.png';" />
                </div>
                <div style="flex:1;">
                    <p style="margin:0; color:#666; font-size:13px;">Nhập URL ảnh để xem preview. Avatar sẽ được cập nhật khi bạn lưu thay đổi.</p>
                </div>
            </div>
            <div style="margin-top:12px;">
                <button class="btn" type="submit">Lưu thay đổi</button>
            </div>
            <div style="margin-top:8px;color:green;"><%= request.getAttribute("message")!=null?request.getAttribute("message"):"" %></div>
            <div style="margin-top:8px;color:red;"><%= request.getAttribute("error")!=null?request.getAttribute("error"):"" %></div>
        </form>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />
<script src="<%=request.getContextPath()%>/admin/js/settings.js"></script>

