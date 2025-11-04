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
                    <input style="width:100%;height:40px;padding:0 10px;border:1px solid var(--border);border-radius:10px;" name="avatarUrl" value="<%= ((model.User)session.getAttribute("user")).getAvatarUrl() %>"/>
                </label>
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

