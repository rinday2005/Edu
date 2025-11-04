<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-3-3.87M4 21v-2a4 4 0 0 1 3-3.87"/><circle cx="12" cy="7" r="4"/></svg>
        Quản lý tài khoản
    </h1>
    <div style="margin-bottom:12px; display:flex; gap:8px;">
        <a class="btn ghost" href="<%=request.getContextPath()%>/admin/users">All</a>
        <a class="btn ghost" href="<%=request.getContextPath()%>/admin/users?role=Instructor">Instructor</a>
        <a class="btn ghost" href="<%=request.getContextPath()%>/admin/users?role=Learner">Learner</a>
    </div>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>FullName</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<model.User> users = (java.util.List<model.User>) request.getAttribute("users");
                    if (users == null) users = java.util.Collections.emptyList();
                    for (model.User u : users) {
                %>
                <tr>
                    <td><%= u.getFullName() %></td>
                    <td><%= u.getEmail() %></td>
                    <td><%= u.getPhoneNumber() %></td>
                    <td><%= u.getRole() %></td>
                    <td><%= u.getStatus() ? "Active" : "Locked" %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/users" style="display:inline;">
                            <input type="hidden" name="userID" value="<%= u.getUserID() %>"/>
                            <input type="hidden" name="action" value="<%= u.getStatus()?"lock":"unlock" %>"/>
                            <button class="btn secondary" type="submit"><%= u.getStatus()?"Khóa":"Mở khóa" %></button>
                        </form>
                        <form method="post" action="<%=request.getContextPath()%>/admin/users" style="display:inline;margin-left:6px;">
                            <input type="hidden" name="userID" value="<%= u.getUserID() %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa tài khoản?');">Xóa</button>
                        </form>
                        <a class="btn" style="margin-left:6px;" href="<%=request.getContextPath()%>/admin/user/detail?id=<%= u.getUserID() %>">Xem chi tiết</a>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

